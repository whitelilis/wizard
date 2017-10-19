package ga;

import java.util.ArrayList;

class MRCIndividual extends Individual {
	public int len;
	public double aimSum;
	public double allSum;
	String zeroOneString;
	public static final int select = 897;
	private ArrayList<Double> raw;
	MRCIndividual(ArrayList<Double> raw, double aimSum, double allSum){
		this.raw = raw;
		this.aimSum = aimSum;
		this.allSum = allSum;
		this.len = raw.size();
		chrom = new Chromosome(len);
	}
	
	//编码
	@Override
	public void coding(){
		chrom.setGene(0 , len - 1, zeroOneString);
	}
	
	//解码
	@Override
	public void decode(){
		zeroOneString = chrom.getGene(0 , len - 1);
	}
	
	//计算目标函数值
	@Override
	public  void calTargetValue(){
		decode();
		targetValue = 0;
		int count = 0;
		for(int i = 0; i < zeroOneString.length(); ++i){
			if(zeroOneString.charAt(i) == '1'){
				targetValue += raw.get(i);
				/*
				count = count + 1;
				if(count > select){
					targetValue = 0;
					break;
				}
				*/
			}
		}
	}
	
	//计算个体适应度
	@Override
	public void calFitness(){
		fitness = - Math.abs(this.aimSum - getTargetValue());
	}
	

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.fitness + "=");
		int count =  0;

	    for(int i = 0; i < zeroOneString.length(); ++i){
	    	if(zeroOneString.charAt(i) == '1'){
	    		count = count + 1;
			}
		}
		sb.append(count);
		return sb.toString();
	}


	public String show(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.fitness + "=");
		int count = 0;
		for(int i = 0; i < zeroOneString.length(); ++i){
			if(zeroOneString.charAt(i) == '1'){
				count = count + 1;
			}
		}
		sb.append(count);
		sb.append(" : ");

		sb.append(targetValue / allSum);
		sb.append(" <> " + (targetValue /allSum -  aimSum / allSum));
		sb.append(" : ");

		for(int i = 0; i < zeroOneString.length(); ++i) {
			if (zeroOneString.charAt(i) == '1') {
				sb.append(raw.get(i) + "+");
			}
		}

		return sb.toString();
	}


	//随机产生个体
	@Override
	public void generateIndividual(){
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(int i = 0; i < len; ++i){
			if(Math.random() < 0.3){
				sb.append('1');
				count = count + 1;
			}else{
				sb.append('0');
			}
		}
		zeroOneString = sb.toString();
		//同步编码和适应度
		coding();
		calTargetValue();
		calFitness();
	}
}
