package com.heymart.supermarket.repository;

import com.heymart.supermarket.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, Long> {
    Optional<Supermarket> findByUrlName(String urlName);
}
