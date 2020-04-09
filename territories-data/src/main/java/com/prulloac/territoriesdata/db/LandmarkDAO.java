package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.prulloac.territoriesdata.model.Landmark;

/**
 * @author Prulloac
 */
public interface LandmarkDAO extends JpaRepository<Landmark, Long>, JpaSpecificationExecutor<Landmark> {
}
