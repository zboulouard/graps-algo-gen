package some.pack.age;

public class Test {

	public static void main(String[] args) {
		AlgoGen algoGen = new AlgoGen();
		algoGen.population();
		for (int i = 0; i < 10; i++) {
			System.out.println("-----------------------");
			System.out.println("Itération Numéro : " + i);
			System.out.println("-----------------------");
			algoGen.fitnessEvaluation();
			algoGen.selection(); // A faire!!! greedy selection (test sur les fils et remplacement des pires parents)
			algoGen.crossOver();
			algoGen.finalSelection();
		}
		//algoGen.finalSelection();
		System.out.println("");
	}

}
