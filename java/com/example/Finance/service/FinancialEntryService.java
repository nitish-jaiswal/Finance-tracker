package com.example.Finance.service;

import com.example.Finance.model.FinancialEntry;
import com.example.Finance.repository.FinancialEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FinancialEntryService {

    @Autowired
    private FinancialEntryRepository repository;

    // Create entry
    public FinancialEntry createEntry(FinancialEntry entry) {
        // Simple validation
        if (entry.getAmount() <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }
        if (!entry.getType().equals("INCOME") && !entry.getType().equals("EXPENSE")) {
            throw new RuntimeException("Type must be INCOME or EXPENSE");
        }
        return repository.save(entry);
    }

    // Get all entries
    public List<FinancialEntry> getAllEntries() {
        return repository.findAll();
    }
}