package com.nexgencarrental.nexGenCarRental.services.concretes;

import com.nexgencarrental.nexGenCarRental.core.utilities.mappers.ModelMapperService;
import com.nexgencarrental.nexGenCarRental.entities.Car;
import com.nexgencarrental.nexGenCarRental.entities.Color;
import com.nexgencarrental.nexGenCarRental.entities.Model;
import com.nexgencarrental.nexGenCarRental.repositories.CarRepository;
import com.nexgencarrental.nexGenCarRental.repositories.ColorRepository;
import com.nexgencarrental.nexGenCarRental.repositories.ModelRepository;
import com.nexgencarrental.nexGenCarRental.services.abstracts.ColorService;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.color.AddColorRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.color.UpdateColorRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.color.GetColorListResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.color.GetColorResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.model.GetModelResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorManager implements ColorService {
    private final ColorRepository colorRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetColorListResponse> getAll() {
        return colorRepository.findAll().stream()
                .map(car -> modelMapperService.forResponse()
                        .map(car, GetColorListResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetColorResponse getColorById(int id) {
        Color color = colorRepository.findById(id).orElseThrow(() ->
                new RuntimeException(id + " girdiğiniz id'ye sahip renk sistemde bulunamıyor."));

        return modelMapperService.forResponse().map(color, GetColorResponse.class);
    }

    @Override
    public void add(AddColorRequest addColorRequest) {

        // Aynı rengi 2.kez ekleme kontrolü
        if (colorRepository.existsByName(addColorRequest.getName().trim().replaceAll("\\s", ""))){
            throw new RuntimeException("Sistemde bu renk bulunuyor,farklı bir renk giriniz.");
        }

        Color addColor = modelMapperService.forRequest().map(addColorRequest, Color.class);

        colorRepository.save(addColor);
    }

    @Override
    public void update(UpdateColorRequest updateColorRequest) {

        if(!(colorRepository.existsById(updateColorRequest.getId()))){
            throw new RuntimeException(updateColorRequest.getId()+" nolu id'ye sahip renk bulunmamaktadır.");
        }

        //Değiştirmek istenen rengin adını kontrol eder.

        Optional<Color> existingColorOptional = colorRepository.findById(updateColorRequest.getId());
        Color existingColor = existingColorOptional.get();
        String newColor = updateColorRequest.getName().trim().replaceAll("\s", "");

        //Id kontrol eder renk varsa hata fırlatır yoksa ekler.

        if (!existingColor.getName().equals(newColor) && colorRepository.existsByName(newColor)) {
            throw new RuntimeException("Renk sistemimizde mevcut lütfen farklı bir renk deneyin.");
        }


        Color color = this.modelMapperService.forRequest()
                .map(updateColorRequest, Color.class);

        color.setName(newColor);

        colorRepository.save(color);
    }

    @Override
    public void delete(int id) {
        Color deleteColor = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu id'ye sahip renk bulunamadı."));
        colorRepository.delete(deleteColor);
    }

}
