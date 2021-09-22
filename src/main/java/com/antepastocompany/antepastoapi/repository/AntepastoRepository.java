package com.antepastocompany.antepastoapi.repository;

import com.antepastocompany.antepastoapi.entity.Antepasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AntepastoRepository extends JpaRepository<Antepasto, Long> {

    Optional<Antepasto> findByFlavor(String flavor);

}
