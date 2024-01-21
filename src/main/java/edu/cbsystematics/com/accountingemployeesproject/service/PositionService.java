package edu.cbsystematics.com.accountingemployeesproject.service;

import edu.cbsystematics.com.accountingemployeesproject.model.Position;

import java.util.List;

public interface PositionService {

	// Save a position
	void savePosition(Position position);

	// Delete a position by ID
	void deletePosition(Long id);

	// Get all positions
	List<Position> getAllPositions();

}