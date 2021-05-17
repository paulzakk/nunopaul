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

    public String getSearch(String separator, String company, String query, Integer page, Integer size) throws IOException {

        String url = "";

        if (separator.equals("place") && company.equals("ka")) {
            url = "https://dapi.kakao.com/v2/local/search/keyword.json?&query=" + URLEncoder.encode(query, "UTF-8")+"&page="+page+"&size="+size;
        }else if (separator.equals("place") && company.equals("na")) {
            url = "https://openapi.naver.com/v1/search/local.json?&query="+ URLEncoder.encode(query, "UTF-8");
        }else if (separator.equals("image") && company.equals("ka")) {
            url = "https://dapi.kakao.com/v2/search/image?&query=" + URLEncoder.encode(query, "UTF-8")+"&page="+page+"&size="+size;
        }else if (separator.equals("image") && company.equals("na")) {
            url = "https://openapi.naver.com/v1/search/image&query="+ URLEncoder.encode(query, "UTF-8");
        }

        URL urlcon = new URL(url);
        HttpURLConnection urlconnection = (HttpURLConnection) urlcon.openConnection();
        urlconnection.setRequestMethod("GET");

        if (company.equals("ka")) {
            urlconnection.setRequestProperty("Content-Type", "application/json");
            urlconnection.setRequestProperty("Authorization", "KakaoAK 7282e4de22cb34c2154bebe73d449e1b");
            urlconnection.setRequestProperty("Accept", "application/json");
        }else if (company.equals("na")) {
            urlconnection.setRequestProperty("Content-Type", "application/json");
            urlconnection.setRequestProperty("X-Naver-Client-Id", "pDCIQf8GJIrJFy5hXMPI");
            urlconnection.setRequestProperty("X-Naver-Client-Secret", "vc7sEw9LUt");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), StandardCharsets.UTF_8));

        String result = br.readLine();

        urlconnection.disconnect();
        br.close();

        return result;
    }
}
