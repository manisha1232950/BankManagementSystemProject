package com.BankManagementSystemProject.serviceimpl;


import com.BankManagementSystemProject.entity.User;
import com.BankManagementSystemProject.exceptionhandling.ResourceNotFoundException;
import com.BankManagementSystemProject.payload.UserDto;
import com.BankManagementSystemProject.repository.UserRepository;
import com.BankManagementSystemProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    //USerServiceImpl par click karo or add umimplement method se sare methods import kar lo .

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;

    //========================================================================================
    //post method
    @Override
    public UserDto createUser(UserDto userDto) {

        // DTO → Entity
        User user = this.modelMapper.map(userDto, User.class);

        // Save to DB
        User addedUser = this.userRepo.save(user);

        // Entity → DTO
        return this.modelMapper.map(addedUser, UserDto.class);
    }

    //========================================================================================
//updatemethod
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        // Find existing user
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // DTO → Entity (important part 🔥)
        this.modelMapper.map(userDto, user);

        // Save updated data
        User updatedUser = this.userRepo.save(user);

        // Entity → DTO
        return this.modelMapper.map(updatedUser, UserDto.class);
    }

    //========================================================================================
    //getUserById()
    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return this.modelMapper.map(user, UserDto.class);
    }

    //========================================================================================
    //getAllUsers()
    @Override
    public List<UserDto> getAllUsers() {

        List<User> users = this.userRepo.findAll();

        List<UserDto> userDtos = users.stream()
                .map(user -> this.modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return userDtos;
    }

    //========================================================================================
    //deleteUser()
    @Override

    public void deleteUser(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        this.userRepo.delete(user);
    }
}

