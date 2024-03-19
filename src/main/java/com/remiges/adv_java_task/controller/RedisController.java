package com.remiges.adv_java_task.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.remiges.adv_java_task.json.JasonRequest;
import com.remiges.adv_java_task.json.JasonResponse;
import com.remiges.adv_java_task.json.UtilGenerator;
import com.remiges.adv_java_task.service.EmployeeService;
import com.remiges.adv_java_task.service.RedisService;

import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    private EmployeeService employeeService;


    // @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    // Problem 20. (i)
    @PostMapping("/addEmployee")
    public ResponseEntity<Object> addEmployee(@RequestBody JasonRequest request) {
        Map<String, Object> data = request.getData();
        String empName = (String) data.get("empName");
        redisService.addEmployee(empName);
        return JasonResponse.generateResponse("success", "", "", empName, UtilGenerator.generateUniqueString(12));
    }

    // Problem 20. (ii)
    @GetMapping("/getEmployeeValue")
    public ResponseEntity<Object> getEmployeeValue(@RequestParam String empName, @RequestBody JasonRequest request) {
        Integer value = redisService.getEmployeeValue(empName);
        return JasonResponse.generateResponse("success", "", "", value, request.get_reqid());
    }

    // Problem 20. (iii)
    @PostMapping("/incrementEmployeeValue")
    public ResponseEntity<Object> incrementEmployeeValue(@RequestParam String empName,
            @RequestBody JasonRequest request) {
        redisService.incrementEmployeeValue(empName);
        return JasonResponse.generateResponse("success", "", "", request.getData(),
                UtilGenerator.generateUniqueString(12));
    }

    // Problem 20. (iv)
    @PostMapping("/decrementEmployeeValue")
    public ResponseEntity<Object> decrementEmployeeValue(@RequestParam String empName,
            @RequestBody JasonRequest request) {
        redisService.decrementEmployeeValue(empName);
        return JasonResponse.generateResponse("success", "", "", request.getData(),
                UtilGenerator.generateUniqueString(12));
    }

    // Problem 20. (v)
    @PostMapping("/setEmployeeTTL")
    public ResponseEntity<Object> setEmployeeTTL(@RequestParam String empName, @RequestParam long ttlInSeconds,
            @RequestBody JasonRequest request) {
        redisService.setEmployeeTTL(empName, ttlInSeconds);
        return JasonResponse.generateResponse("success", "", "", request.getData(),
                UtilGenerator.generateUniqueString(12));
    }

    // Problem 21.

    @PostMapping("/updateEmployeeContribution")
    public ResponseEntity<Object> updateEmployeeContribution(@RequestBody JasonRequest request) {
        try {
            String department = (String) request.getData().get("department");
            String employeeId = (String) request.getData().get("employeeId");
            int count = request.getData().containsKey("count") ? (int) request.getData().get("count") : 1;

            Map<String, Integer> contribution = redisService.updateEmployeeContribution(department, employeeId,
                    count);

            return JasonResponse.generateResponse("success", "", "", contribution, request.get_reqid());
        } catch (Exception e) {
            return JasonResponse.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }
    }

    // Problem 22.

    @GetMapping("/myhr/employee/getContribution")
    public ResponseEntity<Object> getEmployeeContribution(@RequestParam String department) {

        try {

            // Call service method to get the latest count for the employee
            int latestCount = redisService.getEmployeeContribution(department);
            // Return the response
            // return ResponseEntity.ok().body("Latest count for department " + department +
            // ": " + latestCount);
            return JasonResponse.generateResponse("success", "", "", latestCount,
                    UtilGenerator.generateUniqueString(12));
        } catch (Exception e) {
            return JasonResponse.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }

    }

    // Problem 23.

    @GetMapping("/{department}/{employeeId}/contribution")
    public ResponseEntity<Object> getEmployeeContribution(@PathVariable String department,
            @PathVariable String employeeId) {

        try {

            int contribution = employeeService.getEmployeeContribution(department, employeeId);

            return JasonResponse.generateResponse("success", "", "", contribution,
                    UtilGenerator.generateUniqueString(12));
        } catch (Exception e) {
            return JasonResponse.generateResponse("error", "500", e.getMessage(), null,
                    UtilGenerator.generateUniqueString(12));
        }
    }

}

