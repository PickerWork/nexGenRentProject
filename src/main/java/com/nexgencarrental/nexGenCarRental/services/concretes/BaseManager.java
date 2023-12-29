package com.nexgencarrental.nexGenCarRental.services.concretes;

import com.nexgencarrental.nexGenCarRental.core.utilities.mappers.ModelMapperService;
import com.nexgencarrental.nexGenCarRental.entities.concretes.Car;
import com.nexgencarrental.nexGenCarRental.services.abstracts.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public abstract class BaseManager<T, R extends JpaRepository<T, Integer>, G, L, A, U> implements BaseService<T, R, G, L, A, U> {

    public R repository;
    public ModelMapperService modelMapperService;
    public Class<G> responseType;
    public Class<L> listResponseType;
    private final Class<T> entityClass;
    public Class<A> requestType;
    public Class<U> updateRequestType;

    @Override
    public void delete(int id) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(entityClass.getSimpleName() +" with ID " + id + " not found."));
        repository.delete(entity);
    }
    @Override
    public G getById(int id) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(entityClass.getSimpleName() +" with ID " + id + " not found."));
        return modelMapperService.forResponse().map(entity, responseType);
    }
    @Override
    public List<L> getAll() {
        List<T> entities = repository.findAll();
        return entities.stream()
                .map(entity -> modelMapperService.forResponse().map(entity, listResponseType))
                .collect(Collectors.toList());
    }
    @Override
    public void add(A request, Class<T> entityClass) {
        T entity = modelMapperService.forRequest().map(request, entityClass);
        if (entity instanceof Car) {
            Car carEntity = (Car) entity;
            carEntity.setPlate(carEntity.getPlate().replaceAll("\\s", ""));
        }
        repository.save(entity);
    }
    @Override
    public void update(U updateRequest, Class<T> entityClass) {
        T entity = modelMapperService.forRequest().map(updateRequest, entityClass);
        if (entity instanceof Car) {
            Car carEntity = (Car) entity;
            carEntity.setPlate(carEntity.getPlate().replaceAll("\\s", ""));
        }
        repository.save(entity);
    }
}
