package com.losagiles.CineAgile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.losagiles.CineAgile.entidades.RegistroAccion;

@Repository
public interface RegistroAccionRepository extends JpaRepository<RegistroAccion, Long> {
}

