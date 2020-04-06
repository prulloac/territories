package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prulloac.territoriesdata.model.Country;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Prulloac
 */
public interface CountryDAO extends JpaRepository<Country, UUID>, JpaSpecificationExecutor<Country> {

	Optional<Country> findByIsoCode2(String iso2);

	Optional<Country> findByIsoCode3(String iso3);

	Optional<Country> findByIsoNumeric(String isoNumeric);

	List<Country> findAllByContinents_isoCode2(String continent_isoCode2);
}
