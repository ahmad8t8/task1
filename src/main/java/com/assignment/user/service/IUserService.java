package com.assignment.user.service;


import com.assignment.user.json.UserDTO;
import com.assignment.user.json.UserRegistrationDTO;


public interface IUserService {
    public UserDTO add(UserRegistrationDTO user);
    public UserDTO get(String username);
    public Iterable<UserDTO> get();
    public UserDTO update(UserDTO updatedUser,String username);
    public UserDTO delete(String username);
}
