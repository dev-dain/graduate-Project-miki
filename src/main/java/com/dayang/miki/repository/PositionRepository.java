package com.dayang.miki.repository;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByStore(Store store);


}
