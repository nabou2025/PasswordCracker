package cracker;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe abstraite mutualisant le code commun à toutes les stratégies :
 *  - le calcul de l'empreinte MD5 d'une chaîne ;
 *  - le compteur du nombre de tentatives effectuées.
 *
 * Objectif : respecter la contrainte de l'énoncé "les duplications de code
 * doivent être évitées" (principe DRY). Les stratégies concrètes héritent de
 * cette classe et se concentrent uniquement sur leur logique de recherche.
 *
 * Elle implémente {@link HashCracker}, donc la fabrique peut toujours retourner
 * le type abstrait {@code HashCracker} comme l'exige l'énoncé.
 */
public abstract class AbstractHashCracker implements HashCracker {

    /** Nombre de candidats testés lors du dernier appel à {@code crack}. */
    protected long attempts = 0;

    /**
     * Calcule l'empreinte MD5 d'une chaîne et la retourne en hexadécimal
     * (32 caractères, en minuscules).
     *
     * @param input chaîne à hacher
     * @return empreinte MD5 hexadécimale
     */
    protected String md5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // MD5 fait partie de la spécification Java : ce cas ne se produit pas.
            throw new IllegalStateException("Algorithme MD5 indisponible", e);
        }
    }

    /**
     * @return le nombre de candidats testés lors du dernier cassage
     */
    public long getAttempts() {
        return attempts;
    }
}
