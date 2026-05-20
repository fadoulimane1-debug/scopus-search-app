package com.scopus;

import com.scopus.controller.MainController;
import com.scopus.util.ApiConfig;

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
            System.err.println("2. Définissez la variable d'environnement " + ApiConfig.API_KEY_ENV + " avec cette clé");
            System.err.println("3. Recompilez et relancez l'application");
            System.err.println();
            return;
        }

        // Démarrer l'application
        MainController controller = new MainController();
        controller.start();
    }
}
