package some.pack.age;

import java.util.ArrayList;
import java.util.List;

import org.graphstream.algorithm.generator.Generator;
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

	public PopulationGenerator() {
		super();
		graphPop = new ArrayList<Graph>();
		popGen(100);
		//showPop();
	}

	public PopulationGenerator(int totalPop) {
		super();
		graphPop = new ArrayList<Graph>();
		this.totalPop = totalPop;
		popGen(this.totalPop);
		//showPop();
	}
	
	// Cette fonction génére une population de "totalPop" graphes.
	private void popGen(int totalPop) {
		for (int i = 0; i < totalPop; i++) {
			Graph graph = new MultiGraph("Dorogovtsev mendes");
			Generator gen = new DMCoordGen();
			gen.addSink(graph);
			gen.begin();
			for (int j = 0; j < 5; j++) {			// Boucle sur le nombre de noeuds du graphe
				gen.nextEvents();
			}
			gen.end();
			graphPop.add(graph);
		}
	}
	
	// Cette fonction affiche les coordonnées des noeuds des graphes de la population
	public void showPop() {
		for (int i = 0; i < graphPop.size(); i++) {
			System.out.println("*******************");
			System.out.println("Graphe " + i + " : ");
			System.out.println("*******************");
			//Collection<Node> nodes = graphPop.get(i).getNodeSet();
			System.out.println("Number of nodes : " + graphPop.get(i).getNodeCount());
			for (int j = 0; j < graphPop.get(i).getNodeCount(); j++) {
				Node nodeJ = graphPop.get(i).getNode(j);
				System.out.println("Node " + nodeJ.getId() + " : ");
				Double[] attributes = (Double[]) nodeJ.getAttribute("xy");
				Double x = attributes[0];
				Double y = attributes[1];
				System.out.println(x + " , " + y);
//				double[] position = GraphPosLengthUtils.nodePosition(nodeJ);
//				System.out.println(position[0] + " , " + position[1]);
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
