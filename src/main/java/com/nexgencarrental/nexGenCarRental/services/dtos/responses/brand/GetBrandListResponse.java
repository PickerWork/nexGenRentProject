package com.nexgencarrental.nexGenCarRental.services.dtos.responses.brand;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetBrandListResponse {
    private int id;
    private String name;

    public GetBrandListResponse() {
    }
}