package com.sen.messervices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sen.messervices.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
