package com.cafe.account.repositories;


import com.cafe.account.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByName(String name);
}
