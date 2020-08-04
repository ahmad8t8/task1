package com.assignment.user.persistence;

import com.assignment.user.model.User;
import com.assignment.user.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
    @Query("SELECT e from UserToken e where e.user =:user")
    Optional<UserToken> findbyUser(@Param(value = "user")User user);
}
