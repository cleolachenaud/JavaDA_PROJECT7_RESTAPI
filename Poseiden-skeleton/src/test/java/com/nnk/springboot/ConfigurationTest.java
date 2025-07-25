package com.nnk.springboot;

import javax.sql.DataSource;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages = {
										"com.nnk.springboot.repositories",
										"com.nnk.springboot.domain",
										"com.nnk.springboot.services",
										"com.nnk.springboot.controllers",
										})
@EnableTransactionManagement
/**
 * classe qui permet de configurer une base dédiée au test
 * et ainsi ne pas toucher à la base de production
 * utile pour les tests du package repositories qui touche directement à la bdd
 * pour que la configuration soit prise en charge, penser à annoter la classe de test de : 
 * @SpringBootTest 
 * @ActiveProfiles("test")
 */
public class ConfigurationTest {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/demoTest");
        dataSource.setUsername("root");
        dataSource.setPassword("rootroot");

        return dataSource;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(emf);

       return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
       em.setDataSource(dataSource());
       em.setPackagesToScan(new String[] {"com.nnk.springboot.domain"});

       HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       em.setJpaVendorAdapter(vendorAdapter);

       return em;
    }
}