package com.losagiles.CineAgile.seguridad;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.security.SecureRandom;

public class AESCipher {

    private static final String SECRET_KEY = "clave12345678901"; // 16 caracteres = 128 bits
    private static final String AES_ALGO = "AES/CBC/PKCS5Padding";

    public static String encrypt(String textoPlano) throws Exception {
        // Genera IV aleatorio
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // Llave AES
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(AES_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] cifrado = cipher.doFinal(textoPlano.getBytes());

        // Unimos IV + texto cifrado → para poder desencriptarlo luego
        byte[] ivMasCifrado = new byte[iv.length + cifrado.length];
        System.arraycopy(iv, 0, ivMasCifrado, 0, iv.length);
        System.arraycopy(cifrado, 0, ivMasCifrado, iv.length, cifrado.length);

        return Base64.getEncoder().encodeToString(ivMasCifrado);
    }

    public static String decrypt(String tokenBase64) throws Exception {
        byte[] ivMasCifrado = Base64.getDecoder().decode(tokenBase64);

        // Separamos IV y texto cifrado
        byte[] iv = new byte[16];
        byte[] cifrado = new byte[ivMasCifrado.length - 16];
        System.arraycopy(ivMasCifrado, 0, iv, 0, 16);
        System.arraycopy(ivMasCifrado, 16, cifrado, 0, cifrado.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance(AES_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] textoPlano = cipher.doFinal(cifrado);

        return new String(textoPlano);
    }
}

