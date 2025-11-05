package com.rozp.adv.Services;

import com.rozp.adv.DTO.LocationRequest;
import com.rozp.adv.Entities.Location;
import com.rozp.adv.Entities.TravelPlan;
import com.rozp.adv.Exceptions.ResourceNotFoundException;
import com.rozp.adv.Repositories.LocationRepository;
import com.rozp.adv.Repositories.TravelPlanRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final EntityManager entityManager;

    LocationService(LocationRepository locationRepository, TravelPlanRepository travelPlanRepository, EntityManager entityManager) {
        this.locationRepository = locationRepository;
        this.travelPlanRepository = travelPlanRepository;
        this.entityManager = entityManager;
    }

    @Transactional
    public Location create(UUID planID, @Valid LocationRequest locationRequest) {
        TravelPlan parentPlan = travelPlanRepository.findById(planID)
                .orElseThrow(() -> new ResourceNotFoundException("Parent Travel Plan not found", planID));

        Location newLocation = new Location();

        newLocation.setTravelPlan(parentPlan);
        newLocation.setName(locationRequest.getName());
        newLocation.setAddress(locationRequest.getAddress());
        newLocation.setLatitude(locationRequest.getLatitude());
        newLocation.setLongitude(locationRequest.getLongitude());
        newLocation.setArrivalDate(locationRequest.getArrivalDate());
        newLocation.setDepartureDate(locationRequest.getDepartureDate());
        newLocation.setBudget(locationRequest.getBudget());
        newLocation.setNotes(locationRequest.getNotes());
        newLocation.setVersion(1);

        Location savedLocation = locationRepository.save(newLocation);

        entityManager.flush();

        entityManager.refresh(savedLocation);

        return savedLocation;
    }

    @Transactional
    public Location updateLocation(UUID locationId, @Valid LocationRequest request) {
        Location existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found ", locationId));

        existingLocation.setName(request.getName());
        existingLocation.setAddress(request.getAddress());
        existingLocation.setLatitude(request.getLatitude());
        existingLocation.setLongitude(request.getLongitude());
        existingLocation.setArrivalDate(request.getArrivalDate());
        existingLocation.setDepartureDate(request.getDepartureDate());
        existingLocation.setBudget(request.getBudget());
        existingLocation.setNotes(request.getNotes());
        existingLocation.setTravelPlanId(existingLocation.getTravelPlan().getId());

        return locationRepository.save(existingLocation);
    }

    @Transactional
    public void deleteLocation(UUID locationId) {
        Location locationToDelete = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID: ", locationId));

        UUID planId = locationToDelete.getTravelPlan().getId();
        int deletedOrder = locationToDelete.getVisitOrder();

        locationRepository.delete(locationToDelete);

        resequenceLocations(planId, deletedOrder);
    }

    private void resequenceLocations(UUID planId, int startOrder) {
        List<Location> locationsToResequence = locationRepository
                .findAllByTravelPlan_IdAndVisitOrderGreaterThanOrderByVisitOrderAsc(planId, startOrder);

        for (Location location : locationsToResequence) {
            location.setVisitOrder(location.getVisitOrder() - 1);
            locationRepository.save(location);
        }
    }
}
