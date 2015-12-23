package some.pack.age;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class AlgoGen {
	
	// Population
	private List<Graph> pop;
	// Tableau des énergies utilisé pour évaluation
	private Double[] energieEval;
	// Tableau contenant les meilleurs graphes de chaque itération
	private Double[] bestOfTheBest;
	// Map contenant les graphes ainsi que leurs énergies
	private Map<Double, Graph> energieGraph;
	// Map contenant les meilleurs graphes ainsi que leurs énergies
	private Map<Double, Graph> bestGraphs;
	
	public AlgoGen() {
		pop = new ArrayList<Graph>();
		energieGraph = new HashMap<Double, Graph>();
		bestGraphs = new HashMap<Double, Graph>();
	}

	// Génération de la population initiale
	public void population() {
		PopulationGenerator generator = new PopulationGenerator(100);
		pop = generator.getGraphPop();
//		forceEval = new Double[pop.size()];
		System.out.println("Début");
		System.out.println("*****");
		System.out.println("Taille : " + pop.size());
	}
	
	// Evaluation de la fitness
	public void fitnessEvaluation() {
		energieEval = new Double[pop.size()];
		for(int i=0; i<pop.size(); i++) {
			Business business = new Business(pop.get(i));
			business.FDP(pop.get(i), 1.0);
			Double energieGlobaleGraph = business.energieGlobale();
			//System.out.println(pop.get(i) + " , " + business.energieGlobale());
			energieEval[i] = energieGlobaleGraph;
			energieGraph.put(energieGlobaleGraph, pop.get(i));
		}
	}
	
	// Sélection pour croisement
	public void selection() {
		int best = pop.size() / 10;
		Arrays.sort(energieEval);
		for(int i=0; i<best; i++) {
			bestGraphs.put(energieEval[i], energieGraph.get(energieEval[i]));
		}
	}
	
	// Sélection du graphe final pour chaque itération (à modifier pour sélectionner le meilleur graphe)
	public void finalSelection() {
//		System.out.println("----------------------------");
//		System.out.println("Les meilleurs graphes sont :");
//		System.out.println("----------------------------");
//		for(Double key : bestGraphs.keySet()) {
//			System.out.println(key + " , " + bestGraphs.get(key));
//		}
		System.out.println("------------------------");
		System.out.println("Le meilleur graphe est :");
		System.out.println("------------------------");
		System.out.println(energieEval[0] + " , " + bestGraphs.get(energieEval[0]));
	}
	
	// Croisement
	public void crossOver() {
		List<Graph> graphs = new ArrayList<Graph>();
		graphs.addAll(bestGraphs.values());
		ListIterator<Graph> listItr = graphs.listIterator();
		while(listItr.hasNext()) {
			if(!listItr.hasPrevious()) {
				listItr.next();
			} else {
				Graph g1 = listItr.previous();
				listItr.next();
				Graph g2 = listItr.next();
				croisementGraphs(g1, g2);
			}
		}
		System.out.println("Fin");
		System.out.println("***");
		System.out.println("Taille : " + pop.size());
		System.out.println("");
	}
	
	// Mutation et Greedy Acceptance
	private void mutation(Graph g) {
		Business business = new Business(g);
		business.FDP(g, 1.0);
		//greedyAcceptance(g);
		Double energieg = business.energieGlobale();
		for(Double key : energieGraph.keySet()) {
			if(energieg >= key) {
				continue;
			} else {
				//System.out.println("Graphe supprimé");
//				bestGraphs.remove(key);
//				key = energieg;
				pop.add(g);
				energieGraph.put(key, g);
				pop.remove(energieGraph.get(key));
//				energieGraph.remove(key);
				//System.out.println("Graphe ajouté");
				break;
			}
		}
	}
	
	private void greedyAcceptance(Graph g) {
		Business business = new Business(g);
		Double energieg = business.energieGlobale();
		for(Double key : energieGraph.keySet()) {
			if(energieg < key) {
				pop.remove(energieGraph.get(key));
				energieGraph.remove(key);
//				key = energieg;
				pop.add(g);
				energieGraph.put(energieg, g);
				break;
			}
		}
	}
	
	// Croisement effectif
	private void croisementGraphs(Graph g1, Graph g2) {
		ConnectedComponents ccs1 = new ConnectedComponents(g1);
		List<Node> pgpc1 = ccs1.getGiantComponent();
		ConnectedComponents ccs2 = new ConnectedComponents(g2);
		List<Node> pgpc2 = ccs2.getGiantComponent();
        // Croisement des pgpc
		int min = Integer.min(pgpc1.size(), pgpc2.size());
		for(int i=0; i<min; i++) {
    		Object[] attr1 = pgpc1.get(i).getAttribute("xy");
    		Object[] attr2 = pgpc2.get(i).getAttribute("xy");
			Double xe = (Double) attr1[0];
			attr1[0] = attr2[0];
			attr2[0] = xe;
			Double ye = (Double) attr1[1];
			attr1[1] = attr2[1];
			attr2[1] = ye;
			pgpc1.get(i).setAttribute("xy", (Object) attr1[0], (Object) attr1[1]);
			pgpc2.get(i).setAttribute("xy", (Object) attr2[0], (Object) attr2[1]);
		}
		// Remplacement des pgpc
        for(Node n : pgpc2) {
        	ccs1.getGiantComponent().set(n.getIndex(), n);
        }
        for(Node n : pgpc1) {
        	ccs2.getGiantComponent().set(n.getIndex(), n);
        }
        // Mutation des nouveaux graphes
        mutation(g1);
        mutation(g2);
        // Ajout des nouveaux graphes à la population
//        pop.add(g1);
//        pop.add(g2);
	}
}