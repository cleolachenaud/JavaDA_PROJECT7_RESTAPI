package com.nnk.springboot.domain;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	public CurvePoint(Integer curveId, double term, double value) {
		super();
		this.curveId = curveId;
		this.term = term;
		this.value = value;
	}

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
	@NotBlank
    @Column(name = "CurveId")
    private Integer curveId;
    
    @Column(name = "asOfDate")
    private Timestamp asOfDate;
    @NotBlank
    @Column(name = "term")
    private double term;
    @NotBlank
    @Column(name = "value")
    private double value;
    
    @Column(name = "creationDate")
    private Timestamp creationDate;
}
