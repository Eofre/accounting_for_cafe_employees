package com.cafe.account.service;

import com.cafe.account.models.Position;
import com.cafe.account.repositories.PositionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> findAll() {
        return positionRepository.findAll();
    }
}