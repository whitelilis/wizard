package ga;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

class Population{
	private int generation; //��Ⱥ�Ĵ���
	private int size;			//Ⱥ���С
	private Individual[] pop;   //��Ⱥ
	private double averageFitness;    //ƽ����Ӧ��
	private double[] relativeFitness;	//�����Ӧ��
	private int chromlen;//���򳤶�
	Individual bestIndividual;//��ǰ����Ӧ����õĸ���
	Individual worstIndividual;//��ǰ����Ӧ�����ĸ���
	Individual currentBest;//��Ŀǰ��Ϊֹ��õĸ���
	private int worstIndex;//bestIndividual��Ӧ�������±�

	
	public Population(int size){
		this.generation = 0;
		this.size = size;
		
		this.pop = new Individual[size];
		this.averageFitness = 0;
		this.relativeFitness = new double[size];
		ArrayList<Double> raw = new ArrayList<Double>();
		double allSum = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("/Users/liuzhe/Desktop/xx.txt")));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				double tmp = Double.parseDouble(line.trim());
				raw.add(tmp);
				allSum += tmp;
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		this.chromlen = raw.size();
		for(int i = 0; i < size; i++){
			pop[i] = new MRCIndividual(raw, allSum * 0.385455, allSum);
		}
	}
	
	
	//��ʼ����Ⱥ
	public void initPopulation(){
		for(int i = 0;i < size;i++){
			pop[i].generateIndividual();
		}
		
		findBestAndWorstIndividual();				
	}

	//----------------------------------------------------
	//����ѡ��
	public void  select(){
		double[] rouletteWheel; //����
		Individual[] childPop = new Individual[size];
		
		calRelativeFitness();
		
		//��������
		rouletteWheel  = new double[size];
		rouletteWheel[0] = relativeFitness[0];
		for(int i=1;i<size -1;i++){
			rouletteWheel[i] = relativeFitness[i] + rouletteWheel[i - 1];
		}
		rouletteWheel[size - 1] = 1;
		
		//���ж���ѡ��,��������Ⱥ
		for(int i = 0;i < size ; i++){
			double rnd = rand();
			for(int j = 0; j < size; j++){
				if(rnd < rouletteWheel[j]){
					childPop[i] = pop[j];
					break;
				}	
			}		 
		}
		
		for(int i = 0;i < size; i++){
			pop[i] = childPop[i];
		}
		
		//return 	childPop;
	}
	
	//������Ӧ��
	private double calTotalFitness(){
		double total = 0;
		for(int i = 0 ; i < size ;i++)
			total += pop[i].getFitness();
		return total;
	}
		
	//���������Ӧ��
	public double[] calRelativeFitness(){
		double totalFitness = calTotalFitness();
		for(int i = 0 ;i < size ; i++){
			relativeFitness[i] = pop[i].getFitness() / totalFitness;	
		}
		
		return relativeFitness;
	}
	
	//================================
	
	//------------------------------------------------------
	//���㽻��
	public void crossover(){
		for(int i = 0 ; i < size/2*2; i+=2){
			int rnd;
			//����������
			rnd = rand(i , size);

			if(rnd != i)
				exchange(pop , i , rnd);
				
			rnd = rand(i , size);
			if(rnd != i+1)
				exchange(pop , i + 1 , rnd);	
					
			//����
			double random = rand();

			if(random < GeneticAlgorithms.crossoverRate){
				cross(i);
			}			
		}
	}
	
	//ִ�н������
	private void cross(int i){
		String chromFragment1,chromFragment2;//����Ƭ��
		
		int rnd = rand(0 , getChromlen() - 1);//�����Ϊrnd֮��,���ܵ�λ����chromlen - 1��.
		chromFragment1 = pop[i].getChrom(rnd + 1 , getChromlen() - 1);
		chromFragment2 = pop[i+1].getChrom(rnd + 1 , getChromlen() - 1);
			
		pop[i].setChrom(rnd + 1 , getChromlen() - 1 , chromFragment2);
		pop[i+1].setChrom(rnd + 1 , getChromlen() - 1 , chromFragment1);			
	}
	
	//���������
	private int rand(int start , int end){//��������Ϊ[start , end)���������
		return (int)(rand()*(end - start) + start);
	}
	
	//����
	private void exchange(Individual[] p ,int src , int dest){
		Individual temp;
		temp = p[src];
		p[src] = p[dest];
		p[dest] = temp;	
	}
	//==============================

	//-----------------------------------------------------
	//����
	public void mutate(){
		for(int i = 0 ; i < size;i++){
			for(int j = 0 ;j < getChromlen(); j++){
				if(rand() < GeneticAlgorithms.mutateRate){
					pop[i].mutateSingleBit(j);
					///System.out.print("����"+ i +" - "+ j + "  ");///
				}	
			}
		}
	}
	//==============================
	
	//-----------------------------------------------------
	//����
	public void evolve(){
		select();
		crossover();
		mutate();
		evaluate();	
	}
	
	
	//==============================
	//����Ŀ�꺯��ֵ����Ӧ�ȡ��ҳ����Ÿ��塣
	public void evaluate(){
		//ͬ��Ŀ�꺯��ֵ����Ӧ��
		for(int i = 0; i < size; i++){
			pop[i].calTargetValue();
			pop[i].calFitness();
		}
		
		//ʹ�����ű������(Elitist Model)�������Ÿ���
		findBestAndWorstIndividual();
		pop[worstIndex] = (Individual)currentBest.clone();
		
		generation++;	
	}	
	//�ҳ���Ӧ�����ĸ���
	public void findBestAndWorstIndividual(){
		bestIndividual = worstIndividual = pop[0];
		for(int i = 1; i <size;i++){
			if(pop[i].fitness > bestIndividual.fitness){
				bestIndividual = pop[i];
			}
			if(pop[i].fitness < worstIndividual.fitness){
				worstIndividual = pop[i];
				worstIndex = i;
			}
		}
	
		if( generation == 0 ){//��ʼ��Ⱥ
			currentBest = (Individual)bestIndividual.clone();
		}else{
			if(bestIndividual.fitness > currentBest.fitness)
				currentBest = (Individual)bestIndividual.clone();
		}
	}
	
	//�жϽ����Ƿ����
	public boolean isEvolutionDone(){
		if(generation < GeneticAlgorithms.maxGeneration)
			return false;
		return true;	
	}
		
	//����ƽ����Ӧ��
	public double calAverageFitness(){
		for(int i = 0 ; i < size; i++){
			averageFitness += pop[i].getFitness();
		}
		averageFitness/=size;
			
		return averageFitness;
	} 
				
	//���������
	private double rand(){
		return Math.random();
	}
	
	public int getChromlen(){
		return chromlen;
	}
	
	public void setGeneration(int generation){
		this.generation = generation;
	}
	
	public int getGeneration(){
		return generation;
	}
	/*
	public String printPop(Individual[] pop){
		String str="";
		for(int i = 0 ; i < size ; i++)
			str += pop[i];
		return str;
	}*/
	
	public String toString(){
		String str="";
		for(int i = 0 ; i < size ; i++)
			str += pop[i];
		return str;
	}	
}