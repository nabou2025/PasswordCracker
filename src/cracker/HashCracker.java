package cracker;

/**
 * Interface commune à toutes les stratégies de cassage de hash.
 *
 * Contrat imposé par l'énoncé : une seule méthode {@code crack} qui reçoit
 * l'empreinte (hash) recherchée et retourne le mot de passe correspondant,
 * ou {@code null} si aucun résultat n'est trouvé.
 *
 * C'est le "produit" abstrait manipulé par la fabrique (Simple Factory).
 */
public interface HashCracker {

    /**
     * Tente de retrouver le mot de passe à partir de son empreinte MD5.
     *
     * @param hash empreinte MD5 recherchée (32 caractères hexadécimaux)
     * @return le mot de passe trouvé, ou {@code null} si aucune correspondance
     */
    String crack(String hash);
}
