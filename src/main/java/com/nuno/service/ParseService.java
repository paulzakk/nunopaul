package com.nuno.service;

import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParseService {

    @Autowired
    SearchService separateSearch;

    public String getParse(String query, Integer page, Integer size) throws Exception {

        String placeResult = separateSearch.getSearch("place", "ka", query, page, size);

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(placeResult);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray documents = jsonObject.get("documents").getAsJsonArray();

        JsonObject meta = jsonObject.get("meta").getAsJsonObject();
        JsonObject metaObject = meta.getAsJsonObject();
        int pageablecount = metaObject.get("pageable_count").getAsInt();
        int totalPages = (pageablecount % size == 0) ? pageablecount / size : (pageablecount / size) + 1;
        int totalElements = metaObject.get("total_count").getAsInt();
        int currentPage = page;
        int pageSize = size;

        JsonObject metajsontempObject = new JsonObject();
        metajsontempObject.addProperty("totalPages", totalPages);
        metajsontempObject.addProperty("totalElements", totalElements);
        metajsontempObject.addProperty("currentPage", currentPage);
        metajsontempObject.addProperty("pageSize", pageSize);
        JsonObject metajsonObject = new JsonObject();
        metajsonObject.add("meta", metajsontempObject);

        List<String> placeNameList = new ArrayList<>();

        for (JsonElement document : documents) {
            JsonObject JsonObject = document.getAsJsonObject();
            placeNameList.add(JsonObject.get("place_name").getAsString());
        }

        JsonArray jsonResult = new JsonArray();

        for (String s : placeNameList) {
            String imageResult = separateSearch.getSearch("image", "ka", s, page, size);
            jsonElement = jsonParser.parse(imageResult);
            jsonObject = jsonElement.getAsJsonObject();
            JsonArray imageurls = jsonObject.get("documents").getAsJsonArray();

            List<String> imageUrlList = new ArrayList<>();

            for (JsonElement imageurl : imageurls) {
                JsonObject JsonObject = imageurl.getAsJsonObject();
                imageUrlList.add(JsonObject.get("image_url").getAsString());
            }

            if (imageUrlList.size() >= 3) {
                JsonObject tempResult = new JsonObject();
                tempResult.addProperty("title", s);
                Add(tempResult, "imagesUrls", new String[]{imageUrlList.get(0), imageUrlList.get(1), imageUrlList.get(2)});
                jsonResult.add(tempResult);
            } else if (imageUrlList.size() == 2) {
                JsonObject tempResult = new JsonObject();
                tempResult.addProperty("title", s);
                Add(tempResult, "imagesUrls", new String[]{imageUrlList.get(0), imageUrlList.get(1)});
                jsonResult.add(tempResult);
            } else if (imageUrlList.size() == 1) {
                JsonObject tempResult = new JsonObject();
                tempResult.addProperty("title", s);
                Add(tempResult, "imagesUrls", new String[]{imageUrlList.get(0)});
                jsonResult.add(tempResult);
            } else {
                JsonObject tempResult = new JsonObject();
                tempResult.addProperty("title", s);
                jsonResult.add(tempResult);
            }
            metajsonObject.add("places", jsonResult);
        }

        JsonObject resultObject = new JsonObject();
        resultObject.add("result", metajsonObject);

        return resultObject.toString();
    }

    public static void Add(JsonObject jo, String property, String[] values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            array.add(new JsonPrimitive(value));
        }
        jo.add(property, array);
    }

}
