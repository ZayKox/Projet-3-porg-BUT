package graphes;

import java.util.List;


public interface IPCC {
    /**
     * Vérifie si un graphe est ok
     * @param g : IGraphe
     * @return true si le graphe est ok, false sinon
     */
    boolean estOk(IGraphe g);

    /**
     * Calcule la distance la plus courte entre deux sommets d'un graphe
     * @param g : IGraphe,
     * @param debut : int, Sommet de debut
     * @param fin : int, Sommet de fin
     * @param chemin : List<Integer>, chemin le plus court entre debut et fin
     * @return la distance (int) la plus courte entre debut et fin
     */
    int plusCourtChemin(IGraphe g, int debut, int fin, List<Integer> chemin);
}
