package com.prulloac.territoriesdata.model;

import com.prulloac.territoriesdata.utils.FilterableColumn;
import com.prulloac.territoriesdata.utils.SorteableColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table
public class Locality extends AbstractGeographicLocation {

	@OneToMany(mappedBy = "parentLocality", fetch = FetchType.LAZY)
	private List<Locality> localityList;

	@JoinColumn(nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Locality parentLocality;

	@JoinColumn(nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Country country;

	@Column
	@SorteableColumn
	@FilterableColumn
	protected String name;

	@Column
	@SorteableColumn
	private String localCode;

	@Column
	@SorteableColumn
	@FilterableColumn
	private Short divisionLevel;

}
