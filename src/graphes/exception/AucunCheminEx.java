package graphes.exception;

@SuppressWarnings("serial")
public class AucunCheminEx extends IllegalArgumentException {
    public AucunCheminEx(int debut, int fin) {
        super("pas de chemin entre " + debut + " et " + fin);
    }
}
