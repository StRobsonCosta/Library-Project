package com.viageria.tripManager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.viageria.tripManager.entity.TripEntity;
import com.viageria.tripManager.repository.TripRepository;

@Service
public class TripService {
	
	private static final String TOPIC = "travelTopic";
	
	@Autowired
	private TripRepository repository;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public List<TripEntity> getAllTrips() {
		return repository.findAll();
	}
	
	public TripEntity saveTrip(TripEntity trip) {
		TripEntity savedTrip = repository.save(trip);
		kafkaTemplate.send(TOPIC, "New Trip Created: " + trip.getDestination());
		
		return savedTrip;
	}

}
