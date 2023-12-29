package com.nexgencarrental.nexGenCarRental.services.concretes;

import com.nexgencarrental.nexGenCarRental.core.utilities.mappers.ModelMapperService;
import com.nexgencarrental.nexGenCarRental.entities.concretes.User;
import com.nexgencarrental.nexGenCarRental.repositories.UserRepository;
import com.nexgencarrental.nexGenCarRental.services.abstracts.UserService;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.user.AddUserRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.requests.user.UpdateUserRequest;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.user.GetUserListResponse;
import com.nexgencarrental.nexGenCarRental.services.dtos.responses.user.GetUserResponse;
import com.nexgencarrental.nexGenCarRental.services.rules.user.UserBusinessRulesService;
import org.springframework.stereotype.Service;

@Service
public class UserManager extends BaseManager<User, UserRepository, GetUserResponse, GetUserListResponse, AddUserRequest, UpdateUserRequest> implements UserService {
    private UserRepository userRepository;
    private UserBusinessRulesService userBusinessRulesService;
    private ModelMapperService modelMapperService;

    public UserManager(UserRepository repository, ModelMapperService modelMapperService, UserBusinessRulesService userBusinessRulesService) {
        super(repository, modelMapperService, GetUserResponse.class, GetUserListResponse.class, User.class, AddUserRequest.class, UpdateUserRequest.class);
    }


    @Override
    public void customAdd(AddUserRequest addUserRequest) {

    }

    @Override
    public void customUpdate(UpdateUserRequest updateUserRequest) {

    }
}
