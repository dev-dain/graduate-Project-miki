package com.dayang.miki.store;

import com.dayang.miki.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DevStoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findById(Long id);

    List<Store> findAllByIdGreaterThan(Long id);
}
