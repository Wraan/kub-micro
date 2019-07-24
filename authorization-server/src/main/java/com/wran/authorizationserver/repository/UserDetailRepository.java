package com.wran.authorizationserver.repository;

import com.wran.authorizationserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
