package com.bezkoder.spring.hibernate.onetomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.hibernate.onetomany.model.Salle;

public interface SalleRepository extends JpaRepository<Salle, Long> {
	List<Salle> findByDesignationContaining(String designation);
}
