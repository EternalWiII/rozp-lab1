package com.rozp.adv.Controllers;

import com.rozp.adv.DTO.LocationRequest;
import com.rozp.adv.Entities.Location;
import com.rozp.adv.Services.LocationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
@Data
public class LocationController {
    private final LocationService locationService;

    LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable UUID id, @Valid @RequestBody LocationRequest location) {
        return locationService.updateLocation(id, location);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLocation(@PathVariable UUID id) {
       locationService.deleteLocation(id);
    }
}
