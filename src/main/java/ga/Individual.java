package ga;

abstract class Individual implements Cloneable{
	protected Chromosome chrom;//���������:һ��������Ⱦɫ���ɶ���������
	protected int genelen;//���򳤶�
	protected double fitness;//��Ӧ��
	protected double targetValue;//Ŀ�꺯��ֵ
	
	public abstract void coding();//����
	public abstract void decode();//����
	public abstract void calFitness();//���������Ӧ��
	public abstract void generateIndividual();//�����������
	public abstract void calTargetValue();//��ȡĿ�꺯��ֵ 
	
	public double getFitness(){
		return fitness;
	}
	
	public double getTargetValue(){
		return targetValue;
	}
	
	public int getChromlen(){
		return chrom.getLength();
	}
	
	public boolean setChrom(int begin , int end , String gene){
		return chrom.setGene(begin,end,gene);
	}
	
	public String getChrom(int begin , int end){
		return chrom.getGene(begin,end);
	}
	
	public void mutateSingleBit(int index){
		String gene , gn;
		gene = chrom.getGene(index , index);
		gn = gene.equals("0") ? "1":"0";
		chrom.setGene(index , index , gn);
	}
	
	@Override
	public Object clone(){
		Individual indv = null;
		
		try{
			indv = (Individual)super.clone();
			indv.chrom = (Chromosome)chrom.clone();
		}catch(CloneNotSupportedException e ){
			System.out.println(e.getMessage());
		}
		
		return indv;
	}
	
}