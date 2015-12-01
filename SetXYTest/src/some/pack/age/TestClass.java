package some.pack.age;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;

public class TestClass {

	public static void main(String[] args) {
		for (int k = 0; k < 100; k++) {
			System.out.println("*****************");
			System.out.println("Graph " + k + " : ");
			System.out.println("*****************");
			Graph graph = new MultiGraph("Dorogovtsev mendes");
			Generator gen = new DMCoordGen(); // Coordinates Generator
			gen.addSink(graph);
			gen.begin();
			for (int j = 0; j < 5; j++) { // Graph Nodes
				gen.nextEvents();
			}
			gen.end();
			//graph.display(true);
			System.out.println("-----------------");
			System.out.println("First Coordinates");
			System.out.println("-----------------");
			for (int i = 0; i < graph.getNodeCount(); i++) { // First Reading
				Double[] attributes = (Double[]) graph.getNode(i).getAttribute("xy");
				Double x = attributes[0];
				Double y = attributes[1];
				System.out.println(x + " , " + y);
			}
			System.out.println("---------------");
			System.out.println("New Coordinates");
			System.out.println("---------------");
			Business business = new Business(graph);
			try {
				business.FDP(graph, 0.01);
				for (int i = 0; i < graph.getNodeCount(); i++) { // Second Reading
					Object[] attributes = graph.getNode(i).getAttribute("xy");
					Double x = (Double) attributes[0];
					Double y = (Double) attributes[1];
					System.out.println(x + " , " + y);
				}
				business.forceAttrGlobale();
				business.forceAttrMoyenne();
				business.forceRepGlobale();
				business.forceRepMoyenne();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//graph.display(true);
		}
	}
	
	private double cuts(Graph graph) {
		double cutsNumber = 0;
		return cutsNumber;
	}
	
}
