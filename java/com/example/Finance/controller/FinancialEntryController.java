package com.example.Finance.controller;

import com.example.Finance.model.FinancialEntry;
import com.example.Finance.service.FinancialEntryService;
import com.example.Finance.dto.FinancialSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FinancialEntryController {

    @Autowired
    private FinancialEntryService service;

    // Create
    @PostMapping("/entries")
    public FinancialEntry createEntry(@RequestBody FinancialEntry entry) {
        return service.createEntry(entry);
    }

    // Read all with filters
    @GetMapping("/entries")
    public List<FinancialEntry> getEntries(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (category != null) {
            return service.getEntriesByCategory(category);
        }
        if (startDate != null && endDate != null) {
            return service.getEntriesByDateRange(startDate, endDate);
        }
        return service.getAllEntries();
    }

    // Read one
    @GetMapping("/entries/{id}")
    public FinancialEntry getEntry(@PathVariable Long id) {
        return service.getEntryById(id);
    }

    // Update
    @PutMapping("/entries/{id}")
    public FinancialEntry updateEntry(@PathVariable Long id, @RequestBody FinancialEntry entry) {
        return service.updateEntry(id, entry);
    }

    // Delete
    @DeleteMapping("/entries/{id}")
    public void deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
    }

    // Get summary
    @GetMapping("/summary")
    public FinancialSummaryDTO getSummary() {
        return service.getSummary();
    }
}