package com.jayaa.ems.service;

import com.jayaa.ems.dto.EmployeeDto;
import com.jayaa.ems.exception.ResourceNotFoundException;
import com.jayaa.ems.model.Employee;
import com.jayaa.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Transactional(readOnly = true)
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id
                ));
    }

    public Employee createEmployee(EmployeeDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());

        return repository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = getEmployeeById(id);

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());

        return repository.save(employee);
    }

    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Employee> searchEmployees(String search, Pageable pageable) {
        return repository.searchEmployees(search, pageable);
    }
}