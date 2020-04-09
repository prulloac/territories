package com.prulloac.territoriesapi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prulloac.territoriesapi.service.ContinentService;
import com.prulloac.territoriesbase.error.ContinentNotFoundException;
import com.prulloac.territoriesbase.utils.aop.LogExecutionTime;
import com.prulloac.territoriesdto.dto.ContinentDTO;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author Prulloac
 */
@RestController
@RequestMapping(path = "continent")
@Slf4j
public class ContinentController {

	private final ContinentService continentService;

	public ContinentController(ContinentService continentService) {
		this.continentService = continentService;
	}

	@GetMapping(path = "iso/{code}")
	@LogExecutionTime
	public ResponseEntity<ContinentDTO> findContinentByIsoCode2(
			@PathVariable("code") String iso,
			@RequestParam(name = "lang", defaultValue = "en") String lang
	) {
		try {
			return ok(continentService.findContinentByIsoCode2(iso, lang));
		} catch (ContinentNotFoundException e) {
			log.error(e.getMessage(), e);
			return notFound().build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping
	@LogExecutionTime
	public ResponseEntity<List<ContinentDTO>> findAllContintentsFiltered(
			@RequestParam(name = "sort", required = false) String[] sortCombos,
			@RequestParam(name = "filters", required = false) String[] filters,
			@RequestParam(name = "lang", defaultValue = "en") String lang
	) {
		try {
			return ok(continentService.findAllContinentsFiltered(sortCombos, filters, lang));
		} catch (ContinentNotFoundException e) {
			log.error(e.getMessage(), e);
			return notFound().build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
