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
@Table(name = "rulename")
public class RuleName {

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
		super();
		this.name = name;
		this.description = description;
		this.json = json;
		this.template = template;
		this.sqlStr = sqlStr;
		this.sqlPart = sqlPart;
	}
    
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // utilisation de GenerationType.IDENTITY pour la compatibilité avec Hibernate et MySQL
    @Column(name = "Id")
    private Integer id;
	@NotBlank
	@Column(name = "name")
    private String name;
	@NotBlank
    @Column(name = "description")
    private String description;
	@NotBlank
    @Column(name = "json")
    private String json;
	@NotBlank
    @Column(name = "template")
    private String template;
	@NotBlank
    @Column(name = "sqlStr")
    private String sqlStr;
	@NotBlank
    @Column(name = "sqlPart")
    private String sqlPart;
}
