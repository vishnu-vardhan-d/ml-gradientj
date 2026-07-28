package io.mlgradientj;

/**
 * 
 * Linear regression from scratch: gradient descent in plain Java

	Learns one number from finished trips -- the share of a lane's return
	diesel that earns nothing because the lorry came home empty. 
	
	No ML library; the arithmetic is the code.
	
	  model    predict = planned - (weight x diesel)
	  method   predict, measure the miss, nudge the weight, repeat
	  stop     when a full pass moves the weight < 0.1 of a point
	
	Derived on paper first, then written by hand. Converges on the four	sample trips in ~37 passes.
	
	Notes for the reader:
	- STEP_SIZE is why the weight settles on the trips' average rather than swinging to whatever the last trip said.
	- The miss is in rupees but the weight is a fraction; dividing by diesel is what converts between them.
	- Math.abs on the pass movement matters -- a large downward correction would otherwise read as "settled".
 * 
 */
public class LinearRegression {

	static final double STEP_SIZE = 0.05;
	static final double SETTLED = 0.001;
	static final int MAX_PASSES = 40;

	static final Trip[] trips = { 
			new Trip(-2376, 17270, -2376), 
			new Trip(917, 17765, -16848),
			new Trip(4103, 16940, -4367), 
			new Trip(4309, 16610, 987), 
			};

	static class Trip {
		final double planned;
		final double actual;
		final double diesel;

		Trip(double planned, double diesel, double actual) {
			this.planned = planned;
			this.diesel = diesel;
			this.actual = actual;
		}

	}

	public static void main(String[] args) {

		double weight = 0.0;
		

		for (int pass = 1; pass < MAX_PASSES; pass++) {
			
			double weightAtPassStart = weight;  
			double totalError = 0.0;
			for (Trip trip : trips) {
				double predict = trip.planned - (weight * trip.diesel);
				double miss = predict - trip.actual;
				double gap = miss / trip.diesel;
				double error = STEP_SIZE * gap;

				weight += error;
				System.out.println("weight: " + weight);
				
			}
			
			System.out.println("weight: " + weight);
			System.out.println("weightAtPassStart: " + weightAtPassStart);
			double moved = Math.abs(weight - weightAtPassStart);
			
			System.out.println("moved: " + moved);

		    System.out.printf("Pass %d: weight=%.2f%%, moved=%.2f%n",
		                      pass, weight * 100, moved * 100);
			if (moved < SETTLED) {
				break;
			}

			System.out.println("Pass " + pass + ": weight=" + weight + ", totalError=" + totalError);

			

		}

	}

}
