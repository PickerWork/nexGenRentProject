package com.nexgencarrental.nexGenCarRental.services.abstracts;

import com.nexgencarrental.nexGenCarRental.services.dtos.requests.car.AddCarRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.car.UpdateCarRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.car.GetCarListResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.car.GetCarResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.color.GetColorListResponse;

import java.util.List;

public interface CarService {
    public List<GetCarListResponse> getAll();
    public GetCarResponse getById(int id);

    public void add(AddCarRequest addCarRequest);

    public void update(UpdateCarRequest updateCarRequest);

    public void delete(int id);
}
