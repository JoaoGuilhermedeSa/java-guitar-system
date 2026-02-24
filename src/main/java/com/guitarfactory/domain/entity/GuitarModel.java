package com.guitarfactory.domain.entity;

import java.math.BigDecimal;

import com.guitarfactory.domain.enums.GuitarOS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "guitar_models")
public class GuitarModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, unique = true, nullable = false)
	private String name;

	@Column(length = 500)
	private String description;

	@Enumerated(EnumType.STRING)
	private GuitarOS outputStyle;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "default_spec_id")
	private GuitarSpec defaultSpec;

	@Column(precision = 10, scale = 2)
	private BigDecimal basePrice;

	private Boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public GuitarOS getOutputStyle() {
		return outputStyle;
	}

	public void setOutputStyle(GuitarOS outputStyle) {
		this.outputStyle = outputStyle;
	}

	public GuitarSpec getDefaultSpec() {
		return defaultSpec;
	}

	public void setDefaultSpec(GuitarSpec defaultSpec) {
		this.defaultSpec = defaultSpec;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}