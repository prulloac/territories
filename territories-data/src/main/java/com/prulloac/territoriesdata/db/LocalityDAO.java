package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prulloac.territoriesdata.model.Locality;

import java.util.UUID;

/**
 * @author Prulloac
 */
public interface LocalityDAO extends JpaRepository<Locality, UUID>, JpaSpecificationExecutor<Locality> {
}
