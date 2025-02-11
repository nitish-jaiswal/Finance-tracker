package com.example.Finance.controller;

import com.example.Finance.model.FinancialEntry;
import com.example.Finance.service.FinancialEntryService;
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


        return service.getAllEntries();
    }
}