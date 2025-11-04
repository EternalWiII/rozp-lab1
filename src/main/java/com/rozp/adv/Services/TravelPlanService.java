package com.rozp.adv.Services;

import com.rozp.adv.DTO.TravelPlanRequest;
import com.rozp.adv.Entities.TravelPlan;
import com.rozp.adv.Exceptions.ConcurrencyConflictException;
import com.rozp.adv.Exceptions.ResourceNotFoundException;
import com.rozp.adv.Repositories.TravelPlanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final EntityManager entityManager;

    public TravelPlanService(TravelPlanRepository travelPlanRepository, EntityManager entityManager) {
        this.travelPlanRepository = travelPlanRepository;
        this.entityManager = entityManager;
    }

    public Page<TravelPlan> findAll(Pageable pageable) {
        return travelPlanRepository.findAll(pageable);
    }

    @Transactional
    public TravelPlan create (TravelPlanRequest travelPlanRequest) {
        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setTitle(travelPlanRequest.getTitle());
        travelPlan.setDescription(travelPlanRequest.getDescription());
        travelPlan.setStartDate(travelPlanRequest.getStartDate());
        travelPlan.setEndDate(travelPlanRequest.getEndDate());
        travelPlan.setBudget(travelPlanRequest.getBudget());
        travelPlan.setCurrency(travelPlanRequest.getCurrency());
        if (travelPlanRequest.getPublic() == null) {
            travelPlan.setIsPublic(true);
        } else {
            travelPlan.setIsPublic(travelPlanRequest.getPublic());
        }
        travelPlan.setVersion(1);

        TravelPlan savedPlan = travelPlanRepository.save(travelPlan);

        entityManager.flush();

        entityManager.refresh(savedPlan);

        return savedPlan;
    }

    public TravelPlan findById(UUID id) {

        entityManager.clear();
        return travelPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel Plan Not Found", id));
    }

    @Transactional
    public TravelPlan update(UUID id, @Valid TravelPlanRequest request) {
        TravelPlan existingTravelPlan = travelPlanRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Travel Plan Not Found", id));

        if (request.getVersion() == null){
            throw new IllegalArgumentException("Version Not Found");
        }

        if (request.getVersion() == null || !request.getVersion().equals(existingTravelPlan.getVersion())) {
            throw new ConcurrencyConflictException(
                    "Plan modified concurrently.",
                    existingTravelPlan.getVersion()
            );
        }

        existingTravelPlan.setTitle(request.getTitle());
        existingTravelPlan.setDescription(request.getDescription());
        existingTravelPlan.setStartDate(request.getStartDate());
        existingTravelPlan.setEndDate(request.getEndDate());
        existingTravelPlan.setBudget(request.getBudget());
        existingTravelPlan.setCurrency(request.getCurrency());
        if (request.getPublic() != null) {
            existingTravelPlan.setIsPublic(request.getPublic());
        } else {
            existingTravelPlan.setIsPublic(false);
        }
        existingTravelPlan.setVersion(existingTravelPlan.getVersion() + 1);

        return travelPlanRepository.save(existingTravelPlan);
    }

    @Transactional
    public void delete(UUID id) {
        if (findById(id) == null) {
            throw new ResourceNotFoundException("Travel Plan Not Found", id);
        }

        System.out.println("ID: " + id);

        travelPlanRepository.deleteById(id);
    }

}
