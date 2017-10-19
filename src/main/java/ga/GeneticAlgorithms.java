package ga;

import java.io.*;
//2008-11-21
class GeneticAlgorithms{
	public static double crossoverRate;//交叉概率
	public static double mutateRate;//变异概率
	public static int maxGeneration;//进化代数
	public static int populationSize;//群体大小
	
	static {
		maxGeneration  = 500;
		populationSize = 4000;
		crossoverRate = 0.5;
		mutateRate = 0.01;
	}
	
	public static void main(String[] args)throws IOException{

		FileWriter fw = new FileWriter("result.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		
		Population pop = new Population(populationSize);
		pop.initPopulation();

		pw.println("初始种群:\n" + pop);
		Individual best = null;
		while(!pop.isEvolutionDone()){
			System.out.println(pop.getGeneration());
			pop.evolve();
			if(best == null){
				best = pop.bestIndividual;
                System.out.println("BEST is " + best.fitness);
            }else{
				if(best.fitness < pop.bestIndividual.fitness){
					best = pop.bestIndividual;
				}
			}

			pw.print("第" + pop.getGeneration() + "代Best:" + pop.bestIndividual );
			pw.print("第" + pop.getGeneration()  + "代current:" + pop.currentBest );
			pw.println("");		
		}
		pw.println();
		pw.println("第"+ pop.getGeneration()  + "代群体:\n" + pop);

		pw.println("BEST" + ((MRCIndividual)best).show());
		System.out.println("BEST" + ((MRCIndividual)best).show());

		pw.close();
	}
	
	public void print(){

	}
}





