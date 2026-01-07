package com.rozp.adv.Controllers;

import com.rozp.adv.DTO.LocationRequest;
import com.rozp.adv.DTO.TravelPlanRequest;
import com.rozp.adv.Entities.Location;
import com.rozp.adv.Entities.TravelPlan;
import com.rozp.adv.Services.LocationService;
import com.rozp.adv.Services.TravelPlanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/travel-plans")
public class TravelPlanController {
    private final TravelPlanService travelPlanService;
    private final LocationService locationService;

    public TravelPlanController(TravelPlanService travelPlanService, LocationService locationService) {
        this.travelPlanService = travelPlanService;
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<TravelPlan>> getAllTravelPlans(Pageable pageable) {
        // Spring все одно обробить параметри ?page=0&size=10
        // Але ми витягнемо з Page тільки список елементів .getContent()
        List<TravelPlan> travelPlans = travelPlanService.findAll(pageable).getContent();
        return ResponseEntity.ok(travelPlans);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TravelPlan createTravelPlan(@Valid @RequestBody TravelPlanRequest request) { // <-- ADDED @Valid
        return travelPlanService.create(request);
    }

    @GetMapping("/{id}")
    public TravelPlan getTravelPlanById(@PathVariable UUID id) {
        return travelPlanService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelPlan> updateTravelPlan(@PathVariable UUID id, @Valid @RequestBody TravelPlanRequest request) { // <-- ADDED @Valid
        TravelPlan updatedPlan = travelPlanService.update(id, request);
        return ResponseEntity.ok(updatedPlan);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteTravelPlan(@PathVariable UUID id) {
        travelPlanService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/locations")
    @ResponseStatus(HttpStatus.CREATED)
    public Location addLocationToPlan(@PathVariable("id") UUID planId, @Valid @RequestBody LocationRequest request) { // <-- ADDED @Valid
        return locationService.create(planId, request);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFoundException(NoSuchElementException ex) {
        return ex.getMessage();
    }
}