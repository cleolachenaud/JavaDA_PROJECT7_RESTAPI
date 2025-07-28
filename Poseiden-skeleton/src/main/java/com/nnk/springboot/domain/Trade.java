package com.nnk.springboot.domain;


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

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "trade")
public class Trade {
	
	public Trade(String account, String type, Double buyQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.buyQuantity = buyQuantity;
	}

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer tradeId;
	@NotBlank
    @Column(name = "account")
    private String account;
	@NotBlank
    @Column(name = "type")
    private String type;
	@NotBlank
    @Column(name = "buyQuantity")
    private Double buyQuantity;
    
    @Column(name = "sellQuantity")
    private Double sellQuantity;
    
    @Column(name = "buyPrice")
    private Double buyPrice;
    
    @Column(name = "sellPrice")
    private Double sellPrice;
    
    @Column(name = "tradeDate")
    private Timestamp tradeDate;
    
    @Column(name = "security")
    private String security;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "trader")
    private String trader;
    
    @Column(name = "benchmark")
    private String benchmark;
    
    @Column(name = "book")
    private String book;
    
    @Column(name = "creationName")
    private String creationName;
    
    @Column(name = "creationDate")
    private Timestamp creationDate;
    
    @Column(name = "revisionName")
    private String revisionName;
    
    @Column(name = "revisionDate")
    private Timestamp revisionDate;
    
    @Column(name = "dealName")
    private String dealName;
    
    @Column(name = "dealType")
    private String dealType;
    
    @Column(name = "sourceListId")
    private String sourceListId;
    
    @Column(name = "side")
    private String side;
    
    
}
