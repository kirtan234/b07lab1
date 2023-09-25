public class Polynomial {
	private double[] coefficients;
	
	public Polynomial() {
        	coefficients = new double[]{0};
   	}

	public Polynomial(double[] coefficients) {
		this.coefficients = new double[coefficients.length];
    		for (int i = 0; i < coefficients.length; i++) {
        		this.coefficients[i] = coefficients[i];
    		}
	}

	public Polynomial add(Polynomial second) {
        	int maxLength = Math.max(coefficients.length, second.coefficients.length);
        	double[] result = new double[maxLength];
		for (int i = 0; i < coefficients.length; i++) {
            		result[i] += coefficients[i];
        	}
		for (int i = 0; i < second.coefficients.length; i++) {
            		result[i] += second.coefficients[i];
        	}
		return new Polynomial(result);
    	}

	public double evaluate(double x) {
        	double result = 0;
        	for (int i = 0; i < coefficients.length; i++) {
			result += (coefficients[i] * Math.pow(x, i));
        	}
        	return result;
    	}

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
    	}
}