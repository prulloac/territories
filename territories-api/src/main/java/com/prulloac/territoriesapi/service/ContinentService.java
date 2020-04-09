package com.prulloac.territoriesapi.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.prulloac.territoriesbase.error.ContinentNotFoundException;
import com.prulloac.territoriesbase.utils.aop.LogExecutionTime;
import com.prulloac.territoriesdata.db.ContinentDAO;
import com.prulloac.territoriesdata.model.Continent;
import com.prulloac.territoriesdata.utils.PageRequestBuilder;
import com.prulloac.territoriesdata.utils.SpecificationBuilder;
import com.prulloac.territoriesdto.dto.ContinentDTO;
import com.prulloac.territoriesdto.mapper.ContinentMapper;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContinentService {

	private final ContinentDAO continentDAO;
	private final ContinentMapper continentMapper;

	public ContinentService(ContinentDAO continentDAO, ContinentMapper continentMapper) {
		this.continentDAO = continentDAO;
		this.continentMapper = continentMapper;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public Function<Continent, ContinentDTO> toContinentDTO(String lang) {
		Locale locale = new Locale(lang);
		return continent -> continentMapper.toContinentDTO(continent, locale);
	}


	@Transactional(readOnly = true, timeout = 5)
	@Cacheable(value = "continents")
	@LogExecutionTime
	public ContinentDTO findContinentByIsoCode2(String iso, String lang) {
		return continentDAO.findByIsoCode2(iso)
				.map(toContinentDTO(lang))
				.orElseThrow(() -> ContinentNotFoundException.isoCodeNotFound(iso));
	}

	@Transactional(readOnly = true)
	public List<ContinentDTO> findAllContinentsFiltered(String[] sortCombos, String[] filters, String lang) {
		Specification<Continent> specification = new SpecificationBuilder<>(Continent.class).build(filters);
		Sort sort = PageRequestBuilder.buildSort(sortCombos, Continent.class);
		return continentDAO.findAll(specification, sort)
				.stream()
				.map(toContinentDTO(lang))
				.collect(Collectors.toList());
	}
}
