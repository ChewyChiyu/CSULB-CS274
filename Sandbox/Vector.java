public class Vector{

	private BigNumber[] vec;

	public Vector(String[] vec){
		this.vec = new BigNumber[vec.length];
		for(int index = 0; index < vec.length; index++){
			this.vec[index] = new BigNumber(vec[index]);
		}
	}

	public Vector(String vecStr){
		String[] vecArr = vecStr.substring(1,vecStr.length()-1).split(",");
		this.vec = new BigNumber[vecArr.length];
		for(int index = 0; index < vecArr.length; index++){
			this.vec[index] = new BigNumber(vecArr[index]);
		}
	}

	public Vector add(Vector v2){
		balance(v2);
		String[] s3 = new String[vec.length];
		for(int index = 0; index < vec.length; index++){
			s3[index]= vec[index].add(v2.getComponents()[index]).toString();
		}
		return new Vector(s3);
	}

	public Vector subtract(Vector v2){
		balance(v2);
		String[] s3 = new String[vec.length];
		for(int index = 0; index < vec.length; index++){
			s3[index]= vec[index].sub(v2.getComponents()[index]).toString();
		}
		return new Vector(s3);
	}

	public Vector multiply(BigNumber bn){
		String[] s3 = new String[vec.length];
		for(int index = 0; index < vec.length; index++){
			s3[index]= vec[index].multiply(bn).toString();
		}
		return new Vector(s3);
	}

	private void balance(Vector v2){
		if(vec.length>v2.getComponents().length){
			v2.balanceCompIndex(this);
		}else{
			balanceCompIndex(v2);
		}
	}

	private void balanceCompIndex(Vector v2){
		BigNumber[] s3 = new BigNumber[v2.getComponents().length];
			for(int index = 0; index < s3.length; index++){
				if(index < vec.length){
					s3[index] = vec[index];
				}else{
					s3[index] = new BigNumber("0");
				}
			}
		vec = s3;
	}

	public BigNumber[] getComponents(){
		return vec;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder("<");
		for(int index = 0; index < vec.length; index++){
			sb.append(vec[index]);
			if(index!=vec.length-1){
				sb.append(",");
			}
		}
		sb.append(">");
		return sb.toString();
	}

	public static void main(String[] args){
		
		Vector v1 = new Vector("<12.0,32423,0.23423423,893945.2345,23452345,0>");
		Vector v2 = new Vector("<34234,5234535,.4523452345,23453245,3452345.4325>");
		System.out.println(v1.add(v2));
	}
}