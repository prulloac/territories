package com.prulloac.territoriesdto.mapper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.prulloac.territoriesdata.model.Continent;
import com.prulloac.territoriesdata.model.AbstractGeographicLocation;
import com.prulloac.territoriesdto.dto.ContinentDTO;

import java.util.Locale;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Component
@Slf4j
public class ContinentMapper extends AbstractMapper {

	@Transactional(propagation = Propagation.MANDATORY)
	public ContinentDTO toContinentDTO(Continent continent, Locale locale) {
		Assert.notNull(continent, "base object cannot be null");
		ContinentDTO continentDTO = new ContinentDTO();
		continentDTO.setId(continent.getId());
		continentDTO.setIsoCode2(continent.getIsoCode2());
		String name = htmlEscape(getLocalizedMessage("continent."+continent.getIsoCode2(), locale));
		continentDTO.setName(name);
		continentDTO.setCountries(continent.getCountries().size());
		continentDTO.setCountriesIsoCodes(continent.getCountries()
				.stream()
				.map(AbstractGeographicLocation::getIsoCode2)
				.collect(Collectors.toList()));
		return continentDTO;
	}

}
