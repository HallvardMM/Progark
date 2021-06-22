package com.tdt4240.paint2win.rest.repository;

import com.tdt4240.paint2win.rest.model.HighscoreEntry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HighscoreEntryRepository extends JpaRepository<HighscoreEntry, String> {
}
