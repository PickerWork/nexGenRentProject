package com.nexgencarrental.nexGenCarRental.services.rules.model;

import com.nexgencarrental.nexGenCarRental.repositories.CarRepository;
import com.nexgencarrental.nexGenCarRental.repositories.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelBusinessRulesManager implements ModelBusinessRulesService {
    private ModelRepository modelRepository;
    private CarRepository carRepository;

    @Override
    public void existsByName(String name) {
        if (modelRepository.existsByName(name.trim().replaceAll("\\s", ""))) {
            throw new RuntimeException("The Model name is already exists!");
        }

    }

    @Override
    public void existsById(int id) {
        if (!modelRepository.existsById(id)) {
            throw new RuntimeException("The Model with " + id + " the ID number cannot be found in the system.");
        }
    }

    @Override
    public void canDeleteModel(int id) {
        if (carRepository.existsById(id)) {
            throw new RuntimeException("There are vehicles linked to this model. " + id + " You cannot delete the model.");
        }
    }
}
