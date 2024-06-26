package com.viageria.tripManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viageria.tripManager.entity.TripEntity;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long>{

}
