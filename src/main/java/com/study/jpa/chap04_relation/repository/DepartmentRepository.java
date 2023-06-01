package com.study.jpa.chap04_relation.repository;


import com.study.jpa.chap04_relation.entity.Department;
import com.study.jpa.chap04_relation.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("select distinct d from Department d join fetch d.employees")  //별칭 사용
    List<Department> findAllIncludeEmployees();
}

