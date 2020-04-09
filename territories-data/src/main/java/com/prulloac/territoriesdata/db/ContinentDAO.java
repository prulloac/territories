package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prulloac.territoriesdata.model.Continent;

import java.util.Optional;

/**
 * @author Prulloac
 */
public interface ContinentDAO extends JpaRepository<Continent, Long>, JpaSpecificationExecutor<Continent> {

	/**
	 * Searches for a specific {@link Continent} with the given iso code
	 * @param iso represents the iso code used as a filter of our where clause.
	 * @return Optional<Continent>
	 */
	Optional<Continent> findByIsoCode2(String iso);

}
