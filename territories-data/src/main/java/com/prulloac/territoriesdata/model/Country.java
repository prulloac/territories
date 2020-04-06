package com.prulloac.territoriesdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Prulloac
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "country")
public class Country extends GeographicLocation  {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable
	private List<PoliticalDivision> politicalDivisionList;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Locality> localityList;

	@Column(length = 3)
	private Short phonePrefix;

	@Column(length = 5, name = "internetCctld")
	private String internetCctld;

	@ManyToMany
	@JoinTable
	private List<Continent> continents = new ArrayList<>();

}
