package com.sen.messervices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sen.messervices.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	public Category findByName(String name);
}
