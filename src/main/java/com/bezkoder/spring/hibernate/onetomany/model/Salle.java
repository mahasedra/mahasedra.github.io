package com.bezkoder.spring.hibernate.onetomany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salles")

public class Salle {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorial_generator")
	private long id;

	@Column(name = "designation")
	private String designation;

	public Salle() {

	}

	public Salle(String designation) {
		this.designation = designation;
	}

	public long getId() {
		return id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "Salle [id=" + id + ", designation=" + designation + "]";
	}
}
