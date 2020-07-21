package com.rab3tech.customer.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rab3tech.dao.entity.SecurityQuestions;

/**
 * @author nagendra
 * Spring Data JPA repository
 *
 */
public interface SecurityQuestionsRepository extends JpaRepository<SecurityQuestions, Integer> {
	
	public SecurityQuestions findByQuestions(String question);
}

