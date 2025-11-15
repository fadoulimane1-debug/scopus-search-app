package com.scopus.service;

import com.scopus.model.Article;
import com.scopus.model.Author;
import com.scopus.model.SearchResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ScopusApiService {

    private CloseableHttpClient httpClient;

    public ScopusApiService() {
        this.httpClient = HttpClients.createDefault();
    }

    /**
     * Recherche des articles par nom d'auteur
     */
    public SearchResult searchByAuthorName(String authorName) throws IOException {
        if (!ApiConfig.isConfigured()) {
            throw new IllegalStateException("API Key not configured! Please set your API key in ApiConfig.java");
        }

        SearchResult result = new SearchResult();
        result.setQuery(authorName);

        // Encoder le nom de l'auteur pour l'URL
        String encodedAuthor = URLEncoder.encode(authorName, StandardCharsets.UTF_8.toString());

        // Construire l'URL de recherche
        String url = ApiConfig.BASE_URL +
                "?query=AUTHOR-NAME(" + encodedAuthor + ")" +
                "&apiKey=" + ApiConfig.API_KEY +
                "&count=" + ApiConfig.RESULTS_PER_PAGE;

        System.out.println("Searching for author: " + authorName);
        System.out.println("URL: " + url);

        // Créer et exécuter la requête
        HttpGet request = new HttpGet(url);
        request.addHeader("Accept", "application/json");

        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            parseSearchResponse(jsonResponse, result);
        } else {
            String errorMessage = EntityUtils.toString(response.getEntity());
            throw new IOException("API Error: " + statusCode + " - " + errorMessage);
        }

        return result;
    }

    /**
     * Parse la réponse JSON de SCOPUS
     */
    private void parseSearchResponse(String jsonResponse, SearchResult result) {
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // Obtenir le résultat de recherche
        JsonObject searchResults = root.getAsJsonObject("search-results");

        if (searchResults == null) {
            return;
        }

        // Obtenir le nombre total de résultats
        String totalResultsStr = searchResults.get("opensearch:totalResults").getAsString();
        result.setTotalResults(Integer.parseInt(totalResultsStr));

        // Obtenir les entrées (articles)
        JsonArray entries = searchResults.getAsJsonArray("entry");

        if (entries == null || entries.size() == 0) {
            return;
        }

        // Parser chaque article
        for (JsonElement entryElement : entries) {
            JsonObject entry = entryElement.getAsJsonObject();
            Article article = parseArticle(entry);
            if (article != null) {
                result.addArticle(article);
            }
        }
    }

    /**
     * Parse un article individuel
     */
    private Article parseArticle(JsonObject entry) {
        try {
            Article article = new Article();

            // ID Scopus
            if (entry.has("dc:identifier")) {
                article.setScopusId(entry.get("dc:identifier").getAsString());
            }

            // Titre
            if (entry.has("dc:title")) {
                article.setTitle(entry.get("dc:title").getAsString());
            }

            // Nom de publication
            if (entry.has("prism:publicationName")) {
                article.setPublicationName(entry.get("prism:publicationName").getAsString());
            }

            // Date de couverture
            if (entry.has("prism:coverDate")) {
                article.setCoverDate(entry.get("prism:coverDate").getAsString());
            }

            // DOI
            if (entry.has("prism:doi")) {
                article.setDoi(entry.get("prism:doi").getAsString());
            }

            // Nombre de citations
            if (entry.has("citedby-count")) {
                article.setCitationCount(entry.get("citedby-count").getAsInt());
            }

            // Auteur(s)
            if (entry.has("dc:creator")) {
                String authorName = entry.get("dc:creator").getAsString();
                Author author = new Author();
                author.setLastName(authorName);
                article.addAuthor(author);
            }

            return article;

        } catch (Exception e) {
            System.err.println("Error parsing article: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fermer le client HTTP
     */
    public void close() {
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
