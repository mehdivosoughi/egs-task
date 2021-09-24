package com.example.egstask.model.repository;

import com.example.egstask.model.entity.Rating;
import com.example.egstask.model.entity.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rating, RatingKey> {
    Optional<Rating> findById(RatingKey ratingKey);
}
