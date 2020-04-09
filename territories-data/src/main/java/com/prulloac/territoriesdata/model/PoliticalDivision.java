package com.prulloac.territoriesdata.model;

import com.prulloac.territoriesdata.utils.FilterableColumn;
import com.prulloac.territoriesdata.utils.SorteableColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import lombok.Data;

/**
 * @author Prulloac
 */
@Data
@Entity
@Table
public class PoliticalDivision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	@FilterableColumn
	private String name;

	@Column
	@SorteableColumn
	@FilterableColumn
	private Short divisionLevel;

	@ManyToMany(mappedBy = "politicalDivisionList")
	private List<Country> countryList;
}
