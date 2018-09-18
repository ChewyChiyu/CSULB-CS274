import java.math.BigDecimal;
public class Vector{

	private BigDecimal[] vec;

	public Vector(String[] vec){
		this.vec = new BigDecimal[vec.length];
		for(int index = 0; index < vec.length; index++){
			this.vec[index] = new BigDecimal(vec[index]);
		}
	}

	public Vector(String vecStr){
		String[] vecArr = vecStr.split(",");
		for(String s : vecArr){
			System.out.print(s + " ");
		}
		System.out.println();
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
			s3[index]= vec[index].subtract(v2.getComponents()[index]).toString();
		}
		return new Vector(s3);
	}

	public Vector multiply(BigDecimal bn){
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
		BigDecimal[] s3 = new BigDecimal[v2.getComponents().length];
			for(int index = 0; index < s3.length; index++){
				if(index < vec.length){
					s3[index] = vec[index];
				}else{
					s3[index] = new BigDecimal("0");
				}
			}
		vec = s3;
	}

	public BigDecimal[] getComponents(){
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
		// Vector v1 = new Vector(new String[]{"12",".232323","23929838"});
		// Vector v2 = new Vector(new String[]{"2234592340528394543"});
		// System.out.println(v1.add(v2));
		Vector v3 = new Vector("<12.0,32423,0.23423423>");
	}
}