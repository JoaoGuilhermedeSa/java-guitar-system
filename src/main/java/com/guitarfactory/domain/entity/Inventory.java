package com.guitarfactory.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "inventory", uniqueConstraints = @UniqueConstraint(columnNames = { "model_id", "spec_id" }))
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "model_id", nullable = false)
	private GuitarModel model;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "spec_id", nullable = false)
	private GuitarSpec spec;

	private Integer quantityAvailable;
	private Integer reorderThreshold;
	private LocalDateTime lastUpdated;

	@PrePersist
	@PreUpdate
	private void touchTimestamp() {
		this.lastUpdated = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GuitarModel getModel() {
		return model;
	}

	public void setModel(GuitarModel model) {
		this.model = model;
	}

	public GuitarSpec getSpec() {
		return spec;
	}

	public void setSpec(GuitarSpec spec) {
		this.spec = spec;
	}

	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Integer getReorderThreshold() {
		return reorderThreshold;
	}

	public void setReorderThreshold(Integer reorderThreshold) {
		this.reorderThreshold = reorderThreshold;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}