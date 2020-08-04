package com.assignment.user.controller;

import com.assignment.user.json.AuthRequest;
import com.assignment.user.json.TokenDTO;
import com.assignment.user.json.UserDTO;
import com.assignment.user.json.UserRegistrationDTO;
import com.assignment.user.service.UserService;
import com.assignment.user.service.UserTokenService;
import com.assignment.user.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class UserController {



    private UserService userService;
    private UserTokenService userTokenService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager,UserTokenService userTokenService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userTokenService=userTokenService;
    }

//  welcome user starting point for application after setup
    @RequestMapping(method = RequestMethod.GET,value = "/welcome")
    String Welcome(){
        return "Welcome UserApplication";
    }

    //    to create/register a new user
    @PostMapping ("/user/register")
    public UserDTO add(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO){

        UserDTO UserDTO=userService.add(userRegistrationDTO);
        return UserDTO;

    }

//    login user with username and password will return the JWT
    @PostMapping("/authenticate")
    public TokenDTO generateToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }

        TokenDTO tokenDTO=new TokenDTO( jwtUtil.generateToken(authRequest.getUsername()));
        return userTokenService.add(authRequest.getUsername(),tokenDTO);

    }

//    return the current logged in user info
    @GetMapping ("/user")
    public UserDTO get(Principal principal){
        UserDTO userDTO =userService.get(principal.getName());
        return userDTO;

    }
//    update the current logged in user info
    @PutMapping ("/user")
    public UserDTO update (@Valid @RequestBody UserDTO userDTO, Principal principal){
        return userService.update(userDTO,principal.getName());
    }
//    update the current logged in user info
    @DeleteMapping ("/user")
    public UserDTO delete (Principal principal){
        return userService.delete(principal.getName());
    }

   
}
