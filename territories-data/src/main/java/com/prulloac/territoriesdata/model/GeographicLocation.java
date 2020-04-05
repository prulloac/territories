package com.prulloac.territoriesdata.model;

import org.springframework.data.domain.Persistable;

import com.prulloac.territoriesdata.utils.SorteableColumn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Prulloac
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class GeographicLocation extends GeoCoordinate implements Persistable<String> {

	@Id
	@Column(columnDefinition = "uuid default uuid_generate_v1()", name = "id")
	@SorteableColumn
	protected String id;

	@Column(unique = true, nullable = false, length = 2)
	@SorteableColumn
	private String isoCode2;

	@Column(unique = true, length = 3)
	@SorteableColumn
	private String isoCode3;

	@Column(unique = true, length = 5)
	@SorteableColumn
	private String isoNumeric;

	@Override
	@Transient
	public boolean isNew() {
		return null == this.id;
	}

}
