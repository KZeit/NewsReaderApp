package com.example.newsreaderapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GuardianResponse {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        @SerializedName("results")
        private List<Article> articles;

        public List<Article> getArticles() {
            return articles;
        }
    }

    public static class Article {
        @SerializedName("webTitle")
        private String title;
        @SerializedName("webUrl")
        private String url;
        @SerializedName("sectionName")
        private String section;

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getSection() {
            return section;
        }
    }
}
