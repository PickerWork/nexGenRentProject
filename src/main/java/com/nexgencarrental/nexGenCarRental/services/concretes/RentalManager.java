package com.nexgencarrental.nexGenCarRental.services.concretes;

import com.nexgencarrental.nexGenCarRental.core.utilities.mappers.ModelMapperService;
import com.nexgencarrental.nexGenCarRental.entities.concretes.Rental;
import com.nexgencarrental.nexGenCarRental.repositories.RentalRepository;
import com.nexgencarrental.nexGenCarRental.services.abstracts.CarService;
import com.nexgencarrental.nexGenCarRental.services.abstracts.CustomerService;
import com.nexgencarrental.nexGenCarRental.services.abstracts.EmployeeService;
import com.nexgencarrental.nexGenCarRental.services.abstracts.RentalService;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.rental.AddRentalRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.rental.UpdateRentalRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.car.GetCarResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.rental.GetRentalListResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.rental.GetRentalResponse;
import com.nexgencarrental.nexGenCarRental.services.rules.rental.RentalBusinessRulesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RentalManager implements RentalService {
    private final RentalRepository rentalRepository;
    private final ModelMapperService modelMapperService;
    private final CarService carService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final RentalBusinessRulesService rentalBusinessRulesService;

    @Override
    public List<GetRentalListResponse> getAll() {
        return rentalRepository.findAll().stream()
                .map(rental -> modelMapperService.forResponse()
                        .map(rental, GetRentalListResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetRentalResponse getById(int id) {
        return rentalRepository.findById(id)
                .map(rental -> modelMapperService.forResponse().map(rental, GetRentalResponse.class))
                .orElseThrow();
    }

    @Override
    public void add(AddRentalRequest addRentalRequest) {

        // Car id kontrolü
        carService.getById(addRentalRequest.getCarId());

        // Customer id kontrolü
        customerService.getById(addRentalRequest.getCustomerId());

        // Employee id kontrolü
        employeeService.getById(addRentalRequest.getEmployeeId());

        // Başlangıç tarihi kontrolü
        if(addRentalRequest.getStartDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Başlangıç tarihi " + LocalDate.now() + " , yani bugünden daha önce bir tarih olamaz");
        }

        // Bitiş tarihi kontrolü
        if (addRentalRequest.getEndDate().isBefore(addRentalRequest.getStartDate())){
            throw new RuntimeException("Girdiğiniz tarih, " + addRentalRequest.getStartDate() + " bu tarihden ileri bir tarih olmalıdır.");
        }

        // Kiralama süresi kontrolü
        if (ChronoUnit.DAYS.between(addRentalRequest.getStartDate(), addRentalRequest.getEndDate())  > 25
                || 0 == ChronoUnit.DAYS.between(addRentalRequest.getStartDate(), addRentalRequest.getEndDate())) {
            throw new RuntimeException("Bir araç minimum 1 gün, maksimum 25 gün kiralanabilir.");
        }

        // Yeni Sipariş oluşturulması ve kaydedilmesi
        Rental addRental = modelMapperService.forRequest().map(addRentalRequest, Rental.class);

        // Araç kilometresi otomatik olarak id'den alıp kilometreyi çeker.
        GetCarResponse carId = carService.getById(addRentalRequest.getCarId());
        addRental.setStartKilometer(carId.getKilometer());

        // totalPrice hesaplaması burada yapılır
        addRental.setTotalPrice(carId.getDailyPrice() * ChronoUnit.DAYS.between(addRentalRequest.getStartDate(), addRentalRequest.getEndDate()));

        addRental.setEndKilometer(null);
        addRental.setReturnDate(null);
        rentalRepository.save(addRental);
    }

    @Override
    public void update(UpdateRentalRequest updateRentalRequest) {

        // Rental id kontrolü
        rentalBusinessRulesService.existsById(updateRentalRequest.getId());

        // Car id kontrolü
        carService.getById(updateRentalRequest.getCarId());

        // Customer id kontrolü
        customerService.getById(updateRentalRequest.getCustomerId());

        // Employee id kontrolü
        employeeService.getById(updateRentalRequest.getEmployeeId());

        // Başlangıç tarihi kontrolü
        if(updateRentalRequest.getStartDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Başlangıç tarihi " + LocalDate.now() + " , yani bugünden daha önce bir tarih olamaz");
        }

        // Bitiş tarihi kontrolü
        if (updateRentalRequest.getEndDate().isBefore(updateRentalRequest.getStartDate())){
            throw new RuntimeException("Girdiğiniz tarih, " + updateRentalRequest.getStartDate() + " bu tarihden ileri bir tarih olmalıdır.");
        }

        // Kiralama süresi kontrolü
        if (ChronoUnit.DAYS.between(updateRentalRequest.getStartDate(), updateRentalRequest.getEndDate())  > 25
                || 0 == ChronoUnit.DAYS.between(updateRentalRequest.getStartDate(), updateRentalRequest.getEndDate())) {
            throw new RuntimeException("Bir araç minimum 1 gün, maksimum 25 gün kiralanabilir.");
        }

        // Yeni Sipariş oluşturulması ve kaydedilmesi
        Rental addRental = modelMapperService.forRequest().map(updateRentalRequest, Rental.class);

        // Araç kilometresi otomatik olarak id'den alıp kilometreyi çeker.
        GetCarResponse carId = carService.getById(updateRentalRequest.getCarId());
        addRental.setStartKilometer(carId.getKilometer());

        // totalPrice hesaplaması burada yapılır
        addRental.setTotalPrice(carId.getDailyPrice() * ChronoUnit.DAYS.between(updateRentalRequest.getStartDate(), updateRentalRequest.getEndDate()));

        addRental.setEndKilometer(null);
        addRental.setReturnDate(null);
        rentalRepository.save(addRental);

    }

    @Override
    public void delete(int id) {
        Rental deleteRental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu id'ye sahip sipariş bulunamadı."));
        rentalRepository.delete(deleteRental);
        }
    }

