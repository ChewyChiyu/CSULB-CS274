public class Function{

	private String func;

	public Function(String func){
		this.func = func;
	}

	public double inputPositive(double d){
		return Calculator.calculator.calculate(func.replace("x","("+d+")"));
	}	
	public double inputNegative(double d){
		return Calculator.calculator.calculate(func.replace("x","(0-"+d+")"));
	}
}