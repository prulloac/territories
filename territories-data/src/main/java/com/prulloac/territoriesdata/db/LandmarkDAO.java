package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prulloac.territoriesdata.model.Landmark;

public interface LandmarkDAO extends JpaRepository<Landmark, Long> {
}
