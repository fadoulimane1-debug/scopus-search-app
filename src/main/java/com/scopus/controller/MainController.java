package com.scopus.controller;

import com.scopus.model.SearchResult;
import com.scopus.service.ScopusApiService;
import com.scopus.view.ConsoleView;

public class MainController {

    private ScopusApiService apiService;
    private ConsoleView view;

    public MainController() {
        this.apiService = new ScopusApiService();
        this.view = new ConsoleView();
    }

    public void start() {
        view.displayWelcome();

        boolean continueSearching = true;

        while (continueSearching) {
            try {
                // Obtenir le nom de l'auteur
                String authorName = view.getAuthorName();

                if (authorName.isEmpty()) {
                    view.displayError("Le nom de l'auteur ne peut pas être vide!");
                    continue;
                }

                // Afficher message de recherche
                view.displaySearching(authorName);

                // Effectuer la recherche
                SearchResult result = apiService.searchByAuthorName(authorName);

                // Afficher les résultats
                view.displayResults(result);

            } catch (Exception e) {
                view.displayError(e.getMessage());
                e.printStackTrace();
            }

            // Demander si l'utilisateur veut continuer
            continueSearching = view.askContinue();
        }

        // Nettoyage et fermeture
        cleanup();
    }

    private void cleanup() {
        view.displayGoodbye();
        apiService.close();
        view.close();
    }
}
