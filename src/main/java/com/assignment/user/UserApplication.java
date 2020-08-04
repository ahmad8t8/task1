package com.assignment.user;
import com.assignment.user.model.User;
import com.assignment.user.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class UserApplication {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //seed default users
//	@PostConstruct
//	public void initUser(){
//		List<User> users = Stream.of(
//				new User("user1",bCryptPasswordEncoder.encode("pwd1"),"user1","user1@gmail.com","0123456489")
//		).collect(Collectors.toList());
//		userRepository.saveAll(users);
//	}

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
