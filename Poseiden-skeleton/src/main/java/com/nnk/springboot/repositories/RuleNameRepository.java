package com.nnk.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nnk.springboot.domain.RuleName;

@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
	
	Optional<RuleName> findById(RuleName id);
	
	boolean existsById(Integer id);
}
