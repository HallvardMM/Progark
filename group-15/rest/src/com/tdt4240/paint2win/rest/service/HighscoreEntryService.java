package com.tdt4240.paint2win.rest.service;


import com.tdt4240.paint2win.rest.model.HighscoreEntry;
import com.tdt4240.paint2win.rest.repository.HighscoreEntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HighscoreEntryService {
    @Autowired
    private HighscoreEntryRepository highscoreEntryRepository;

    public List<HighscoreEntry> highscoreList(){
        List<HighscoreEntry> highscoreEntries =  highscoreEntryRepository.findAll();
        highscoreEntries.sort((a, b)-> a.getTime() - b.getTime());
        return highscoreEntries;
    }

    public HighscoreEntry getHighscoreEntry(String playerName){
        return highscoreEntryRepository.findById(playerName).orElse(null);
    }

    public void updateHighscoreEntry(HighscoreEntry highscoreEntry){
        highscoreEntryRepository.save(highscoreEntry);
    }

    public void deleteHighscoreEntry(String playerName){
        highscoreEntryRepository.deleteById(playerName);
    }

}
