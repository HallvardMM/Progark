package com.tdt4240.paint2win.networking;

import com.tdt4240.paint2win.model.maps.AbstractMap;
import com.tdt4240.paint2win.networking.Dto.IDto;
import com.tdt4240.paint2win.networking.Dto.HighScoreRow;
import com.tdt4240.paint2win.networking.Dto.Placement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataTransfer {

    private final static String domain = "http://46.101.118.152:";
    private final static String port = "8080";

    /**
     * Based on https://www.baeldung.com/httpurlconnection-post
     * Used for sending time used on a round
     * @param playerName name of the player
     * @param mapType
     * @throws IOException
     */
    public static final Placement SendTimeUsed(String playerName, int timeInMilliSec, AbstractMap mapType) throws IOException {
        URL url = new URL(domain+port+"/highscores/");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = new HighScoreRow(playerName,timeInMilliSec,mapType.toString().toUpperCase()).toJsonString();
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return IDto.fromJsonString(response.toString(), Placement.class);
        }
    }

    /**
     * Used for getting list of highscores
     * @param mapType which map to get data from
     * @throws IOException
     * @returns list of highScoreRows or throws
     */
    public static final List<HighScoreRow> getHighscores(AbstractMap.valid_maps mapType) throws IOException {
        URL url = new URL(domain+port+"/highscores/"+mapType.toString().toLowerCase());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return IDto.fromArryWithJsonString(response.toString(),HighScoreRow.class);
            }
        }
        throw new IOException("Response code was not OK");
    }
}
