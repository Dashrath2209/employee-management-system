package com.jayaa.ems.controller;

import com.jayaa.ems.dto.EmployeeDto;
import com.jayaa.ems.model.Employee;
import com.jayaa.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllEmployees(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDto dto) {
        Employee created = service.createEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto dto
    ) {
        return ResponseEntity.ok(service.updateEmployee(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam String q,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(service.searchEmployees(q, pageable));
    }
}