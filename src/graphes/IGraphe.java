package graphes;

import java.util.List;

public interface IGraphe {

	public final static int INFINI = Integer.MAX_VALUE;

	/**
	 * Donne le nombre de sommet d'un graphe
	 * @return le nombre (int) de sommet d'un graphe
	 */
	int getNbSommets();

	/**
	 * Ajouter un arc au graphe
	 * @param source : int, le sommet source
	 * @param valuation : int, la valuation de l'arc
	 * @param destination : int, le sommet destination
	 */
	void ajouterArc(int source, int valuation, int destination);

	/**
	 * Donne la valuation entre deux sommets
	 * @param i : int, le sommet i
	 * @param j : int, le sommet j
	 * @return la valeur (int) de la valuation entre i et j
	 */
	int getValuation(int i, int j);

	/**
	 * Vérifie s'il y a un arc entre deux sommets
	 * @param i : int, le sommet i
	 * @param j : int, le sommet j
	 * @return true s'il y a un arc entre i et j, false sinon
	 */
	boolean aArc(int i, int j);

	/**
	 * Donne la distance d'un chemin dans le graphe
	 * @param chemin : List<Integer>, le chemin
	 * @return la distance (int) du chemin
	 */
	int distance(List<Integer> chemin);
}
