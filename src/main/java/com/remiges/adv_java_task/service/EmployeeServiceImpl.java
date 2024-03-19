package com.remiges.adv_java_task.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.remiges.adv_java_task.dto.EmployeeDetailsDto;
import com.remiges.adv_java_task.dto.EmployeeDto;
import com.remiges.adv_java_task.dto.UpdateEmployeeRequest;
import com.remiges.adv_java_task.entity.Departments;
import com.remiges.adv_java_task.entity.Employee;
import com.remiges.adv_java_task.entity.EmployeeShadow;
import com.remiges.adv_java_task.entity.Ranks;
import com.remiges.adv_java_task.repository.DepatmentRepository;
import com.remiges.adv_java_task.repository.EmployeeRepository;
import com.remiges.adv_java_task.repository.EmployeeShadowRepository;
import com.remiges.adv_java_task.repository.RankRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private DepatmentRepository departmentRepository;

    @Autowired
    private EmployeeShadowRepository employeeShadowRepository;

       @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    public void addEmployee(EmployeeDto employeeDto) {

        Employee employee = new Employee();
        employee.setEmpId(employeeDto.getEmpId());
        employee.setFname(employeeDto.getFname());
        employee.setFullname(employeeDto.getFullname());
        employee.setDob(employeeDto.getDob());
        employee.setDoj(employeeDto.getDoj());
        employee.setSalary(employeeDto.getSalary());
        employee.setReportsTo(employeeDto.getReportsTo());

        Departments department = new Departments();
        department.setId((employeeDto.getDeptId()));
        employee.setDepartment(department);

        Ranks rank = new Ranks();
        rank.setId(employeeDto.getRankId());
        employee.setRank(rank);

        employee.setClientReqId(employeeDto.getClientReqId());

        employeeRepository.save(employee);

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    private EmployeeDto convertToDTO(Employee emp) {
        EmployeeDto dto = new EmployeeDto();

        dto.setEmpId(emp.getEmpId());
        dto.setFname(emp.getFname());

        return dto;
    }

    @Override
    public EmployeeDetailsDto getEmployeeDetails(Long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Ranks rank = rankRepository.findById(employee.getRank().getId()).orElse(null);
            Departments department = departmentRepository.findById(employee.getDepartment().getId()).orElse(null);

            EmployeeDetailsDto employeeDetailsDTO = new EmployeeDetailsDto();
            employeeDetailsDTO.setId(employee.getId());
            employeeDetailsDTO.setEmpId(employee.getEmpId());
            employeeDetailsDTO.setFname(employee.getFname());
            employeeDetailsDTO.setFullname(employee.getFullname());
            if (rank != null) {
                employeeDetailsDTO.setRankDesc(rank.getRankDesc());
            }

            if (department != null) {
                employeeDetailsDTO.setDepartmentName(department.getDeptName());
            }
            return employeeDetailsDTO;
        } else {
            return null; // Or throw an exception if employee with given ID is not found
        }
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeRequest updateRequest) {
        try {
            Employee employee = employeeRepository.findById(updateRequest.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            // Set the updatedBy to current user in employee_shadow Table
            EmployeeShadow empShadow = new EmployeeShadow(employee);
            employeeShadowRepository.save(empShadow);

            // Employee employee = new Employee();
            // Update employee details
            employee.setId(updateRequest.getId());
            employee.setEmpId(updateRequest.getEmpId());
            employee.setFname(updateRequest.getFname());
            employee.setFullname(updateRequest.getFullname());
            employee.setDob(updateRequest.getDob());
            employee.setDoj(updateRequest.getDoj());
            employee.setSalary(updateRequest.getSalary());
            employee.setReportsTo(updateRequest.getReportsTo());

            // Save and return the updated employee
            return employeeRepository.save(employee);
        } catch (Exception e) {
            return null;
        }
    }



    @Override
    public ResponseEntity<Object> deleteEmployee(Long id) {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (!optionalEmployee.isPresent()) {
                // throw new EmployeeNotFoundException("Employee not found");
                throw new RuntimeException("Employee not found");
            }

            // setting employee in employee_shadow table before gets deleted
            Employee employee = optionalEmployee.get();
            EmployeeShadow shadowEmployee = new EmployeeShadow(employee);
            employeeShadowRepository.save(shadowEmployee);
            // Delete the employee
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            // throw new EmployeeServiceException("Failed to delete employee", e);
            new RuntimeException("Failed to add Employee ", e);
        }
        return null;
    }

        // Problem 23.

    public int getEmployeeContribution(String department, String employeeId) {
        String key = "user." + department + "." + employeeId;
        Integer contribution = redisTemplate.opsForValue().get(key);
        if (contribution == null) {
            contribution = fetchEmployeeContributionFromDatabase(department, employeeId);
            cacheEmployeeContributionInRedis(department, employeeId, contribution);
        }
        return contribution;
    }

    private int fetchEmployeeContributionFromDatabase(String department, String employeeId) {
        // Perform database operation to fetch contribution
        // For example:
        // Employee employee = employeeRepository.findByDepartmentAndEmpId(department,
        // employeeId);
        // return employee.getContribution();
        // For demonstration, returning a dummy value
        return 7;
    }

    private void cacheEmployeeContributionInRedis(String department, String employeeId, int contribution) {
        String key = "user." + department + "." + employeeId;
        redisTemplate.opsForValue().set(key, contribution);
        // Set TTL for the key
        redisTemplate.expire(key, 3, TimeUnit.MINUTES);
    }

    // Problem 23 Ends Here.

    
}

