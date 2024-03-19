package com.remiges.adv_java_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.remiges.adv_java_task.entity.Departments;

@Repository
public interface DepatmentRepository extends JpaRepository<Departments , Long> {

}
