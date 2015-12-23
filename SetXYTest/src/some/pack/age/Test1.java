package some.pack.age;

import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class Test1 {

	public static void main(String[] args) {
		// Création d'un graphe de 50 noeuds avec des coordonnées bien connues (matrice de 50) depuis la courbe excel
		/*
		 * 1) FDP
		 * 2) Fonction
		 * 3) Algo Gen (énérgie) : greedy acceptance
		 * 4) 3D
		 * 
		 */
		Random random = new Random();
		random = random == null ? new Random(
				System.currentTimeMillis()) : random;
		Graph graph = new MultiGraph("Graph ");
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
		Business business = new Business(graph);
		business.energie(a, b);
		graph.display();
	}

}
