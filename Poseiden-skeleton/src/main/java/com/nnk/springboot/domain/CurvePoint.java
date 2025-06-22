package com.nnk.springboot.domain;

import java.sql.Timestamp;

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
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    
    @Column(name = "CurveId")
    private Integer curveId;
    
    @Column(name = "asOfDate")
    private Timestamp asOfDate;
    
    @Column(name = "term")
    private double term;
    
    @Column(name = "value")
    private double value;
    
    @Column(name = "creationDate")
    private Timestamp creationDate;
}
