package com.devsuperior.dsmeta.services;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import com.devsuperior.dsmeta.services.exceptions.ResourceNotFoundException;

@Service
public class SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {

		Optional<Sale> result = saleRepository.findById(id);
		Sale entity = result.get();

		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<ReportDTO> findToReport(String minDateParam, String maxDateParam, String nameParam, Pageable pageable) {

		LocalDate maxDate = consisteMaxDate(maxDateParam);
		LocalDate minDate = consisteMinDate(minDateParam, maxDate);

		Page<ReportDTO> listaReportDTO = saleRepository.findToReport(minDate, maxDate, nameParam, pageable);

		return listaReportDTO	;
	}

	@Transactional(readOnly = true)
	public List<SummaryDTO> findToSummary(String minDateParam, String maxDateParam) {

		LocalDate maxDate = consisteMaxDate(maxDateParam);
		LocalDate minDate = consisteMinDate(minDateParam, maxDate);

		List<SummaryDTO> listaSummaryDTO = saleRepository.findToSummary(minDate, maxDate);

		return listaSummaryDTO;
	}

	private LocalDate consisteMaxDate(String maxDateParam) {

		try {

			LocalDate datahoje = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

			LocalDate maxDate = datahoje;
			if(maxDateParam!=null && !maxDateParam.trim().isEmpty()) {
				maxDate = LocalDate.parse(maxDateParam);
			}

			return maxDate;

		} catch (DateTimeException e) {
			throw new ResourceNotFoundException("Data inválida para: 'maxDate'");
		}
	}

	private LocalDate consisteMinDate(String minDateParam, LocalDate maxDate) {

		try {
			LocalDate minDate = maxDate.minusYears(1L);
			if (minDateParam != null && !minDateParam.trim().isEmpty()) {
				minDate = LocalDate.parse(minDateParam);
			}

			return minDate;
		} catch (DateTimeException e) {
			throw new ResourceNotFoundException("Data inválida para: 'minDate'");
		}
	}
}