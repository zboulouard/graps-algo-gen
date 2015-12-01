package some.pack.age;

import org.graphstream.algorithm.Toolkit;
import java.util.Random;

import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Edge;

public class DMCoordGen extends DorogovtsevMendesGenerator {

	public void begin() {
		this.random = this.random == null ? new Random(
				System.currentTimeMillis()) : this.random;

		addNode("0", random.nextDouble(), random.nextDouble());
		addNode("1", random.nextDouble(), random.nextDouble());
		addNode("2", random.nextDouble(), random.nextDouble());

		addEdge("0-1", "0", "1");
		addEdge("1-2", "1", "2");
		addEdge("2-0", "2", "0");

		nodeNames = 3;
	}
	
	public boolean nextEvents() {
		String name = Integer.toString(nodeNames++);
		Edge edge = Toolkit.randomEdge(internalGraph, random);
		String n0 = edge.getNode0().getId();
		String n1 = edge.getNode1().getId();

		addNode(name, random.nextDouble(), random.nextDouble());

		addEdge(n0 + "-" + name, n0, name);
		addEdge(n1 + "-" + name, n1, name);

		return true;
	}
	
}
