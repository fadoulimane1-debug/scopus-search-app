package com.scopus.view;

import com.scopus.model.Article;
import com.scopus.model.SearchResult;

import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void displayWelcome() {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   SCOPUS Article Search - Recherche par Auteur    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
    }

    public String getAuthorName() {
        System.out.print("Entrez le nom de l'auteur à rechercher: ");
        return scanner.nextLine().trim();
    }

    public void displaySearching(String authorName) {
        System.out.println("\n🔍 Recherche en cours pour: " + authorName);
        System.out.println("Veuillez patienter...\n");
    }

    public void displayResults(SearchResult result) {
        System.out.println("═══════════════════════════════════════════════════");
        System.out.println("Résultats de recherche pour: " + result.getQuery());
        System.out.println("Total: " + result.getTotalResults() + " article(s) trouvé(s)");
        System.out.println("Affichage de " + result.getResultCount() + " article(s)");
        System.out.println("═══════════════════════════════════════════════════\n");

        if (result.getArticles().isEmpty()) {
            System.out.println("Aucun article trouvé pour cet auteur.");
            return;
        }

        int index = 1;
        for (Article article : result.getArticles()) {
            displayArticle(article, index++);
        }
    }

    public void displayArticle(Article article, int index) {
        System.out.println("─────────────────────────────────────────────────");
        System.out.println("[" + index + "] " + article.getTitle());
        System.out.println("    Auteur(s): " + article.getAuthorsString());
        System.out.println("    Journal: " + article.getPublicationName());
        System.out.println("    Date: " + article.getCoverDate());
        System.out.println("    Citations: " + article.getCitationCount());
        if (article.getDoi() != null && !article.getDoi().isEmpty()) {
            System.out.println("    DOI: " + article.getDoi());
        }
        System.out.println();
    }

    public void displayError(String message) {
        System.err.println("\n❌ ERREUR: " + message);
        System.out.println();
    }

    public boolean askContinue() {
        System.out.print("\nVoulez-vous faire une autre recherche? (o/n): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("o") || response.equals("oui") || response.equals("y") || response.equals("yes");
    }

    public void displayGoodbye() {
        System.out.println("\n👋 Merci d'avoir utilisé SCOPUS Article Search!");
        System.out.println("Au revoir!\n");
    }

    public void close() {
        scanner.close();
    }
}