package com.remiges.adv_java_task.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDto {

    private Long id;
    private String empId;
    private String fname;
    private String fullname;
    private String rankDesc;
    private String departmentName;
}

