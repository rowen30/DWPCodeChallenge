package com.app.test.controller;

import com.app.test.dto.UserDTO;
import com.app.test.facade.UserFacade;
import com.app.test.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserFacade userFacade;

    @RequestMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam String city, @RequestParam int radius) {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> users = userFacade.getUsersOfCityAndWithinRadius(city, radius);
        if (!CollectionUtils.isEmpty(users)) {
            ModelMapper modelMapper = new ModelMapper();
            for (User user : users) {
                userDTOS.add(modelMapper.map(user, UserDTO.class));
            }
        }
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
}
