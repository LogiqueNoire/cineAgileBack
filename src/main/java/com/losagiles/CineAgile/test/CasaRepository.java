package com.losagiles.CineAgile.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasaRepository extends JpaRepository<Casa, Long> {

    public Casa getCasaById(Long id);

}
