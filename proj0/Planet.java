import java.lang.*;

public class Planet {
	public double xxPos, yyPos, xxVel, yyVel, mass;
	public String imgFileName;
	public static final double g = 6.67e-11; 

	public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
		this.xxPos = xxPos;
		this.yyPos = yyPos;
		this.xxVel = xxVel;
		this.yyVel = yyVel;
		this.mass = mass;
		this.imgFileName = imgFileName;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p) {
		double distanceSquare, dx, dy;
		dx = xxPos - p.xxPos;
		dy = yyPos - p.yyPos;
		distanceSquare = dx * dx + dy * dy;
		return Math.sqrt(distanceSquare);
	}

	public double calcForceExertedBy(Planet p) {
		return g * mass * p.mass / (calcDistance(p) * calcDistance(p));
	}


	public double calcForceExertedByX(Planet p) {
		return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
	}

	public double calcForceExertedByY(Planet p) {
		return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] allPlanets) {
		double netForceX = .0;
		for (Planet planet : allPlanets) {
			if (!this.equals(planet))	netForceX += calcForceExertedByX(planet);
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets) {
		double netForceY = .0;
		for (Planet planet : allPlanets) {
			if (!this.equals(planet))	netForceY += calcForceExertedByY(planet);
		}
		return netForceY;
	}

	public void update(double dt, double fx, double fy) {
		double netX, netY;
		netX = fx / mass;
		netY = fy / mass;
		xxVel = xxVel + netX * dt;
		yyVel = yyVel + netY * dt;
		xxPos = xxPos + xxVel * dt;
		yyPos = yyPos + yyVel * dt;
	}

	public void draw() {
		//StdDraw.clear();
		StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
		//StdDraw.show();
        //StdDraw.pause(20000);
	}






}