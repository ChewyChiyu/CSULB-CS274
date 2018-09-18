import java.math.BigInteger;
public class BigNumber{

	private BigInteger bigNum;
	private int scaleFactor = 0;

	public BigNumber(String num){
		if(num.indexOf(".")!=num.lastIndexOf(".")){ num = "-1"; }
		if(num.indexOf(".")==-1){ bigNum = new BigInteger(num); }
		adjustScaleFactor(num);
		bigNum = new BigInteger(removeDecimal(num));
	}

	public BigNumber(String num, int scaleFactor){
		if(num.indexOf(".")!=num.lastIndexOf(".")){ num = "-1"; }
		if(num.indexOf(".")==-1){ bigNum = new BigInteger(num); }
		adjustScaleFactor(num);
		bigNum = new BigInteger(removeDecimal(num));
		this.scaleFactor = scaleFactor;
	}

	private void adjustScaleFactor(String num){
		if(num.indexOf(".")==-1){ return; }
		scaleFactor = Math.max(scaleFactor,num.length()-num.indexOf(".")-1);
	}

	private String removeDecimal(String num){
		if(num.indexOf(".")==-1){ return num; }
		return num.substring(0,num.indexOf(".")) + num.substring(num.indexOf(".")+1);
	}

	public String toString(){
		String num = bigNum.toString();
		if(scaleFactor > num.length()){
			for(int index = num.length(); index < scaleFactor; index++){
				num = "0"+num;
			}
		}
		return num.substring(0,num.length()-scaleFactor)+"."+num.substring(num.length()-scaleFactor);
	}

	public BigInteger getNum(){ return bigNum; }
	public int getScaleFactor(){ return scaleFactor; }
	

	public BigNumber flipSign(){
		return new BigNumber("-"+bigNum.toString(),getScaleFactor());
	}

	public BigNumber sub(BigNumber bn){
		if(bn.equals(this)){ return new BigNumber("0");}
		return add(bn.flipSign());
	}
	
	public BigNumber multiply(BigNumber bn){
		return new BigNumber(bigNum.multiply(bn.getNum()).toString(),bn.getScaleFactor()+scaleFactor);
	}

	public BigNumber divide(BigNumber bn){
		if(bn.getNum().toString()=="0"){ return new BigNumber("-1"); }
		String num = bigNum.divide(bn.getNum()).toString();
		String scale_const = "";

		for(int index = 0; index < (scaleFactor-bn.getScaleFactor())*-1; index++){
			scale_const += "0";
		}
		return new BigNumber(num+scale_const,((scaleFactor-bn.getScaleFactor()>0)?scaleFactor-bn.getScaleFactor():0));
	}

	public BigNumber add(BigNumber bn){
		int scaleIncrement = Math.abs(bn.getScaleFactor()-scaleFactor);
		int postScaleFactor = bn.getScaleFactor();
		BigInteger scaleFalse = bn.getNum();
		BigInteger scaleTrue = bigNum;
		if(bn.getScaleFactor()<scaleFactor){ // scale this
			scaleFalse = bigNum;
			scaleTrue = bn.getNum();
			postScaleFactor = getScaleFactor();
		}
		String scale_const = "";
		for(int index = 0; index < scaleIncrement; index++){
			scale_const+="0";
		}
		scaleTrue = new BigInteger(scaleTrue.toString()+scale_const);
		BigInteger sum = scaleTrue.add(scaleFalse);
		String num = sum.toString();
		return new BigNumber(num,postScaleFactor);
	}

	public static void main(String[] args){
		BigNumber bn = new BigNumber("0.0002");
		BigNumber bn2 = new BigNumber(".000000002");
		System.out.println(bn);
		System.out.println(bn2);
		System.out.println(bn2.divide(bn));
	}
}