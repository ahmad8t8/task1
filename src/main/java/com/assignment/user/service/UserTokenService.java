package com.assignment.user.service;


import com.assignment.user.exception.MyException;
import com.assignment.user.json.TokenDTO;
import com.assignment.user.model.User;
import com.assignment.user.model.UserToken;
import com.assignment.user.persistence.UserRepository;
import com.assignment.user.persistence.UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
//implementation of add and delete of UserToken

@Service
public class UserTokenService {
    @Autowired
    UserTokenRepository userTokenRepository;
    @Autowired
    UserRepository userRepository;

    public UserTokenService() {
    }

    public TokenDTO add(String username, TokenDTO tokenDTO) {


        Optional<User> optionalUserEntity= userRepository.findbyUsername(username);
        if(!optionalUserEntity.isPresent()) {
            System.out.println("user "+username+" not exists");
            throwException(400, 10001, "user with username "+username+" not exists");
        }
        User user =optionalUserEntity.get();

        Optional<UserToken> optionalUserTokenEntity= userTokenRepository.findbyUser(user);
        UserToken userToken=new UserToken(user,tokenDTO.getToken());

        if(optionalUserTokenEntity.isPresent()) {
            UserToken savedUserToken =optionalUserTokenEntity.get();
            userToken.setId(savedUserToken.getId());
        }

        UserToken userTokenSaved = userTokenRepository.save(userToken);

        return new TokenDTO(userTokenSaved.getToken());
    }

    public void delete(User user){

        Optional<UserToken> optionalUserTokenEntity= userTokenRepository.findbyUser(user);
        if(optionalUserTokenEntity.isPresent()) {
            UserToken savedUserToken =optionalUserTokenEntity.get();
            userTokenRepository.delete(savedUserToken);
        }
    }

    private void throwException(int httpStatus, Integer errorCode, String message) {
        throw new MyException(httpStatus, errorCode, message);
    }

}
