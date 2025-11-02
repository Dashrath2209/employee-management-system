package com.jayaa.ems.repository;

import com.jayaa.ems.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    Page<Employee> findByDepartment(String department, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Employee> searchEmployees(String search, Pageable pageable);

    boolean existsByEmail(String email);
}