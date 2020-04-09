package com.prulloac.territoriesdata.model;

import org.springframework.data.domain.Persistable;

import com.prulloac.territoriesdata.utils.FilterableColumn;
import com.prulloac.territoriesdata.utils.SorteableColumn;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Prulloac
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractGeographicLocation extends AbstractGeoCoordinate implements Persistable<UUID> {

	@Id
	@Column(columnDefinition = "uuid default uuid_generate_v1()", name = "id")
	@SorteableColumn
	protected UUID id;

	@Column(unique = true, nullable = false, length = 2)
	@SorteableColumn
	@FilterableColumn
	private String isoCode2;

	@Column(unique = true, length = 3)
	@SorteableColumn
	@FilterableColumn
	private String isoCode3;

	@Column(unique = true)
	@SorteableColumn
	@FilterableColumn
	private Integer isoNumeric;

	@Override
	@Transient
	public boolean isNew() {
		return null == this.id;
	}

}
