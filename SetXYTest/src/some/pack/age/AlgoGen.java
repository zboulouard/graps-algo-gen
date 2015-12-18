package some.pack.age;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class AlgoGen {
	
	private List<Graph> pop;
	private Double[] forceEval;
	private Map<Double, Graph> forceGraph;
	private Map<Double, Graph> bestGraphs;
	
	public AlgoGen() {
		pop = new ArrayList<Graph>();
		forceGraph = new HashMap<Double, Graph>();
		bestGraphs = new HashMap<Double, Graph>();
	}

	public void population() {
		PopulationGenerator generator = new PopulationGenerator(100);
		pop = generator.getGraphPop();
		forceEval = new Double[pop.size()];
	}
	
	public void fitnessEvaluation() {
		for(int i=0; i<pop.size(); i++) {
			Business business = new Business(pop.get(i));
			business.FDP(pop.get(i), 1.0);
			Double forceGlobaleGraph = Math.abs(business.forceAttrGlobale()) + Math.abs(business.forceRepGlobale());
			forceEval[i] = forceGlobaleGraph;
			forceGraph.put(forceGlobaleGraph, pop.get(i));
		}
	}
	
	public void selection() {
		int best = pop.size() / 10;
		//List<Graph> bestGraphs = new ArrayList<Graph>();
		Arrays.sort(forceEval, Collections.reverseOrder());
		for(int i=0; i<best; i++) {
			//bestGraphs.add(forceGraph.get(forceEval[i]));
			bestGraphs.put(forceEval[i], forceGraph.get(forceEval[i]));
		}
	}
	
	public void crossOver() {
		List<Graph> graphs = new ArrayList<Graph>();
		graphs.addAll(bestGraphs.values());
		ListIterator<Graph> listItr = graphs.listIterator();
		while(listItr.hasNext()) {
			if(!listItr.hasPrevious()) {
				listItr.next();
			} else {
				Graph g1 = listItr.previous();
				Graph g2 = listItr.next();
				croisementGraphs(g1, g2);
				listItr.next();
			}
		}
		System.out.println("");
	}
	
	public void mutation() {
		
	}
	
	private void croisementGraphs(Graph g1, Graph g2) {
		System.out.println("*****");
		System.out.println("Avant");
		System.out.println("*****");
		ConnectedComponents ccs1 = new ConnectedComponents(g1);
		List<Node> pgpc1 = ccs1.getGiantComponent();
		System.out.println("PGPC 1 :");
		System.out.println("--------");
        for(Node n : pgpc1) {
    		Object[] attr = n.getAttribute("xy");
			Double x = (Double) attr[0];
			Double y = (Double) attr[1];
			System.out.println(x + " , " + y);
        }
		ConnectedComponents ccs2 = new ConnectedComponents(g2);
		List<Node> pgpc2 = ccs2.getGiantComponent();
		System.out.println("PGPC 2 :");
		System.out.println("--------");
        for(Node n : pgpc2) {
    		Object[] attr = n.getAttribute("xy");
			Double x = (Double) attr[0];
			Double y = (Double) attr[1];
			System.out.println(x + " , " + y);
        }
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
			// Tester si le changement a eu lieu au niveau de tout le graphe et non pas uniquement au niveau de la pgpc
			System.out.println("");
		}
		System.out.println("*****");
		System.out.println("Après");
		System.out.println("*****");
		System.out.println("PGPC 1 :");
		System.out.println("--------");
        for(Node n : pgpc1) {
    		Object[] attr = n.getAttribute("xy");
			Double x = (Double) attr[0];
			Double y = (Double) attr[1];
			System.out.println(x + " , " + y);
        }
		System.out.println("PGPC 2 :");
		System.out.println("--------");
        for(Node n : pgpc2) {
    		Object[] attr = n.getAttribute("xy");
			Double x = (Double) attr[0];
			Double y = (Double) attr[1];
			System.out.println(x + " , " + y);
        }
	}
}