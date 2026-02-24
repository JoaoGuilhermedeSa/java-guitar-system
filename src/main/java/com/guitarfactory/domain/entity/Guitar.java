package com.guitarfactory.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.guitarfactory.domain.enums.BuildStatus;
import com.guitarfactory.domain.enums.GuitarOS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "guitars")
public class Guitar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30, unique = true)
	private String serialNumber;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "model_id", nullable = false)
	private GuitarModel model;

	@Enumerated(EnumType.STRING)
	private GuitarOS outputStyle;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "spec_id", nullable = false)
	private GuitarSpec spec;

	@Enumerated(EnumType.STRING)
	private BuildStatus buildStatus;

	@Column(precision = 10, scale = 2)
	private BigDecimal finalPrice;

	@Column(length = 200)
	private String customerName;

	@Column(length = 200)
	private String customerEmail;

	private LocalDateTime orderedAt;
	private LocalDateTime completedAt;

	@PrePersist
	private void prePersist() {
		this.buildStatus = BuildStatus.ORDERED;
		this.orderedAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public GuitarModel getModel() {
		return model;
	}

	public void setModel(GuitarModel model) {
		this.model = model;
	}

	public GuitarOS getOutputStyle() {
		return outputStyle;
	}

	public void setOutputStyle(GuitarOS outputStyle) {
		this.outputStyle = outputStyle;
	}

	public GuitarSpec getSpec() {
		return spec;
	}

	public void setSpec(GuitarSpec spec) {
		this.spec = spec;
	}

	public BuildStatus getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(BuildStatus buildStatus) {
		this.buildStatus = buildStatus;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}

	public LocalDateTime getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(LocalDateTime completedAt) {
		this.completedAt = completedAt;
	}
}