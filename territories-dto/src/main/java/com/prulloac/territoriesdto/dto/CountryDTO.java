package com.prulloac.territoriesdto.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class CountryDTO implements Serializable {

	private String id;
	private String isoCode2;
	private String isoCode3;
	private Integer isoNumeric;
	private String internetCctld;
	private Short phonePrefix;
	private List<String> continentIsoCode;
	private String name;
	private String officialName;

}
