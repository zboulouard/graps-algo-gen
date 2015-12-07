package some.pack.age;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graphstream.graph.Graph;

public class AlgoGen {
	
	private List<Graph> pop;
	private Double[] forceEval;
	private Map<Double, Graph> forceGraph;
	
	public AlgoGen() {
		pop = new ArrayList<Graph>();
		forceGraph = new HashMap<Double, Graph>();
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
		List<Graph> bestGraphs = new ArrayList<Graph>();
		Arrays.sort(forceEval, Collections.reverseOrder());
		for(int i=0; i<best; i++) {
			bestGraphs.add(forceGraph.get(forceEval[i]));
		}
	}
	
	public void crossOver() {
		
	}
	
	public void mutation() {
		
	}
	
}
