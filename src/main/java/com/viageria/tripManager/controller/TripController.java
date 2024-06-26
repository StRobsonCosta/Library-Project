package com.viageria.tripManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viageria.tripManager.entity.TripEntity;
import com.viageria.tripManager.service.TripService;

@RestController
@RequestMapping("/travels")
public class TripController {
	
	@Autowired
	private TripService tripService;
	
	@GetMapping
	public List<TripEntity> getAllTrips() {
		return tripService.getAllTrips();
	}
	
	@PostMapping
	public TripEntity createTrip(@RequestBody TripEntity trip) {
		return tripService.saveTrip(trip);
	}
	
}
