package com.example.Finance.repository;

import com.example.Finance.model.FinancialEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface FinancialEntryRepository extends JpaRepository<FinancialEntry, Long> {
    // Simple query methods
    List<FinancialEntry> findByCategory(String category);
    List<FinancialEntry> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(f.amount) FROM FinancialEntry f WHERE f.type = 'INCOME'")
    Double getTotalIncome();

    @Query("SELECT SUM(f.amount) FROM FinancialEntry f WHERE f.type = 'EXPENSE'")
    Double getTotalExpenses();
}