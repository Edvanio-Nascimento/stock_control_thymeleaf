package com.desenedy.stockcontrol.repository;

import com.desenedy.stockcontrol.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByActiveTrue();

    List<Category> findByActiveFalse();

    List<Category> findByNameContainingIgnoreCaseAndActiveTrue(String name);

    List<Category> findByNameContainingIgnoreCaseAndActiveFalse(String name);

    boolean existsByNameIgnoreCase(String name);

}
