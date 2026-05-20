package com.scopus.util;

public final class ApiConfig {
    public static final String BASE_URL = "https://api.elsevier.com/content/search/scopus";
    public static final int RESULTS_PER_PAGE = 10;
    public static final String API_KEY_ENV = "SCOPUS_API_KEY";

    private static final String API_KEY = System.getenv(API_KEY_ENV);

    private ApiConfig() {
    }

    public static boolean isConfigured() {
        return API_KEY != null && !API_KEY.trim().isEmpty();
    }

    public static String getApiKey() {
        return API_KEY.trim();
    }
}
