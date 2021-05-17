package com.nuno.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class SearchService {

    public String getSearch(String separator, String query, Integer page, Integer size) throws IOException {

        String url = "";

        if (separator.equals("place")) {
            url = "https://dapi.kakao.com/v2/local/search/keyword.json?&query=" + URLEncoder.encode(query, "UTF-8")+"&page="+page+"&size="+size;
        }else if (separator.equals("image")) {
            url = "https://dapi.kakao.com/v2/search/image?&query=" + URLEncoder.encode(query, "UTF-8")+"&page="+page+"&size="+size;
        }

        URL urlcon = new URL(url);
        HttpURLConnection urlconnection = (HttpURLConnection) urlcon.openConnection();
        urlconnection.setRequestMethod("GET");

        urlconnection.setRequestProperty("Content-Type", "application/json");
        urlconnection.setRequestProperty("Authorization", "KakaoAK 7282e4de22cb34c2154bebe73d449e1b");
        urlconnection.setRequestProperty("Accept", "application/json");

        BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));

        String result = br.readLine();

        urlconnection.disconnect();
        br.close();

        return result;
    }
}
