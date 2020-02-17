/**
 * @author Simon O'Shea
 */
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class Integrate {
	
public static double functionValue(double A, double B, double C, double D, double E, double F, double x) {
		
		double value = 0;
		
		value = (A * Math.pow(x, 5)) + (B * Math.pow(x, 4)) + (C * Math.pow(x, 3)) + 
					(D * (Math.pow(x, 2)) + (E * x) + F); 
		
		return value;
		
}
	

public static double antiDerivative(double A, double B, double C, double D, double E, double F, double a, double b) {
	
	A = A / 6.0; B = B / 5.0; C = C / 4.0; D = D / 3.0; E = E / 2.0;
	
	double first = (A * Math.pow(a, 6)) + (B * Math.pow(a, 5)) + (C * Math.pow(a, 4)) + (D * Math.pow(a, 3)) +
					(E * Math.pow(a, 2)) + (F * a); 
	
	double last = (A * Math.pow(b, 6)) + (B * Math.pow(b, 5)) + (C * Math.pow(b, 4)) + (D * Math.pow(b, 3)) +
			(E * Math.pow(b, 2)) + (F * b);  
	

	
	return last - first;
	
}


public static double midpointRule(double A, double B, double C, double D, double E, double F, double startInterval, double endInterval, double numInterval) {
		
		double sum = 0, temp;
		double width = (endInterval - startInterval) / numInterval;
		double start = startInterval + (width / 2.0);
		
		for(double i = start; i <= endInterval; i = i + width) {
				
				temp = functionValue(A, B, C, D, E, F, i);
				sum += temp;
				
		}
		
	return sum * width;
		
}
	

public static double trapezoidRule(double A, double B, double C, double D, double E, double F, double startInterval, double endInterval, double numInterval) {
	
	double sum = 0, b1, b2;
	double width = (endInterval - startInterval) / numInterval;	

	for(double i = startInterval; i < endInterval; i = i + width) {
		
		
		
		b1 = functionValue(A, B, C, D, E, F, i);
		b2 = functionValue(A, B, C, D, E, F, (i + width));
		
		
		sum += (width * ((b1 + b2) / 2.0));
		
	}
	
	return sum;
	
}


public static double simpsonsRule(double A, double B, double C, double D, double E, double F, double startInterval, double endInterval, double numInterval) {
	
	double mid = midpointRule(A, B, C, D, E, F, startInterval, endInterval, numInterval);
	double trap = trapezoidRule(A, B, C, D, E, F, startInterval, endInterval, numInterval); 
	
	return ((2.0 * mid) + trap) / 3.0;
	
}

		
public static String percentError(double actual, double estimate) {
	
	double error = (estimate - actual) / actual;
	
	if(error == 0)
		return "none";
	
	return String.format("%.9f %%", error);

}


public static void main(String[] args) {
	Scanner input = new Scanner(System.in);
	DecimalFormat formatter = new DecimalFormat("#,###.00");
	
	try {
		System.out.println("Please enter the \"a\" bound of integration: ");
			double a = input.nextDouble();
			
		
		System.out.println("Please enter the \"b\" bound of integration: ");
			double b = input.nextDouble();
		
			if(b < a) {
				System.out.println("Error: \"a\" must be less than \"b\"");	
				System.exit(0);
			}
		
		System.out.println("Please enter the number of sub-intervals: ");
			int n = input.nextInt();
		
			if(n <= 0) {
				System.out.println("The amount of sub-intervals must be greater than or equal to 0.");
				System.exit(0);
			}
		
		System.out.println("Please enter 6 values: ");
			double A = input.nextDouble();
			double B = input.nextDouble();
			double C = input.nextDouble();
			double D = input.nextDouble();
			double E = input.nextDouble();
			double F = input.nextDouble();
			input.close();
		
		System.out.printf("%n%n%nFunction being integrated: %.2fx^5 + %.2fx^4 + %.2fx^3 + %.2fx^2 + %.2fx + %.2f%n%n", A, B, C, D, E, F);
			
			Double actual = antiDerivative(A, B, C, D, E, F, a, b);
			double midpoint = midpointRule(A, B, C, D, E, F, a, b, n);
			double trapezoid = trapezoidRule(A, B, C, D, E, F, a, b, n);
			double simpsons = simpsonsRule(A, B, C, D, E, F, a, b, n);
			
		
		System.out.printf("-> The exact value (found by using the Fundamental Theorem of Calculus): %s   // error: %s%n%n", 
																							formatter.format(actual), percentError(actual, actual));
		
		System.out.printf("-> The estimated value (found by using the Midpoint Reimann Sum method): %s   // error: %s%n%n", 
																							formatter.format(midpoint), percentError(actual, midpoint));
		
		System.out.printf("-> The estimated value (found by using the Trapezoidal Reimann Sum method): %s   // error: %s%n%n", 
																							formatter.format(trapezoid), percentError(actual, trapezoid));
		
		System.out.printf("-> The estimated value (found by using the Simpsons Reimann Sum method): %s   // error: %s%n%n", 
																							formatter.format(simpsons), percentError(actual, simpsons));
	}

	catch(InputMismatchException x) {
		
		System.out.println("Error: Invalid input type");
		System.exit(0);
			
	}
	
	catch(NoSuchElementException y) {
		
		System.out.println("Error: No input");
		System.exit(0);
		
	}
	
}
}
