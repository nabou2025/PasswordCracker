package cracker;

/**
 * Stratégie de cassage par FORCE BRUTE.
 *
 * Le programme génère automatiquement toutes les combinaisons possibles de
 * l'alphabet {@code a-z} jusqu'à une longueur maximale de 4 caractères, dans
 * l'ordre : a, b, ... z, aa, ab, ... zzzz. Chaque combinaison est testée
 * jusqu'à trouver une correspondance.
 */
public class BruteForceHashCracker extends AbstractHashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    @Override
    public String crack(String hash) {
        attempts = 0;
        String target = hash.trim().toLowerCase();

        // On explore les longueurs croissantes : 1, 2, 3 puis 4 caractères.
        for (int length = 1; length <= MAX_LENGTH; length++) {
            String result = generate(new char[length], 0, length, target);
            if (result != null) {
                return result;
            }
        }
        return null;   // aucune combinaison ne correspond
    }

    /**
     * Génère récursivement toutes les combinaisons d'une longueur donnée et
     * les teste au fur et à mesure.
     *
     * @param buffer tampon en cours de construction
     * @param pos    position courante dans le tampon
     * @param length longueur cible
     * @param target empreinte MD5 recherchée
     * @return le mot correspondant, ou {@code null}
     */
    private String generate(char[] buffer, int pos, int length, String target) {
        if (pos == length) {
            String candidate = new String(buffer);
            attempts++;
            return md5(candidate).equals(target) ? candidate : null;
        }
        for (int i = 0; i < ALPHABET.length(); i++) {
            buffer[pos] = ALPHABET.charAt(i);
            String result = generate(buffer, pos + 1, length, target);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
