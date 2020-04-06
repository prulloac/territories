package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prulloac.territoriesdata.model.Locality;

import java.util.UUID;

public interface LocalityDAO extends JpaRepository<Locality, UUID> {
}
