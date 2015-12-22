package some.pack.age;

public class Test {

	public static void main(String[] args) {
		AlgoGen algoGen = new AlgoGen();
		algoGen.population();
		for (int i = 0; i < 5; i++) {
			algoGen.fitnessEvaluation();
			algoGen.selection();
			algoGen.crossOver();
		}
		System.out.println("");
	}

}
