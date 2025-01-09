package com.menna.LetsDoIt.features.common.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.menna.LetsDoIt.features.common.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
     Optional<User> findByEmail(String email);
}
