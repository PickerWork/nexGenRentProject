package com.nexgencarrental.nexGenCarRental.services.concretes;

import com.nexgencarrental.nexGenCarRental.core.utilities.mappers.ModelMapperService;
import com.nexgencarrental.nexGenCarRental.entities.concretes.Employee;
import com.nexgencarrental.nexGenCarRental.repositories.EmployeeRepository;
import com.nexgencarrental.nexGenCarRental.services.abstracts.EmployeeService;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.employee.AddEmployeeRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.employee.UpdateEmployeeRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.employee.GetEmployeeListResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.employee.GetEmployeeResponse;
import org.springframework.stereotype.Service;

@Service
public class EmployeeManager extends BaseManager<Employee, EmployeeRepository, GetEmployeeResponse, GetEmployeeListResponse, AddEmployeeRequest, UpdateEmployeeRequest> implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapperService modelMapperService;

    public EmployeeManager(EmployeeRepository repository, ModelMapperService modelMapperService, Class<GetEmployeeResponse> responseType, Class<GetEmployeeListResponse> listResponseType, Class<Employee> entityClass, Class<AddEmployeeRequest> requestType, Class<UpdateEmployeeRequest> updateRequestType, EmployeeRepository employeeRepository, ModelMapperService modelMapperService1) {
        super(repository, modelMapperService, responseType, listResponseType, entityClass, requestType, updateRequestType);
        this.employeeRepository = employeeRepository;
        this.modelMapperService = modelMapperService1;
    }

    @Override
    public void customAdd(AddEmployeeRequest addEmployeeRequest) {

    }

    @Override
    public void customUpdate(UpdateEmployeeRequest updateEmployeeRequest) {

    }
}
