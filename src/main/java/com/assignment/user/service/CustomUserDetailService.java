package com.assignment.user.service;

import com.assignment.user.exception.MyException;
import com.assignment.user.model.User;
import com.assignment.user.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    override the loadUserByUsername method with our user username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUserEntity= userRepository.findbyUsername(username);
        if(!optionalUserEntity.isPresent()) {
            System.out.println("user "+username+" not exists");
            throwException(400, 10001, "user with "+username+" not exists");
        }
        User user =optionalUserEntity.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
    }

    private void throwException(int httpStatus, Integer errorCode, String message) {
        throw new MyException(httpStatus, errorCode, message);
    }
}
