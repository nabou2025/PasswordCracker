package cracker;

/**
 * Fabrique simple (patron Simple Factory).
 *
 * Centralise la création des objets {@link HashCracker}. Le programme principal
 * ne connaît que la méthode demandée ("BRUTE" ou "DICO") et reçoit en retour le
 * bon objet, sans jamais instancier lui-même les classes concrètes.
 *
 * C'est le coeur du patron : la logique de "quelle classe instancier" est
 * isolée à un seul endroit.
 */
public class HashCrackerFactory {

    /** Constructeur privé : la fabrique n'a pas vocation à être instanciée. */
    private HashCrackerFactory() {
    }

    /**
     * Crée la stratégie correspondant à la méthode demandée.
     *
     * @param method "BRUTE" (force brute) ou "DICO" (dictionnaire),
     *               insensible à la casse
     * @return l'instance de {@link HashCracker} adaptée
     * @throws IllegalArgumentException si la méthode est inconnue ou nulle
     */
    public static HashCracker create(String method) {
        if (method == null) {
            throw new IllegalArgumentException("La méthode ne peut pas être nulle");
        }
        switch (method.trim().toUpperCase()) {
            case "BRUTE":
                return new BruteForceHashCracker();
            case "DICO":
                return new DictionaryHashCracker();
            default:
                throw new IllegalArgumentException(
                        "Méthode inconnue : '" + method + "' (attendu : BRUTE ou DICO)");
        }
    }
}
