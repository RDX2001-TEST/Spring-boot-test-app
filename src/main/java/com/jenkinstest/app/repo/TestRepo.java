package com.jenkinstest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jenkinstest.app.entity.TestEntity;

@Repository
public interface TestRepo extends JpaRepository<TestEntity, Long>{

}
