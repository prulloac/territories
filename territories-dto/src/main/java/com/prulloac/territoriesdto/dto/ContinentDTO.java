package com.prulloac.territoriesdto.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ContinentDTO implements Serializable {

	private long id;
	private String isoCode2;
	private String name;
	private int countries;
	private List<String> countriesIsoCodes;
}
