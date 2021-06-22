package com.tdt4240.paint2win.rest.controller;

import com.tdt4240.paint2win.rest.model.HighscoreEntry;
import com.tdt4240.paint2win.rest.service.HighscoreEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/highscores")
public class HighscoreEntryController {
    @Autowired
    HighscoreEntryService highscoreEntryService;

    /**
     * GET top 10 results for a map
     * @param mapName Map to get highscores from, "desert" or "urban".
     * @return A sorted highscore list / top 10.
     */
    @GetMapping("/{mapName}")
    public List<HighscoreEntry> list(@PathVariable String mapName){
        return highscoreEntryService.highscoreList()
                .stream()
                .filter(entry -> entry.getMapName().equals(mapName.toUpperCase()))
                .sorted((a, b) -> a.getTime() - b.getTime())
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * POST new highscoreEntry to database
     * @param highscoreEntry Entry of current attempt
     * @return JSON-String "placement": $int:placementOfAttempt, "newPr": $bool:attemptIsNewPersonalBest"
     */
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody HighscoreEntry highscoreEntry){
        List<HighscoreEntry> highscoreEntryList = highscoreEntryService.highscoreList();

        // To compensate for delay in highscoreEntryService.updateHighScoreEntry,
        // we create a local map, add the new entry, and then sort to find placement.
        Map<String, HighscoreEntry> highscoreEntryMap = highscoreEntryList.stream()
                .filter(entry -> entry.getMapName().equals(highscoreEntry.getMapName()))
                .collect(Collectors.toMap(HighscoreEntry::getPlayerName, entry -> entry));

        HighscoreEntry previousBest = highscoreEntryMap.get(highscoreEntry.getPlayerName());
        // Adding underscores will ensure that the key will be unique, as it is longer than
        // the column length in the database.
        // If it's stupid, but it works, then it ain't stupid.
        highscoreEntryMap.put(highscoreEntry.getPlayerName() + "______________", highscoreEntry);
        highscoreEntryList = new ArrayList<>(highscoreEntryMap.values());
        highscoreEntryList.sort((a, b)->a.getTime() - b.getTime());

        int placement = highscoreEntryList.indexOf(highscoreEntry) + 1;

        boolean newPr = false;

        // IF new personal best OR first entry -> update score
        if (previousBest == null || highscoreEntry.getTime() < previousBest.getTime()){
            highscoreEntryService.updateHighscoreEntry(highscoreEntry);
            newPr = true;
        }

        // @TODO: add newPr boolean
        return new ResponseEntity<>("{\"placement\":" + placement + ", \"newPr\":" + newPr + "}", HttpStatus.OK);
    }

    /**
     * POST list of highscoreentries, used for populating database in development
     * @param highscoreEntries List of entries to upload to insert to Database
     * @return String with info about how many entries that were inserted.
     */
    @PostMapping("/fill")
    public ResponseEntity<?> add(@RequestBody List<HighscoreEntry> highscoreEntries){

        highscoreEntries.forEach(entry -> {
            highscoreEntryService.updateHighscoreEntry(entry);
        });
        int entriesLength = highscoreEntries.size();

        return new ResponseEntity<>("Added " + entriesLength + " entries.", HttpStatus.OK);
    }

    /**
     * DELETE all entries from database. FOR DEVELOPMENT ONLY.
     * @return
     */
    @DeleteMapping("/wipeDatabase")
    public ResponseEntity<?> delete() {
        highscoreEntryService.highscoreList().forEach(entry ->
        {
            highscoreEntryService.deleteHighscoreEntry(entry.getPlayerName());
        });
        return new ResponseEntity<>("Wiped database.", HttpStatus.OK);
    }

}
