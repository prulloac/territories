package com.prulloac.territoriesdata.model;

import com.prulloac.territoriesdata.utils.FilterableColumn;
import com.prulloac.territoriesdata.utils.SorteableColumn;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Prulloac
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table
public class Landmark extends AbstractGeoCoordinate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@ManyToOne
	@JoinColumn
	private Locality locality;

	@Column
	@SorteableColumn
	@FilterableColumn
	private String name;

	@Column
	@FilterableColumn
	private String streetAddress;

}
