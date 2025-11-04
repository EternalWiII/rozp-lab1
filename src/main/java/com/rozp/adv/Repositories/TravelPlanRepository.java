package com.rozp.adv.Repositories;

import com.rozp.adv.Entities.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface TravelPlanRepository extends
        JpaRepository<TravelPlan, UUID>,
        PagingAndSortingRepository<TravelPlan, UUID>
{

}
