package com.example.CateringApp.repository;

import com.example.CateringApp.entity.Catering;
import com.example.CateringApp.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateringRepository extends JpaRepository<Catering,Integer> {
    List<Catering> findByStatus(Status status);
}
