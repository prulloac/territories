package com.prulloac.territoriesdata.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author Prulloac
 */
@Data
@Entity
@Table
public class Continent implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	@Column
	private String isoCode2;

	@ManyToMany(mappedBy = "continents")
	private List<Country> countries = new ArrayList<>();

	@Override
	@Transient
	public boolean isNew() {
		return null == id;
	}
}
