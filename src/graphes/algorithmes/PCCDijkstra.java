package graphes.algorithmes;

import graphes.IPCC;
import graphes.exception.ArcNegatifEx;
import graphes.IGraphe;
import graphes.exception.AucunCheminEx;
import graphes.types.Graphe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PCCDijkstra implements IPCC
{

    public boolean estOk(IGraphe graphe)
    {
        boolean grapheEstOk = true;
        for (int s1 = 1; s1 <= graphe.getNbSommets(); s1++)
        {
            for (int s2 = 1; s2 <= graphe.getNbSommets(); s2++)
            {
                if (graphe.aArc(s1, s2))
                {
                    if (graphe.getValuation(s1, s2) < 0)
                    {
                        grapheEstOk = false;
                    }
                }
            }
        }
        return grapheEstOk;
    }

    public int plusCourtChemin(IGraphe graphe, int sommet_debut, int sommet_fin, List<Integer> chemin)
    {
        if (estOk(graphe))
        {
            chemin.clear();

            int nombre_sommets = graphe.getNbSommets();
            List<Integer> sommets_non_connues = new ArrayList<>();
            for (int i = 1; i <= nombre_sommets; i++)
            {
                if (i != sommet_debut)
                {
                    sommets_non_connues.add(i);
                }
            }
            int[] sommets_precedents = new int[nombre_sommets];
            int[] sommets_distances = new int[nombre_sommets];

            for (int i = 1; i <= nombre_sommets; ++i)
            {
                sommets_distances[i-1] = Graphe.INFINI;
            }
            sommets_distances[sommet_debut-1] = 0;

            int sommet_debut_distance = 0;
            int sommet_courant = sommet_debut;
            while (sommets_non_connues.isEmpty() == false)
            {
                for (int sommet = 1; sommet <= nombre_sommets; sommet++)
                {
                    if (sommet != sommet_courant && sommets_non_connues.contains(sommet))
                    {
                        if (graphe.aArc(sommet_courant, sommet))
                        {
                            if (sommet_debut_distance + graphe.getValuation(sommet_courant, sommet) < sommets_distances[sommet-1])
                            {
                                sommets_distances[sommet-1] = sommet_debut_distance + graphe.getValuation(sommet_courant, sommet);
                                sommets_precedents[sommet-1] = sommet_courant;
                            }
                        }
                    }
                }

                int sommet_distance_plus_petite = sommets_non_connues.get(0);
                for (int sommet_non_connue : sommets_non_connues)
                {
                    if (sommets_distances[sommet_non_connue-1] < sommets_distances[sommet_distance_plus_petite-1])
                        sommet_distance_plus_petite = sommet_non_connue;
                }
                sommet_courant = sommet_distance_plus_petite;

                sommets_non_connues.remove(Integer.valueOf(sommet_courant));
                sommet_debut_distance = sommets_distances[sommet_courant-1];
            }

            sommet_courant = sommet_fin;
            while (sommet_courant != sommet_debut)
            {
                chemin.add(sommet_courant);
                sommet_courant = sommets_precedents[sommet_courant-1];
                if (sommet_courant == 0)
                {
                    throw new AucunCheminEx(sommet_debut, sommet_fin);
                }

            }
            chemin.add(sommet_debut);
            Collections.reverse(chemin);

            return sommets_distances[sommet_fin-1];
        }

        else
        {
            throw new ArcNegatifEx();
        }
    }
}
