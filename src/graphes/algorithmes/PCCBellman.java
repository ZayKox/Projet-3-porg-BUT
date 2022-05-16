package graphes.algorithmes;

import graphes.IGraphe;
import graphes.IPCC;
import graphes.exception.AucunCheminEx;
import graphes.exception.CircuitAbsorbantEx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PCCBellman implements IPCC {
	
	public boolean existeChemin(IGraphe g, int u, int v) {
		ArrayList<Integer> file = new ArrayList<>();
		boolean visites[] = new boolean[g.getNbSommets()];
		for (int i = 0; i < g.getNbSommets(); i++) visites[i] = false;
		file.add(u);
		int courant;
		
		while (file.size() != 0) {
			courant = file.remove(0);
			visites[courant-1] = true;
			
			for (int i = 1; i <= g.getNbSommets(); i++) {
				if (g.aArc(courant, i) && visites[i-1] == false) {
					file.add(i);
					visites[i-1] = true;
				}
				else if (g.aArc(courant, v))
					return true;
			}
		}	
		return false;
	}

	public boolean estOk(IGraphe g) {
		for (int i = 1; i <= g.getNbSommets(); i++) {
			if (existeChemin(g, i, i)) return false; // renvoie false si il y a un circuit
		}
		return true;
	}
	
	private void getPredecesseurs(IGraphe g, ArrayList<Integer> predecesseurs[]) {
		for (int i = 1; i <= g.getNbSommets(); i++) {
			for (int j = 1; j <= g.getNbSommets(); j++) {
				if (g.aArc(i, j))
					predecesseurs[j-1].add(i);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private int[] topologie(IGraphe g) {
		int[] resultat = new int[g.getNbSommets()]; // Le niveau de chaque sommets
		ArrayList<Integer> predecesseurs[]; // Les predecesseurs de chaque sommets
		
		predecesseurs = new ArrayList[g.getNbSommets()];
		for (int i = 0; i < g.getNbSommets(); i++) {
			predecesseurs[i] = new ArrayList<>();
			resultat[i] = -1;
		}
		
		getPredecesseurs(g, predecesseurs);
		
		int tmp = 0; // Permet de savoir quand tous les sommets sont class�s par niveau
		int niveau = 0; // Attribut le niveau aux sommets
		while(true) {
			for (int i = 0; i < g.getNbSommets(); i++) {
				if (predecesseurs[i].size() == 0 && resultat[i] == -1) {
					resultat[i] = niveau;
				}
			}
			for (int i = 0; i < g.getNbSommets(); i++) {
				if (predecesseurs[i].size() == 0 && resultat[i] == niveau) {
					for (int j = 0; j < g.getNbSommets(); j++) {
						if (predecesseurs[j].contains(i+1)) {
							for (int k = 0; k < predecesseurs[j].size(); k++) {
								if (predecesseurs[j].get(k) == i+1) {
									predecesseurs[j].remove(k);
								}
							}
						}
					}
				}
			}
			niveau++;
			
			for (int i = 0; i < g.getNbSommets(); i++) {
				if (resultat[i] != -1) 
					tmp++;
			}
			
			if (tmp == g.getNbSommets()) break;
			else tmp = 0;
		}
		
		return resultat;
	}
	
	@SuppressWarnings("unchecked")
	public int plusCourtChemin(IGraphe g, int u, int v, List<Integer> chemin) {
		if (!existeChemin(g, u, v)) throw new AucunCheminEx(u, v);
		if (!estOk(g)) throw new CircuitAbsorbantEx(); // si y'a un circuit cela provoque une erreur

		chemin.clear();
		
		int niveauSommets[] = topologie(g); // le niveau de chaque sommets
		int sommetCourant;
		
		int tmp = 0;
		for (int i = 0; i < g.getNbSommets(); i++) {
			if (tmp < niveauSommets[i]) {
				tmp = niveauSommets[i];
			}
		}
		tmp++;
		ArrayList<Integer> sommetsParNiveau[] = new ArrayList[tmp]; // initialise les sommets par niveau
		for (int i = 0; i < tmp; i++) 
			sommetsParNiveau[i] = new ArrayList<>();
		for (int i = 0; i < g.getNbSommets(); i++)
			sommetsParNiveau[niveauSommets[i]].add(i+1);
		
		int niveauCourant = niveauSommets[u-1]; // le niveau courant
		
		int[] valuationChemin = new int[g.getNbSommets()]; // v�rifie que c'est bien le plus court chemin
		for (int i = 0; i < g.getNbSommets(); i++) valuationChemin[i] = Integer.MAX_VALUE;
		valuationChemin[u-1] = 0;
		
		ArrayList<Integer> predecesseurs[]; // les predecesseurs des sommets
		predecesseurs = new ArrayList[g.getNbSommets()];
		for (int i = 0; i < g.getNbSommets(); i++) 
			predecesseurs[i] = new ArrayList<>();
		getPredecesseurs(g, predecesseurs);
		
		for (int i = 0; i < g.getNbSommets(); i++) {
			if (g.aArc(u, i+1) && niveauCourant <= niveauSommets[i]) {
				valuationChemin[i] = g.getValuation(u, i+1);
			}
		}
		niveauCourant++;
		
		while(niveauCourant <= niveauSommets[v-1]) { // chemin le plus court pour tous les sommets
			for (int s = 0; s < sommetsParNiveau[niveauCourant].size(); s++) {
				sommetCourant = sommetsParNiveau[niveauCourant].get(s);
				if (sommetCourant == v || existeChemin(g, sommetCourant, v)) {
					for (int i = 0; i < predecesseurs[sommetCourant-1].size(); i++) {
						if (valuationChemin[predecesseurs[sommetCourant-1].get(i)-1] != Integer.MAX_VALUE && valuationChemin[predecesseurs[sommetCourant-1].get(i)-1] + g.getValuation(predecesseurs[sommetCourant-1].get(i), 
						sommetCourant) < valuationChemin[sommetCourant-1]) {
							valuationChemin[sommetCourant-1] = valuationChemin[predecesseurs[sommetCourant-1].get(i)-1] + 
							g.getValuation(predecesseurs[sommetCourant-1].get(i), sommetCourant);
						}
					}
				}
			}
			niveauCourant++;
		}

		niveauCourant--;
		sommetCourant = v;
		chemin.add(v);

		while (niveauCourant != valuationChemin[u-1]) { // les sommets de u � v (le plus court chemin)
			for (int i = 0; i < predecesseurs[sommetCourant-1].size(); i++) {
				if (valuationChemin[sommetCourant-1] - valuationChemin[predecesseurs[sommetCourant-1].get(i)-1] - 
				g.getValuation(predecesseurs[sommetCourant-1].get(i), sommetCourant) == 0) {
					chemin.add(predecesseurs[sommetCourant-1].get(i));
					niveauCourant--;
					sommetCourant = predecesseurs[sommetCourant-1].get(i);
				}
			}
		}
		Collections.reverse(chemin);

		return valuationChemin[v-1];
	}
}