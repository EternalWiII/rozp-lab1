package com.rozp.adv.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TravelPlanRequest {

    @NotBlank(message = "Title is required and cannot be empty")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    @DecimalMin(value = "0.01", message = "Budget must be positive.")
    @Digits(integer = 10, fraction = 2, message = "Budget can have a maximum of 2 decimal places.")
    private BigDecimal budget;

    @Pattern(regexp = "^[A-Z0-9\\s\\p{Punct}]+$",
            message = "Title must be in uppercase, containing only letters, numbers, spaces, and standard punctuation.")
    private String currency;
    private Boolean isPublic;

    @Min(1)
    private Integer version;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
