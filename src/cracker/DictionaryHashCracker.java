package cracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Stratégie de cassage par DICTIONNAIRE.
 *
 * Le programme charge un fichier contenant une liste de mots ; pour chaque mot
 * il calcule l'empreinte MD5 et la compare au hash recherché. Dès qu'une
 * correspondance est trouvée, le mot est retourné.
 */
public class DictionaryHashCracker extends AbstractHashCracker {

    /** Chemin du fichier dictionnaire utilisé par défaut. */
    public static final String DEFAULT_DICTIONARY = "resources/dictionary.txt";

    private final String dictionaryPath;

    /** Utilise le dictionnaire par défaut. */
    public DictionaryHashCracker() {
        this(DEFAULT_DICTIONARY);
    }

    /** Permet de fournir un dictionnaire personnalisé. */
    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    @Override
    public String crack(String hash) {
        attempts = 0;
        String target = hash.trim().toLowerCase();

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(dictionaryPath), StandardCharsets.UTF_8)) {

            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim();
                if (word.isEmpty()) {
                    continue;
                }
                attempts++;
                if (md5(word).equals(target)) {
                    return word;   // mot de passe trouvé
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur : impossible de lire le dictionnaire '"
                    + dictionaryPath + "' (" + e.getMessage() + ")");
            return null;
        }
        return null;   // aucun mot ne correspond
    }
}
