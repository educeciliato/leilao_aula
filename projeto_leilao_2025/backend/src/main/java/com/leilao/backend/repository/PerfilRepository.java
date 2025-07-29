package com.leilao.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.leilao.backend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    @Query("from Perfil where email=:email")
    public Page<Perfil> buscarEmail(@Param("email") String email, Pageable pageable);
}
