package com.example.Finance.service;

import com.example.Finance.model.FinancialEntry;
import com.example.Finance.repository.FinancialEntryRepository;
import com.example.Finance.dto.FinancialSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialEntryService {

    @Autowired
    private FinancialEntryRepository repository;

    public FinancialEntry createEntry(FinancialEntry entry) {
        validateEntry(entry);
        return repository.save(entry);
    }

    public List<FinancialEntry> getAllEntries() {
        return repository.findAll();
    }

    public FinancialEntry getEntryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found with id: " + id));
    }

    public FinancialEntry updateEntry(Long id, FinancialEntry entry) {
        validateEntry(entry);
        FinancialEntry existingEntry = getEntryById(id);
        existingEntry.setAmount(entry.getAmount());
        existingEntry.setCategory(entry.getCategory());
        existingEntry.setDate(entry.getDate());
        existingEntry.setDescription(entry.getDescription());
        existingEntry.setType(entry.getType());
        return repository.save(existingEntry);
    }

    public void deleteEntry(Long id) {
        repository.deleteById(id);
    }

    public List<FinancialEntry> getEntriesByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<FinancialEntry> getEntriesByDateRange(LocalDate startDate, LocalDate endDate) {
        return repository.findByDateBetween(startDate, endDate);
    }

    public FinancialSummaryDTO getSummary() {
        FinancialSummaryDTO summary = new FinancialSummaryDTO();
        Double totalIncome = repository.getTotalIncome();
        Double totalExpenses = repository.getTotalExpenses();

        summary.setTotalIncome(totalIncome != null ? totalIncome : 0.0);
        summary.setTotalExpenses(totalExpenses != null ? totalExpenses : 0.0);
        summary.setBalance(summary.getTotalIncome() - summary.getTotalExpenses());

        return summary;
    }

    private void validateEntry(FinancialEntry entry) {
        if (entry.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (entry.getType() == null || (!entry.getType().equals("INCOME") && !entry.getType().equals("EXPENSE"))) {
            throw new IllegalArgumentException("Type must be either INCOME or EXPENSE");
        }
        if (entry.getCategory() == null || entry.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
    }
}