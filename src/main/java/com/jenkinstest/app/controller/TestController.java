package com.jenkinstest.app.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenkinstest.app.entity.TestEntity;
import com.jenkinstest.app.model.TestModel;
import com.jenkinstest.app.repo.TestRepo;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	ModelMapper mapper;
	@Autowired
	TestRepo repo;
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody TestModel model) {
	    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	    TestEntity entity = mapper.map(model, TestEntity.class);
	    TestEntity savedEntity = repo.save(entity);

	    TestModel response = mapper.map(savedEntity, TestModel.class);

	    return ResponseEntity.ok(response);
	}

}
