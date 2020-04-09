package com.prulloac.territoriesdata.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prulloac.territoriesdata.model.PoliticalDivision;

/**
 * @author Prulloac
 */
public interface PoliticalDivisionDAO extends JpaRepository<PoliticalDivision, Long> {
}
