package com.pad.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pad.ppmtool.domain.BackLog;

@Repository
public interface BackLogRepository extends CrudRepository<BackLog, Long> {
	
	BackLog findByProjectIdentifier(String identifier);
}
