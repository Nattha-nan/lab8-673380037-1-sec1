package com.example.lab8_6733800371_sec1.repository;

import com.example.lab8_6733800371_sec1.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}