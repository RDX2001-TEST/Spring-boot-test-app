package com.jenkinstest.app.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenkinstest.app.entity.TestEntity;
import com.jenkinstest.app.model.TestModel;
import com.jenkinstest.app.repo.TestRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	ModelMapper mapper;
	@Autowired
	TestRepo repo;
	@Autowired
	EntityManager entityManager;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody TestModel model) {
	    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	    TestEntity entity = mapper.map(model, TestEntity.class);
	    TestEntity savedEntity = repo.save(entity);

	    TestModel response = mapper.map(savedEntity, TestModel.class);

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<?> getData() {

	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<TestEntity> cq = cb.createQuery(TestEntity.class);

	    Root<TestEntity> root = cq.from(TestEntity.class);

	    // SELECT * FROM TestEntity
	    cq.select(root);

	    List<TestEntity> entities = entityManager.createQuery(cq).getResultList();

	    // Map Entity → Model
	    List<TestModel> response = entities.stream()
	            .map(e -> mapper.map(e, TestModel.class))
	            .toList();

	    return ResponseEntity.ok(response);
	}

}
