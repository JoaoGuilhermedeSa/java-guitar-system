package com.guitarfactory.domain.entity;

import com.guitarfactory.domain.enums.BridgeType;
import com.guitarfactory.domain.enums.FinishType;
import com.guitarfactory.domain.enums.PickupType;
import com.guitarfactory.domain.enums.WoodType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guitar_specs")
public class GuitarSpec {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer numberOfStrings;
	private Integer numberOfFrets;
	private Double scaleLengthInches;

	@Enumerated(EnumType.STRING)
	private WoodType bodyWood;

	@Enumerated(EnumType.STRING)
	private WoodType neckWood;

	@Enumerated(EnumType.STRING)
	private WoodType fretboardWood;

	@Enumerated(EnumType.STRING)
	private PickupType neckPickup;

	@Enumerated(EnumType.STRING)
	private PickupType bridgePickup;

	@Enumerated(EnumType.STRING)
	private BridgeType bridgeType;

	@Enumerated(EnumType.STRING)
	private FinishType finishType;

	@Column(length = 50)
	private String color;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumberOfStrings() {
		return numberOfStrings;
	}

	public void setNumberOfStrings(Integer numberOfStrings) {
		this.numberOfStrings = numberOfStrings;
	}

	public Integer getNumberOfFrets() {
		return numberOfFrets;
	}

	public void setNumberOfFrets(Integer numberOfFrets) {
		this.numberOfFrets = numberOfFrets;
	}

	public Double getScaleLengthInches() {
		return scaleLengthInches;
	}

	public void setScaleLengthInches(Double scaleLengthInches) {
		this.scaleLengthInches = scaleLengthInches;
	}

	public WoodType getBodyWood() {
		return bodyWood;
	}

	public void setBodyWood(WoodType bodyWood) {
		this.bodyWood = bodyWood;
	}

	public WoodType getNeckWood() {
		return neckWood;
	}

	public void setNeckWood(WoodType neckWood) {
		this.neckWood = neckWood;
	}

	public WoodType getFretboardWood() {
		return fretboardWood;
	}

	public void setFretboardWood(WoodType fretboardWood) {
		this.fretboardWood = fretboardWood;
	}

	public PickupType getNeckPickup() {
		return neckPickup;
	}

	public void setNeckPickup(PickupType neckPickup) {
		this.neckPickup = neckPickup;
	}

	public PickupType getBridgePickup() {
		return bridgePickup;
	}

	public void setBridgePickup(PickupType bridgePickup) {
		this.bridgePickup = bridgePickup;
	}

	public BridgeType getBridgeType() {
		return bridgeType;
	}

	public void setBridgeType(BridgeType bridgeType) {
		this.bridgeType = bridgeType;
	}

	public FinishType getFinishType() {
		return finishType;
	}

	public void setFinishType(FinishType finishType) {
		this.finishType = finishType;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}