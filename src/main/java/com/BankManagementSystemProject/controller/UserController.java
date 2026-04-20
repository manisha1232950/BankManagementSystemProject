package com.BankManagementSystemProject.controller;

import com.BankManagementSystemProject.payload.UserDto;
import com.BankManagementSystemProject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
//==================================================================================================
    //1.POST - Create user
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto)
    {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

    }
//==================================================================================================
    //2.Put - update user

    @PutMapping("/{userId}")
    ResponseEntity<UserDto> updateUser ( @RequestBody UserDto userDto,
                                        @PathVariable("userId") Integer userId){
        UserDto updatedUser = this.userService.updateUser (userDto, userId); //Controller → Service call
        return ResponseEntity.ok(updatedUser);

    }
//==================================================================================================

    //GET user get for multiple user
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        List<UserDto> users = this.userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
//==================================================================================================

//GET user get for single user

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {

        UserDto userDto = this.userService.getUserById(userId);

        return ResponseEntity.ok(userDto);
    }
    //==================================================================================================
    //3.DELETE -delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {

        this.userService.deleteUser(userId);

        return ResponseEntity.ok("User deleted successfully");
    }

    //==================================================================================================

}

