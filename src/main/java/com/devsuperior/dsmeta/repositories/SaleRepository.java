package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.ReportDTO( sa.id, sa.date, sa.amount, se.name ) FROM Sale sa " + 
				   "JOIN sa.seller se " +
				   "WHERE sa.date BETWEEN :minDate AND :maxDate AND " +
				   "      UPPER(se.name) LIKE UPPER( CONCAT('%', :name, '%') )")
	Page<ReportDTO> findToReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

	@Query(value = "SELECT new com.devsuperior.dsmeta.dto.SummaryDTO( se.name, SUM(sa.amount) ) FROM Seller se " +
				   "JOIN se.sales sa " +
				   "WHERE sa.date BETWEEN :minDate AND :maxDate " +
				   "GROUP BY se.name " +
				   "ORDER BY se.name")
	List<SummaryDTO> findToSummary(LocalDate minDate, LocalDate maxDate);
}