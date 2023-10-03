import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.IOException;

public class Polynomial {
	private double[] coefficients;
	private int[] exponents;
	
	public Polynomial() {
        coefficients = new double[]{0};
		exponents = new int[]{0};
   	}

	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];
    	for (int i = 0; i < coefficients.length; i++) {
        	this.coefficients[i] = coefficients[i];
            this.exponents[i] = exponents[i];
    	}
	}

    public Polynomial(File file) throws IOException{
        Scanner scan = new Scanner(file);
        String equation = scan.nextLine();
        String[] splitPositive = equation.split("\\+");
        boolean firstNeg = equation.charAt(0) == '-';
        for (int i = 0; i < splitPositive.length; i++){
            String[] splitNegative = splitPositive[i].split("-");
            for (int j = 0; j < splitNegative.length; j++){
                String[] values = splitNegative[j].split("x");
                double coefficient = Double.parseDouble(values[0]);
                if (j > 0 || i==0 && firstNeg ){
                    coefficient *= -1;
                }
                int exponent = 0;
                if (values.length > 1){
                    exponent = Integer.parseInt(values[1]);
                }else if (splitNegative[j].indexOf("x") > -1){
                    exponent = 1;
                }
                Polynomial final = this.add(new Polynomial(new double[] {coefficient}, new int[] {exponent}));
                this.coefficients = final.coefficients;
                this.exponents = final.exponents;
            }
        }  
        scan.close();
    }

    public Polynomial add(Polynomial second) {
        if (this.exponents == null && second.exponents == null) {
            return new Polynomial();
        }
        int maxExponent = -1;
        if (this.exponents != null) {
            for (int i = 0; i < this.exponents.length; i++) {
                maxExponent = Math.max(maxExponent, this.exponents[i]);
            }
        }
        if (second.exponents != null) {
            for (int i = 0; i < second.exponents.length; i++) {
                maxExponent = Math.max(maxExponent, second.exponents[i]);
            }
        }
        double[] finalCoefficients = new double[maxExponent + 1];
        if (this.exponents != null) {
            for (int i = 0; i < this.exponents.length; i++) {
                finalCoefficients[this.exponents[i]] += this.coefficients[i];
            }
        }
        if (second.exponents != null) {
            for (int i = 0; i < second.exponents.length; i++) {
                finalCoefficients[second.exponents[i]] += second.coefficients[i];
            }
        }
        int term = 0;
        for (int i = 0; i < finalCoefficients.length; i++) {
            if (finalCoefficients[i] != 0) {
                term++;
            }
        }
        double[] newCoefficients = new double[term];
        int[] newExponents = new int[term];
        int dummy = 0;
        for (int i = 0; i < finalCoefficients.length; i++) {
            if (finalCoefficients[i] != 0) {
                newCoefficients[dummy] = finalCoefficients[i];
                newExponents[dummy] = i;
                dummy++;
            }
        }
        return new Polynomial(newCoefficients, newExponents);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += (coefficients[i] * Math.pow(x, exponents[i]));
        }
        return result;
    }

	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
    	}
    
    public Polynomial multiply(Polynomial second) {
        if (this.exponents == null || second.exponents == null) {
            return new Polynomial();
        }
        int maxExponent = -1;
        if (this.exponents != null) {
            for (int i = 0; i < this.exponents.length; i++) {
                for (int j = 0; j < second.exponents.length; j++) {
                    maxExponent = Math.max(maxExponent, this.exponents[i] + second.exponents[j]);
                }
            }
        }
        maxExponent += 1;
        double[] finalCoefficients = new double[maxExponent];
        for (int i = 0; i < maxExponent; i++) {
            finalCoefficients[i] = 0;
        }
        if (this.exponents != null) {
            for (int i = 0; i < this.exponents.length; i++) {
                for (int j = 0; j < second.exponents.length; j++) {
                    finalCoefficients[this.exponents[i] + second.exponents[j]] += this.coefficients[i] * second.coefficients[j];
                }
            }
        }
        int term = 0;
        for (int i = 0; i < maxExponent; i++) {
            if (finalCoefficients[i] != 0) {
                term += 1;
            }
        }
        double[] newCoefficients = new double[term];
        int[] newExponents = new int[term];
        int dummy = 0;
        for (int i = 0; i < maxExponent; i++) {
            if (finalCoefficients[i] != 0) {
                newCoefficients[dummy] = finalCoefficients[i];
                newExponents[dummy] = i;
                dummy++;
            }
        }
        return new Polynomial(newCoefficients, newExponents);
    }
    public void saveToFile (String filename) throws FileNotFoundException {
        String string = "";
        for (int i = 0; i < coefficients.length; i++){
            if (i != 0 && coefficients[i] > 0){
                string = string + "+";
        }
        string = string + coefficients[i];
        if (exponents[i] != 0){
            string = string + "x" + exponents[i];
        }
        }
        PrintStream printstring = new PrintStream(filename);
        printstring.println(string);
        printstring.close();
    }
}