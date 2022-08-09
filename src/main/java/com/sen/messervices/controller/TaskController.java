package com.sen.messervices.controller;


import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sen.messervices.model.Category;
import com.sen.messervices.model.Task;
import com.sen.messervices.repository.CategoryRepository;
import com.sen.messervices.repository.TaskRepository;



@RestController
public class TaskController {

	private final Logger logger= LoggerFactory.getLogger(TaskController.class);
	private TaskRepository taskRepository;
	private CategoryRepository categoryRepository;
	/**
	 * @param taskRepository
	 * @param categoryRepository
	 */
	public TaskController(TaskRepository taskRepository, CategoryRepository categoryRepository) {
		super();
		this.taskRepository = taskRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@PostMapping("task")
	@Transactional
	public ResponseEntity<Task> addTask(@RequestBody Task task){
		logger.info("task {}", task);
		Set<Category> categories = task.getCategories();
		Set<Category> taskCategories = new HashSet<>();
		
		categories.stream().forEach(category->{
			Category existingCategory = categoryRepository.findByName(category.getName());
			if (existingCategory==null) {
				existingCategory = categoryRepository.save(category);
				taskCategories.add(existingCategory);
			}
			existingCategory.setTasks(new HashSet<>());
			taskCategories.add(existingCategory);
		});
		task.setCategories(taskCategories);
		Task savedTask = taskRepository.save(task);
		return new ResponseEntity<Task>(savedTask, HttpStatus.CREATED);
		}
}
