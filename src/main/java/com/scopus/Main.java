package com.scopus;

import com.scopus.controller.MainController;

public class Main {

    public static void main(String[] args) {
        // Vérifier si l'API Key est configurée
        if (!ApiConfig.isConfigured()) {
            System.err.println("╔═══════════════════════════════════════════════════════════╗");
            System.err.println("║              ERREUR DE CONFIGURATION                      ║");
            System.err.println("╚═══════════════════════════════════════════════════════════╝");
            System.err.println();
            System.err.println("❌ Clé API SCOPUS non configurée!");
            System.err.println();
            System.err.println("Veuillez suivre ces étapes:");
            System.err.println("1. Obtenez une clé API sur: https://dev.elsevier.com/");
            System.err.println("2. Ouvrez le fichier: src/main/java/com/scopus/config/ApiConfig.java");
            System.err.println("3. Remplacez 'VOTRE_CLE_API_ICI' 793d48015988610ff0bb2494d7adb11c");
            System.err.println("4. Recompilez et relancez l'application");
            System.err.println();
            return;
        }

        // Démarrer l'application
        MainController controller = new MainController();
        controller.start();
    }
}
