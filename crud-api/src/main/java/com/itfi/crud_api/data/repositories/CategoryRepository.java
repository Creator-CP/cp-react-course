package com.itfi.crud_api.data.repositories;

import com.itfi.crud_api.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {}
