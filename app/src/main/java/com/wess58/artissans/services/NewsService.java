package com.wess58.artissans.services;

import com.wess58.artissans.Constants;
import com.wess58.artissans.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsService {

    public static void findArtWorks( Callback callBack) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.NEWS_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("apikey",Constants.NEWS_API_KEY);
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callBack);
    }

    public ArrayList<News> processResults (Response response){
        ArrayList<News> artNews = new ArrayList<>();


        try{
            String jsonData = response.body().string();
            if (response.isSuccessful()){
                JSONObject harvardJSON = new JSONObject(jsonData);
                JSONArray recordsJSON = harvardJSON.getJSONArray("records");

                for (int i=0; i< recordsJSON.length(); i++){

                    JSONObject newsJSON = recordsJSON.getJSONObject(i);
                    String accessionyear = newsJSON.getString("accessionyear");
                    String technique = newsJSON.getString("technique");
                    String copyright = newsJSON.getString("copyright");
                    String url = newsJSON.getString("url");
                    String classification = newsJSON.getString("classification");
                    String image = newsJSON.optString("primaryimageurl", "NO IMAGES");
                    News news = new News(accessionyear, technique, copyright, url, image, classification);
                    artNews.add(news);

                }

            }
        } catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
            return artNews;
    }
}


