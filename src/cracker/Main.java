package cracker;

/**
 * Application en ligne de commande : passwordCracker.
 *
 * Usage :
 *   passwordCracker -m BRUTE -h e7247759c1633c0f9f1485f3690294a9
 *   passwordCracker -m DICO  -h 098f6bcd4621d373cade4e832627b4f6
 *
 * Le programme :
 *   1. lit la méthode (-m) et le hash (-h) ;
 *   2. demande la bonne stratégie à la fabrique ;
 *   3. lance le cassage en mesurant le temps et le nombre de tentatives ;
 *   4. affiche "Password found: <mdp>" ou "Password not found".
 */
public class Main {

    public static void main(String[] args) {
        // Force l'affichage en UTF-8 (évite les accents mal affichés sous Windows).
        System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));

        String method = null;
        String hash = null;

        // --- 1. Analyse des arguments -m et -h ---
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-m":
                    method = args[i + 1];
                    break;
                case "-h":
                    hash = args[i + 1];
                    break;
                default:
                    // argument ignoré
            }
        }

        if (method == null || hash == null) {
            System.out.println("Usage : passwordCracker -m <BRUTE|DICO> -h <hashMD5>");
            return;
        }

        // --- 2. Création de la stratégie via la FABRIQUE (jamais 'new' ici) ---
        HashCracker cracker;
        try {
            cracker = HashCrackerFactory.create(method);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
            return;
        }

        System.out.println("Méthode  : " + method.toUpperCase());
        System.out.println("Hash     : " + hash);
        System.out.println("Recherche en cours...");

        // --- 3. Cassage + mesures ---
        long start = System.nanoTime();
        String result = cracker.crack(hash);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;

        long attempts = -1;
        if (cracker instanceof AbstractHashCracker) {
            attempts = ((AbstractHashCracker) cracker).getAttempts();
        }

        // --- 4. Affichage du résultat ---
        System.out.println("------------------------------------------");
        if (result != null) {
            System.out.println("Password found: " + result);
        } else {
            System.out.println("Password not found");
        }
        if (attempts >= 0) {
            System.out.println("Tentatives : " + attempts);
        }
        System.out.println("Temps      : " + elapsedMs + " ms");
    }
}
