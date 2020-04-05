package com.prulloac.territoriesapi.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prulloac.territoriesapi.service.CountryService;
import com.prulloac.territoriesapi.utils.aop.LogExecutionTime;
import com.prulloac.territoriesbase.error.CountryNotFoundException;
import com.prulloac.territoriesdata.model.Country;
import com.prulloac.territoriesdto.dto.CountryDTO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author Prulloac
 */
@RestController
@RequestMapping(path = "country")
@Slf4j
public class CountryController {

	private final CountryService countryService;

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping(path = "iso/{code}")
	@LogExecutionTime
	public ResponseEntity<CountryDTO> findCountryByIsoCode2(
			@PathVariable("code") String iso,
			@RequestParam(name = "lang", defaultValue = "en") String lang
	) {
		try {
			return ok(countryService.findCountryByIsoCode(iso, lang));
		} catch (CountryNotFoundException e) {
			log.error(e.getMessage(), e);
			return notFound().build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(path = "continent/{iso2}")
	public ResponseEntity<List<CountryDTO>> findCountriesByContinentIsoCode2(
			@PathVariable("iso2") String iso2,
			@RequestParam(name = "lang", defaultValue = "en") String lang
	) {
		try {
			return ok(countryService.findCountriesByContinentIsoCode2(iso2, lang));
		} catch (CountryNotFoundException e) {
			return notFound().build();
		} catch (Exception e) {
			return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	@LogExecutionTime
	public ResponseEntity<Page<Country>> findAllCountriesPaginated(
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "sort", required = false) String[] sortCombos,
			@RequestParam(name = "filters", required = false) String[] filters,
			@RequestParam(name = "lang", defaultValue = "en") String lang
	) {
		try {
			return ok(countryService.findAllCountriesPaginated(page, size, sortCombos, filters, lang));
		} catch (CountryNotFoundException e) {
			return notFound().build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
