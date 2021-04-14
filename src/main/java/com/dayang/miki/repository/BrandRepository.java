package com.dayang.miki.repository;

import com.dayang.miki.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByNameContaining(String name);
}
