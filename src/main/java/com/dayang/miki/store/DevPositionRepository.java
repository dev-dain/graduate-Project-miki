package com.dayang.miki.store;

import com.dayang.miki.domain.Position;
import com.dayang.miki.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevPositionRepository extends JpaRepository<Position, Long> {
    Position findByStore(Store store);
}
