package com.example.egstask.model.repository;

import com.example.egstask.model.entity.EgsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<EgsUser, Long> {

    Optional<EgsUser> findByUsername(String username);

}
