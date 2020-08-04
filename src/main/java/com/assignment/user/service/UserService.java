package com.assignment.user.service;
import com.assignment.user.exception.MyException;
import com.assignment.user.json.UserDTO;
import com.assignment.user.json.UserRegistrationDTO;
import com.assignment.user.model.User;
import com.assignment.user.persistence.UserRepository;
import com.assignment.user.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//implemention of CURD oprations logic of user in UserService
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService() {
    }

    @Override
    public UserDTO add(UserRegistrationDTO userRegistrationDTO) {
        User user = ObjectMapperUtils.map(userRegistrationDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
        User userEntity=userRepository.save(user);
        return ObjectMapperUtils.map(userEntity, UserDTO.class);
    }

    @Override
    public UserDTO get(String username) {
        Optional<User> optionalUserEntity= userRepository.findbyUsername(username);
        if(!optionalUserEntity.isPresent()) {
            System.out.println("user "+username+" not exists");
            throwException(400, 10001, "user with username "+username+" not exists");
        }
        User user =optionalUserEntity.get();
        return ObjectMapperUtils.map(user, UserDTO.class);
    }

    @Override
    public Iterable<UserDTO> get() {
        return ObjectMapperUtils.mapAll(userRepository.findAll(),UserDTO.class);

    }

    @Override
    public UserDTO update(UserDTO updatedUserDTO,String username) {
        Optional<User> optionalUserEntity= userRepository.findbyUsername(username);
        if(!optionalUserEntity.isPresent()) {
            System.out.println("user "+username+" not exists");
            throwException(400, 10001, "user with username "+username+" not exists");
        }
        User savedUser =optionalUserEntity.get();
        User updatedUser = ObjectMapperUtils.DTOtoUser(updatedUserDTO, savedUser);
        User user =userRepository.save(updatedUser);
        return ObjectMapperUtils.mapUserDTO(user);
    }

    @Override
    public UserDTO delete(String username) {
        Optional<User> optionalUserEntity= userRepository.findbyUsername(username);
        if(!optionalUserEntity.isPresent()) {
            System.out.println("user "+username+" not exists");
            throwException(400, 10001, "user with "+username+" not exists");
        }
        User user =optionalUserEntity.get();
        userTokenService.delete(user);
        userRepository.delete(optionalUserEntity.get());

        return ObjectMapperUtils.map(user, UserDTO.class);
    }

    private void throwException(int httpStatus, Integer errorCode, String message) {
        throw new MyException(httpStatus, errorCode, message);
    }

}
