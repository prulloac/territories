package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.prulloac.territoriesdata.model.Continent;
import com.prulloac.territoriesdata.model.Country;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Prulloac
 */
public interface CountryDAO extends JpaRepository<Country, UUID>, JpaSpecificationExecutor<Country> {

	/**
 	 * Searches for a specific {@link Country} with the given iso code
	 * @see <a href="https://www.iso.org/iso-3166-country-codes.html">ISO 3166</a>
	 * @param iso2 country Alpha-2 code
	 * @return Optional<Country>
	 */
	Optional<Country> findByIsoCode2(String iso2);

	/**
	 * Searches for a specific {@link Country} with the given iso code
	 * @see <a href="https://www.iso.org/iso-3166-country-codes.html">ISO 3166</a>
	 * @param iso3 country Alpha-3 code
	 * @return Optional<Country>
	 */
	Optional<Country> findByIsoCode3(String iso3);

	/**
	 * Searches for a specific {@link Country} with the given iso code
	 * @see <a href="https://www.iso.org/iso-3166-country-codes.html">ISO 3166</a>
	 * @param isoNumeric country Numeric code
	 * @return Optional<Country>
	 */
	Optional<Country> findByIsoNumeric(Integer isoNumeric);

	/**
	 * Searches for every country related with a continent found by its iso code
	 * @param continentIsoCode2 continent iso code
	 * @return List<Country>
	 */
	@Query("select c from Country c join c.continents cc where cc.isoCode2 = ?1")
	List<Country> findAllByContinentsIsoCode2(String continentIsoCode2);

	/**
	 * Searches for every country related with a given continent
	 * @param continent continent
	 * @return List<Country>
	 */
	@Query("select c from Country c where ?1 in (c.continents)")
	List<Country> findAllByContinent(Continent continent);
}
