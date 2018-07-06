public class NBody {
	public static double radius;

	public static double readRadius(String path) {
		In in = new In(path);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();
		return secondItemInFile;
	}

	public static Planet[] readPlanets(String path) {
		Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif"); // must initialize the planet first
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Planet p3 = new Planet(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p4 = new Planet(3.0, 2.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet p5 = new Planet(5.0, 2.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Planet[] planets = new Planet[] {p1, p2, p3, p4, p5}; 
		In in = new In(path);
		int firstItemInFile = in.readInt();
		radius = in.readDouble();
		for (Planet element : planets) { 
			element.xxPos = in.readDouble();
			element.yyPos = in.readDouble();
			element.xxVel = in.readDouble();
			element.yyVel = in.readDouble();
			element.mass = in.readDouble();
			element.imgFileName = in.readString();
		}
		return planets;
	}

	public static void main(String[] args) {
	// must new a object, then can call its method, cannot use this in a static context.
		NBody self = new NBody(); 
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];
		//double radius = NBody.readRadius(fileName).secondItemInFile;
		double universeRadius = self.readRadius(fileName);
		Planet[] planets = self.readPlanets(fileName);
		StdDraw.setXscale(-self.radius, self.radius);
        StdDraw.setYscale(-self.radius, self.radius);
        StdDraw.enableDoubleBuffering(); // enable animation
		StdDraw.clear(); // set background image
		StdDraw.picture(0, 0, "./images/starfield.jpg");
		StdDraw.show();
		for (Planet planet : planets) {
			planet.draw();
		}

		// draw animation
		double time;
		for (time = 0; time <= T; time += dt) {
			double[] xForces = new double[5];
			double[] yForces = new double[5];
			int i = 0;
			for (Planet planet : planets) {
				xForces[i] = planet.calcNetForceExertedByX(planets);
				yForces[i] = planet.calcNetForceExertedByY(planets);
				planet.update(dt, xForces[i], yForces[i]);
				i ++;
			}
			StdDraw.clear(); 
			StdDraw.picture(0, 0, "./images/starfield.jpg");
			//StdDraw.show();
			for (Planet planet : planets) {
				planet.draw();
			}
			StdDraw.show(); // show at last, do not put it in the for loop, otherwise the animation will become flickery
			StdDraw.pause(10);
		}
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}



	}




}