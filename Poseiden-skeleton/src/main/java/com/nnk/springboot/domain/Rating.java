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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rating")
public class Rating {
    

	public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
		super();
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;
	}

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // utilisation de GenerationType.IDENTITY pour la compatibilité avec Hibernate et MySQL
    @Column(name = "Id")
    private Integer id;
	@NotBlank
    @Column(name = "moodysRating")
    private String moodysRating;
	@NotBlank
    @Column(name = "sandPRating")
    private String sandPRating;
	@NotBlank
    @Column(name = "fitchRating")
    private String fitchRating;
	@NotBlank
    @Column(name = "orderNumber")
    private Integer orderNumber;
}
