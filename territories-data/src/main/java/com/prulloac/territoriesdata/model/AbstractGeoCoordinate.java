package com.prulloac.territoriesdata.model;

import org.springframework.util.Assert;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Prulloac
 */
@MappedSuperclass
public abstract class AbstractGeoCoordinate {

	@Column
	@Range(min = -90L, max = 90L)
	protected Double latitude;

	@Column
	@Range(min = -180L, max = 180L)
	protected Double longitude;

	public void setLatitude(Double latitude) {
		Assert.isTrue(latitude >= -90 && latitude <= 90, "latitude must be between -90 and 90");
		this.latitude = latitude;
	}
	public void setLongitude(Double longitude) {
		Assert.isTrue(longitude >= -180 && longitude <= 180, "longitude must be between -180 and 180");
		this.longitude = longitude;
	}

}
