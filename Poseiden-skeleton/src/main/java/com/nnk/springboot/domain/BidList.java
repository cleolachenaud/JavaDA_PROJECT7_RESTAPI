package com.nnk.springboot.domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bidlist")
public class BidList {


	public BidList(String account, String type, double bidQuantity) {
		super();
		this.account = account;
		this.type = type;
		this.bidQuantity = bidQuantity;
	}

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "BidListId")
    private Integer bidListId;
    
    @Column(name = "account")
    private String account;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "bidQuantity")
    private double bidQuantity;
    
    @Column(name = "askQuantity")
    private double askQuantity;
    
    @Column(name = "bid")
    private double bid; 
    
    @Column(name = "ask")
    private double ask;
    
    @Column(name = "benchmark")
    private String benchmark;
    
    @Column(name = "bidListDate")
    private Timestamp bidListDate;
    
    @Column(name = "commentary")
    private String commentary;
    
    @Column(name = "security")
    private String security;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "trader")
    private String trader;
    
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
