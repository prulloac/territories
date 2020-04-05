package com.prulloac.territoriesdto.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class CountryDTO implements Serializable {

	private String id;
	private String isoCode2;
	private String isoCode3;
	private String isoNumeric;
	private String internetCctld;
	private Short phonePrefix;
	private String continentIsoCode;
	private String name;
	private String officialName;

}
