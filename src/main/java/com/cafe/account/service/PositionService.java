package com.cafe.account.service;

import com.cafe.account.dto.position.PositionDto;
import com.cafe.account.dto.position.PositionUpdateDto;
import com.cafe.account.models.Employee;
import com.cafe.account.models.Position;
import com.cafe.account.repositories.EmployeeRepository;
import com.cafe.account.repositories.PositionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Position> findAll() {
        return positionRepository.findAll();
    }



    public Position create(PositionDto positionDto) {
        Position position = new Position();
        position.setName(positionDto.getName());
        position.setHourlyRate(positionDto.getHourlyRate());

        try {
            return positionRepository.save(position);
        } catch (DataIntegrityViolationException e) {
            // handle unique constraint violation
            throw new IllegalArgumentException("Такая должность уже существует");
        }
    }

    public void deleteById(Long id) {
        List<Employee> employees = employeeRepository.findByPositionId(id);
        if (!employees.isEmpty()) {
            throw new IllegalArgumentException("Нельзя удалить данную должность, так как есть работники с такой должностью");
        } else {
            positionRepository.deleteById(id);
        }
    }

    public PositionUpdateDto getPositionById(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Position not found with id: " + id));
       PositionUpdateDto positionUpdateDto = new PositionUpdateDto();
       positionUpdateDto.setId(id);
       positionUpdateDto.setName(position.getName());
       positionUpdateDto.setHourlyRate(position.getHourlyRate());
       return positionUpdateDto;
    }

    public void updatePosition(PositionUpdateDto positionUpdateDto) {
        Position position = positionRepository.findById(positionUpdateDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Должность с id=" + positionUpdateDto.getId() + " не найдена"));

        if (positionRepository.findByName(positionUpdateDto.getName()).isPresent() && !position.getName().equals(positionUpdateDto.getName())) {
            throw new IllegalArgumentException("Должность " + positionUpdateDto.getName() + " уже существует!");
        } else {
            position.setHourlyRate(positionUpdateDto.getHourlyRate());
            position.setName(positionUpdateDto.getName());
            positionRepository.save(position);
        }

    }

    public List<Position> findByNameContaining(String name) {
        return positionRepository.findByNameContaining(name);
    }
}