package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prulloac.territoriesdata.model.Continent;

public interface ContinentDAO extends JpaRepository<Continent, Long> {
}