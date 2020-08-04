package com.assignment.user.persistence;

import com.assignment.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT e from User e where e.username =:username")
    Optional<User> findbyUsername(@Param(value = "username")String username);

}
