package com.losagiles.CineAgile.controllersVenta;

import com.losagiles.CineAgile.dto.responses.ResComprarEntrada;
import com.losagiles.CineAgile.dto.responses.ResRegistrarEntrada;
import com.losagiles.CineAgile.dto.responses.RespuestaPago;
import com.losagiles.CineAgile.dto.solicitudes.PagoRequest;
import com.losagiles.CineAgile.services.EntradaService;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

@RestController
@RequestMapping ("/api/venta/v1/pagos")
public class MercadoPagoController {
    @Value("${mercado.pago.access.token}")
    private String mercadoPagoToken;

    @Autowired
    private EntradaService entradaService;

    @PostMapping
    public ResponseEntity<?> procesarPago(@RequestBody PagoRequest request) {
        MercadoPagoConfig.setAccessToken(mercadoPagoToken);

        PaymentClient paymentClient = new PaymentClient();

        System.out.println(mercadoPagoToken);
        System.out.println(request.getMonto());
        System.out.println(request.getToken());
        System.out.println(request.getPaymentMethodId());
        System.out.println(request.getIssuerId());
        System.out.println(request.getEmail());

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(new BigDecimal(request.getMonto()).setScale(2, RoundingMode.HALF_UP))
                .token(request.getToken())
                .description("Pago de cuota") // o puedes usar request.getDescription() si lo agregas
                .installments(1) // puedes hacerlo dinámico: request.getInstallments()
                .paymentMethodId(request.getPaymentMethodId())
                .issuerId(request.getIssuerId()) // puede ser null sin problema
                .payer(PaymentPayerRequest.builder()
                        .email(request.getEmail())
                        .build())
                .build();
        try {
            // Respuesta completa del pago (incluye status, id, etc.)
            Payment payment = paymentClient.create(paymentCreateRequest);
            if(payment != null) {
                ResComprarEntrada resultadoRegistroEntradas = null;
                if(payment.getStatus().compareToIgnoreCase("approved")==0){
                    ResRegistrarEntrada res = entradaService.registrarEntradas(request.getEntradas());
                    resultadoRegistroEntradas = new ResComprarEntrada(res.entradasCompradas(), res.status().getDescripcion());
                }
                String nuevoStatus = switch (payment.getStatus()) {
                    case "approved" -> "Pago aprobado";
                    case "in_process" -> "Pago pendiente";
                    case "rejected" -> "Pago rechazado";
                    default -> "Desconocido";
                };
                String nuevoStatusDetail = switch (payment.getStatusDetail()) {
                    case "accredited" -> "Pago recibido";
                    case "pending_contingency" -> "Pago en validación";
                    case "cc_rejected_insufficient_amount" -> "Fondos insuficientes";
                    case "cc_rejected_bad_filled_security_code" -> "Código de seguridad inválido";
                    case "cc_rejected_bad_filled_date" -> "Fecha de vencimiento inválida";
                    case "cc_rejected_call_for_authorize" -> "Debes autorizar el pago con tu banco";
                    case "cc_rejected_other_reason", "cc_rejected_bad_filled_other" -> "Pago rechazado por el banco";
                    default -> "Pago rechazado";
                };
                return ResponseEntity.ok(new RespuestaPago(request.getIdOperacion(), request.getPaymentMethodId(),
                        nuevoStatus, nuevoStatusDetail, payment.getTransactionAmount(), resultadoRegistroEntradas));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                        Map.of("message", "Servidor de Mercado Pago respondió algo inválido",
                                "idOperacion", request.getIdOperacion()));
            }
        } catch (MPApiException e) {
            System.err.println("Status code: " + e.getStatusCode());
            System.err.println("Response: " + e.getApiResponse().getContent());
            return ResponseEntity.status(500).body("Error MercadoPago: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error SDK: " + e.getMessage());
        }


    }
}
