package some.pack.age;

import java.util.List;

import org.graphstream.graph.Graph;

public class AlgoGen {
	
	private List<Graph> pop;

	public void population() {
		PopulationGenerator generator = new PopulationGenerator(100);
		pop = generator.getGraphPop();
	}
	
	public void evaluation() {
		
	}
	
	public void crossOver() {
		
	}
	
	public void mutation() {
		
	}
	
}
