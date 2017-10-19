package ga;

import java.io.*;
//2008-11-21
class GeneticAlgorithms{
	public static double crossoverRate;//�������
	public static double mutateRate;//�������
	public static int maxGeneration;//��������
	public static int populationSize;//Ⱥ���С
	
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

		pw.println("��ʼ��Ⱥ:\n" + pop);
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

			pw.print("��" + pop.getGeneration() + "��Best:" + pop.bestIndividual );
			pw.print("��" + pop.getGeneration()  + "��current:" + pop.currentBest );
			pw.println("");		
		}
		pw.println();
		pw.println("��"+ pop.getGeneration()  + "��Ⱥ��:\n" + pop);

		pw.println("BEST" + ((MRCIndividual)best).show());
		System.out.println("BEST" + ((MRCIndividual)best).show());

		pw.close();
	}
	
	public void print(){

	}
}





