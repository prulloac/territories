package com.prulloac.territoriesdto.mapper;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.prulloac.territoriesdata.model.Continent;
import com.prulloac.territoriesdata.model.Country;
import com.prulloac.territoriesdto.dto.CountryDTO;

import java.util.Locale;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Component
@Slf4j
public class CountryMapper extends AbstractMapper {

	@Transactional(propagation = Propagation.MANDATORY)
	public CountryDTO toCountryDTO(Country country, Locale locale) {
		Assert.notNull(country, "base object cannot be null");
		CountryDTO countryDTO = new CountryDTO();
		countryDTO.setId(country.getId().toString());
		countryDTO.setIsoCode2(country.getIsoCode2());
		countryDTO.setIsoCode3(country.getIsoCode3());
		countryDTO.setIsoNumeric(country.getIsoNumeric());
		countryDTO.setInternetCctld(country.getInternetCctld());
		countryDTO.setPhonePrefix(country.getPhonePrefix());
		if (!country.getContinents().isEmpty()) {
			countryDTO.setContinentIsoCode(country.getContinents().stream().map(Continent::getIsoCode2).collect(Collectors.toList()));
		}
		if (null != locale) {
			String name = htmlEscape(getLocalizedMessage(country.getIsoCode2(), locale));
			String officialName = htmlEscape(getLocalizedMessage(country.getIsoCode2()+".official", locale));
			countryDTO.setName(name);
			countryDTO.setOfficialName(officialName);
		}
		return countryDTO;
	}

}
