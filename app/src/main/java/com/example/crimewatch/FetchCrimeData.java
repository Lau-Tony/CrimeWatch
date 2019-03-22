package com.example.crimewatch;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchCrimeData {

    public String request(String uri) throws IOException {
        StringBuilder sb = new StringBuilder();

        URL url = new URL(uri);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {


            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            urlConnection.disconnect();
        }

       return sb.toString();
    }


}
