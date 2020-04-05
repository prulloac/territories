package com.prulloac.territoriesapi.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prulloac.territoriesbase.error.CountryNotFoundException;
import com.prulloac.territoriesdata.db.CountryDAO;
import com.prulloac.territoriesdata.model.Country;
import com.prulloac.territoriesdata.utils.PageRequestBuilder;
import com.prulloac.territoriesdata.utils.SpecificationBuilder;
import com.prulloac.territoriesdto.dto.CountryDTO;
import com.prulloac.territoriesdto.mapper.CountryMapper;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryService {

	private final CountryDAO countryDAO;
	private final CountryMapper countryMapper;

	public CountryService(CountryDAO countryDAO, CountryMapper countryMapper) {
		this.countryDAO = countryDAO;
		this.countryMapper = countryMapper;
	}

	private Function<Country, CountryDTO> toCountryDTO(String lang) {
		Locale locale = new Locale(lang);
		return country -> countryMapper.toCountryDTO(country, locale);
	}

	@Transactional(readOnly = true, timeout = 30)
	public List<CountryDTO> findCountriesByContinentIsoCode2(String iso2, String lang) {
		List<CountryDTO> countries = countryDAO.findAllByContinent_isoCode2(iso2)
				.parallelStream()
				.map(toCountryDTO(lang))
				.collect(Collectors.toList());
		if (countries.isEmpty()) {
			throw CountryNotFoundException.emptyCollection();
		}
		return countries;
	}

	@Transactional
	@Cacheable(value = "countries")
	public CountryDTO findCountryByIsoCode(String iso, String lang) {
		log.info("retrieving from db instead of cache");
		CountryDTO countryDTO;
		if (iso.matches("^\\D{2}$")) {
			log.info("iso2");
			countryDTO = findCountryByIsoCode2(iso, lang);
		} else if (iso.matches("^\\D{3}$")) {
			log.info("iso3");
			countryDTO = findCountryByIsoCode3(iso, lang);
		} else if (iso.matches("^\\d{3}$")) {
			log.info("isoNumeric");
			countryDTO = findCountryByIsoNumeric(iso, lang);
		} else {
			throw new RuntimeException();
		}
		log.info("{}", countryDTO);
		return countryDTO;
	}

	@Transactional(readOnly = true, timeout = 5)
	public CountryDTO findCountryByIsoCode2(String iso2, String lang) {
		return countryDAO.findByIsoCode2(iso2)
				.map(toCountryDTO(lang))
				.orElseThrow(() -> CountryNotFoundException.isoCodeNotFound(iso2))
				;
	}

	@Transactional(readOnly = true, timeout = 5)
	public CountryDTO findCountryByIsoCode3(String iso3, String lang) {
		return countryDAO.findByIsoCode3(iso3)
				.map(toCountryDTO(lang))
				.orElseThrow(() -> CountryNotFoundException.isoCodeNotFound(iso3))
				;
	}

	@Transactional(readOnly = true, timeout = 5)
	public CountryDTO findCountryByIsoNumeric(String isoNumeric, String lang) {
		return countryDAO.findByIsoNumeric(isoNumeric)
				.map(toCountryDTO(lang))
				.orElseThrow(() -> CountryNotFoundException.isoCodeNotFound(isoNumeric))
				;
	}

	public Page<Country> findAllCountriesPaginated(Integer page, Integer size, String[] sortCombos, String[] filters, String lang) {
		Specification specification = SpecificationBuilder.build(filters, Country.class);
		PageRequest pageRequest = PageRequestBuilder.buildRequest(page, size, sortCombos, Country.class);
		return countryDAO.findAll(specification, pageRequest);
	}

}
