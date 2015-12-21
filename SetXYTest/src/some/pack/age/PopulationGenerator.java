package some.pack.age;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/*
 * Cette classe génére une population de "totalPop" graphes, utilisée comme population initiale pour 
 * l'algorithme génétique.
 * 
 */
public class PopulationGenerator {

	// Nombre total de la population des graphes.
	private int totalPop;
	
	// Population des graphes générés.
	private List<Graph> graphPop;
	
	private Random random;

	public PopulationGenerator() {
		super();
		random = new Random();
		graphPop = new ArrayList<Graph>();
		popGen(100);
	}

	public PopulationGenerator(int totalPop) {
		super();
		graphPop = new ArrayList<Graph>();
		this.totalPop = totalPop;
		popGen(this.totalPop);
	}
	
	// Cette fonction génère une population de "totalPop" graphes.
	private void popGen(int totalPop) {
		for (int i = 0; i < totalPop; i++) {
			this.random = this.random == null ? new Random(
					System.currentTimeMillis()) : this.random;
			Graph graph = new MultiGraph("Graph " + i);
			Node a = graph.addNode("A");
			a.addAttribute("xy", random.nextDouble(), random.nextDouble());
			Node b = graph.addNode("B");
			b.addAttribute("xy", random.nextDouble(), random.nextDouble());
			Node c = graph.addNode("C");
			c.addAttribute("xy", random.nextDouble(), random.nextDouble());
			Node d = graph.addNode("D");
			d.addAttribute("xy", random.nextDouble(), random.nextDouble());
			Node e = graph.addNode("E");
			e.addAttribute("xy", random.nextDouble(), random.nextDouble());
			graph.addEdge("AB", a, b);
			graph.addEdge("BC", b, c);
			graph.addEdge("CA", c, a);
			graph.addEdge("DE", d, e);
			graphPop.add(graph);
		}
	}
	
	// Cette fonction affiche les coordonnées des noeuds des graphes de la population
	public void showPop() {
		for (int i = 0; i < graphPop.size(); i++) {
			System.out.println("*******************");
			System.out.println("Graphe " + i + " : ");
			System.out.println("*******************");
			System.out.println("Number of nodes : " + graphPop.get(i).getNodeCount());
			for (int j = 0; j < graphPop.get(i).getNodeCount(); j++) {
				Node nodeJ = graphPop.get(i).getNode(j);
				System.out.println("Node " + nodeJ.getId() + " : ");
				Double[] attributes = (Double[]) nodeJ.getAttribute("xy");
				Double x = attributes[0];
				Double y = attributes[1];
				System.out.println(x + " , " + y);
			}
		}
	}

	public List<Graph> getGraphPop() {
		return graphPop;
	}

	public void setGraphPop(List<Graph> graphPop) {
		this.graphPop = graphPop;
	}

	public int getTotalPop() {
		return totalPop;
	}

	public void setTotalPop(int totalPop) {
		this.totalPop = totalPop;
	}

	@Override
	public String toString() {
		return "Population totale des graphes : " + totalPop;
	}
	
}
