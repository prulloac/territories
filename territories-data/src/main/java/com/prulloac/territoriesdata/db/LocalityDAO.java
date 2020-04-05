package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prulloac.territoriesdata.model.Locality;

public interface LocalityDAO extends JpaRepository<Locality, String> {
}
