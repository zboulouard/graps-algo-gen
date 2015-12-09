package some.pack.age;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphPosLengthUtils;

public class Business {
	
	// Constante nominale représentant la taille au repos du ressort
	private Double K;
	
	// Liste des valeurs des forces de répulsion appliquées 
	// utilisée pour calculer la force de répulsion globale et moyenne
	private List<Double> listForcesRep;
	
	// Liste des valeurs des forces d'attraction appliquées 
	// utilisée pour calculer la force d'attraction globale et moyenne
	private List<Double> listForcesAttr;
	
	public Business(Graph graph) {
		super();
		this.K = moyenne(graph);
		listForcesRep = new ArrayList<Double>();
		listForcesAttr = new ArrayList<Double>();
//		repulsion(graph);
//		attraction(graph);
	}
	
	public Business() {
		super();
	}

	public Double getK() {
		return K;
	}

	public void setK(Double k) {
		K = k;
	}

	// Distance entre deux noeuds
	private Double distance(Node n1, Node n2) {
		Double d = 0.0;
		Edge e = n1.getEdgeBetween(n2);
		if(e==null) {
			d = distEuc(n1, n2);
		} else {
			try {
				d = GraphPosLengthUtils.edgeLength(e);
			} catch (Exception e2) {}
		}
		return d;
	}
	
	// Distance Euclidienne
	private Double distEuc(Node n1, Node n2) {
		Double d = 0.0;
		Object[] n1Attributes = n1.getAttribute("xy");
		Double x1 = (Double) n1Attributes[0];
		Double y1 = (Double) n1Attributes[1];
		Object[] n2Attributes = n2.getAttribute("xy");
		Double x2 = (Double) n2Attributes[0];
		Double y2 = (Double) n2Attributes[1];
		d = Math.sqrt((Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
		return d;
	}
	
	// Distance Euclidienne entre deux ensembles de noeuds
	private Double distance(Collection<Node> l1, Collection<Node> l2) {
		ArrayList<Double> dist = new ArrayList<Double>();
		for (Node i : l1) {
			for (Node j : l2) {
				dist.add(distEuc(i, j));
			} 
		}
		Double min = 0.0;
		for(int i=0; i<dist.size(); i++) {
			if(dist.get(i)<=min) {
				min = dist.get(i);
			}
		}
		return min;
	}
	
	private Double moyenne(Graph graph) {
		Double dist = 0.0;
		Double moy = 0.0;
		Collection<Node> nodeList = graph.getNodeSet();
		for(int i=0; i<nodeList.size(); i++) {
			for(int j=i+1; j<nodeList.size(); j++) {
				dist += distance(graph.getNode(i), graph.getNode(j));
			}
		}
		moy = dist / nodeList.size();
		return moy;
	}
	
	// Force de répulsion calculée selon l'équation : Fr(n1, n2) = K²/dist(n1, n2)
	public Double repulsion(Node n1, Node n2) {
		Double rep = 0.0;
		rep = Math.pow(K, 2) / distEuc(n1, n2);
		listForcesRep.add(rep);
		return rep;
	}
	
	// Force de répulsion globale calculée pour des fins d'évaluation pour l'algorithme génétique
	public Double forceRepGlobale() {
		Double frg = 0.0;
		for(int i=0; i<listForcesRep.size(); i++) {
			frg += listForcesRep.get(i);
		}
		System.out.println("Force de Réplusion Globale : " + frg);
		return frg;
	}
	
	// Force de réplusion moyenne calculée pour des fins d'évaluation pour l'algorithme génétique
	public void forceRepMoyenne() {
		Double frm = 0.0;
		for(int i=0; i<listForcesRep.size(); i++) {
			frm += listForcesRep.get(i) / listForcesRep.size();
		}
		System.out.println("Force de Répulsion Moyenne : " + frm);
	}
	
	// Force d'attraction calculée au niveau des noeuds deux à deux voisins 
	// selon l'équation : Fa(n1, n2) = -dist²(n1, n2)/K
	public Double attraction(Node n1, Node n2) {
		Double attr = 0.0;
		attr = -Math.pow(K, 2) / distance(n1, n2);
		listForcesAttr.add(attr);
		return attr;
	}
	
	// Force d'attraction globale calculée pour des fins d'évaluation pour l'algorithme génétique
	public Double forceAttrGlobale() {
		Double fag = 0.0;
		for(int i=0; i<listForcesAttr.size(); i++) {
			fag += listForcesAttr.get(i);
		}
		System.out.println("Force d'Attraction Globale : " + fag);
		return fag;
	}
	
	// Force d'attraction moyenne calculée pour des fins d'évaluation pour l'algorithme génétique
	public void forceAttrMoyenne() {
		Double fam = 0.0;
		for(int i=0; i<listForcesAttr.size(); i++) {
			fam += listForcesAttr.get(i) / listForcesAttr.size();
		}
		System.out.println("Force d'Attraction Moyenne : " + fam);
	}
	
	public void FDP(Graph graph, Double tolerence) {
		Double step = 0.01;
		Collection<Node> n0 = graph.getNodeSet();
		do {
			for(int i=0; i<graph.getNodeCount(); i++) {
				Double f = 0.0;
				for(int j=0; j<graph.getNodeCount(); j++) {
					if(j==i) {
						continue;
					}
					try {
						if(graph.getNode(j).hasEdgeBetween(i)) {
							f = attraction(graph.getNode(i), graph.getNode(j));
							Object[] attr = graph.getNode(j).getAttribute("xy");
							Double x = (Double) attr[0];
							Double y = (Double) attr[1];
							x = x + step * (f / Math.abs(f));
							y = y + step * (f / Math.abs(f));
							attr[0] = x;
							attr[1] = y;
							graph.getNode(j).setAttribute("xy", (Object) attr[0], (Object) attr[1]);
						} else {
							f = repulsion(graph.getNode(i), graph.getNode(j));
							if(Double.isInfinite(f)) {
								continue;
							}
							Object[] attr = graph.getNode(j).getAttribute("xy");
							Double x = (Double) attr[0];
							Double y = (Double) attr[1];
							x = x + step * (f / Math.abs(f));
							y = y + step * (f / Math.abs(f));
							attr[0] = x;
							attr[1] = y;
							graph.getNode(j).setAttribute("xy", (Object) attr[0], (Object) attr[1]);
						}
//						Object[] attr = graph.getNode(j).getAttribute("xy");
//						Double x = (Double) attr[0];
//						Double y = (Double) attr[1];
//						x = x + step * (f / Math.abs(f));
//						y = y + step * (f / Math.abs(f));
//						attr[0] = x;
//						attr[1] = y;
//						graph.getNode(j).setAttribute("xy", (Object) attr[0], (Object) attr[1]);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		} while(distance(graph.getNodeSet(), n0) >= tolerence * K);
	}
}
