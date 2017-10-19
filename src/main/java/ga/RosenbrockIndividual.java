package ga;

class RosenbrockIndividual extends Individual {
	private double x1 , x2; // ���������
	//������chromosome�� (x1 , x2)�������
	
	RosenbrockIndividual(int chromlen){
		genelen = 10;
		chrom = new Chromosome(chromlen);
	}
	
	//����
	public void coding(){
		String code1,code2;
		code1 = codingVariable(x1);
		code2 = codingVariable(x2);
		
		chrom.setGene(0 , 9 , code1);
		chrom.setGene(10, 19 , code2);
	}
	
	//����
	public void decode(){
		String gene1,gene2;
		
		gene1 = chrom.getGene(0 , 9);
		gene2 = chrom.getGene(10 , 19);
		
		x1 = decodeGene(gene1);
		x2 = decodeGene(gene2);
	}
	
	//����Ŀ�꺯��ֵ
	public  void calTargetValue(){
		decode();
		targetValue = rosenbrock(x1 , x2);
	}
	
	//���������Ӧ��
	public void calFitness(){
		fitness = getTargetValue();
	}
	
	private String codingVariable(double x){
		double y = (((x + 2.048) * 1023) / 4.096);
		String code = Integer.toBinaryString((int) y);
		
		StringBuffer codeBuf = new StringBuffer(code);
		for(int i = code.length(); i<genelen; i++)
			codeBuf.insert(0,'0');
			
		return codeBuf.toString();
	}
	
	private double decodeGene(String gene){
		int value ;
		double decode;
		value = Integer.parseInt(gene, 2);
		decode = value/1023.0*4.096 - 2.048;
		
		return decode;
	}
		
	public String toString(){
		String str = "";
		///str = "������:" + chrom + "  ";
		///str+= "������:" + "[x1,x2]=" + "[" + x1 + "," + x2 + "]" + "\t";
		str+="����ֵ:" + rosenbrock(x1 , x2) + "\n";
		
		return 	str;	
	}
	
	/**
	 *Rosenbrock����:
	 *f(x1,x2) = 100*(x1**2 - x2)**2 + (1 - x1)**2
	 *�ڵ�x��[-2.048 , 2.048]��ʱ��
	 *���������������:
	 *f(2.048 , -2.048) = 3897.7342
	 *f(-2.048,-2.048) = 3905.926227
	 *���к���Ϊȫ�����㡣
	 */
	public static double rosenbrock(double x1 , double x2){
		double fun;
		fun = 100*Math.pow((x1*x1 - x2) , 2) + Math.pow((1 - x1) , 2);
		
		return fun;
	}
	
	//�����������
	public void generateIndividual(){
		x1 = Math.random() * 4.096 - 2.048;
		x2 = Math.random() * 4.096 - 2.048;
		
		//ͬ���������Ӧ��
		coding();
		calTargetValue();
		calFitness();
	}
}
