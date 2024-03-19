package com.remiges.adv_java_task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.remiges.adv_java_task.entity.EmployeeShadow;

@Repository
public interface EmployeeShadowRepository extends JpaRepository<EmployeeShadow, Long> {

}
