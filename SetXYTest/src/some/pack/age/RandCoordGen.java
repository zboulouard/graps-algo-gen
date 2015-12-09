package some.pack.age;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.graphstream.algorithm.generator.RandomGenerator;

public class RandCoordGen extends RandomGenerator {

	public void begin() {
		this.random = this.random == null ? new Random(
				System.currentTimeMillis()) : this.random;
		if (allowRemove)
			edgeIds = new ArrayList<String>();
		subset = new HashSet<Integer>();
		for (nodeCount = 0; nodeCount <= (int) averageDegree; nodeCount++)
			addNode(nodeCount + "", random.nextDouble(), random.nextDouble());
		for (int i = 0; i < nodeCount; i++)
			for (int j = i + 1; j < nodeCount; j++) {
				String edgeId = i + "_" + j;
				addEdge(edgeId, i + "", j + "");
				if (allowRemove)
					edgeIds.add(edgeId);
			}
	}
	
	public boolean nextEvents() {
		double addProbability = averageDegree / nodeCount;
		if (allowRemove)
			removeExistingEdges(1.0 / nodeCount);
		else
			addProbability /= 2;
		addNode(nodeCount + "", random.nextDouble(), random.nextDouble());
		addNewEdges(addProbability);
		nodeCount++;
		return true;
	}
	
}
