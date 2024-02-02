public class NBody {
    public static double readRadius(String imgFileName){
        In in = new In(imgFileName);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        return secondItemInFile;
    }
    public static Planet[] readPlanets(String imgFileName){
        In in = new In(imgFileName);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        Planet[] planets = new Planet[firstItemInFile];

        for (int i = 0; i < firstItemInFile; i++) {
            planets[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),
                    in.readDouble(),in.readDouble(),in.readString());
        }
        return planets;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = NBody.readRadius(filename);
        //set backgroud picture
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        //draw all planets
        Planet[] planets = NBody.readPlanets(filename);
        for (int i = 0; i < 5; i++) {
            planets[i].draw();
        }
        StdDraw.show();
        //make pictures run
        StdDraw.enableDoubleBuffering();
        double time = 0;
        double[] xForces = new double[5];
        double[] yForces = new double[5];
        for (; time <= T; time += dt) {
            //calc net force
            for (int i = 0; i < 5; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            //update all planet
            for (int i = 0; i < 5; i++) {
                planets[i].update(dt,xForces[i],yForces[i]);
            }
            StdDraw.clear();
            //Draw the background image.
            StdDraw.picture(0, 0, "images/starfield.jpg");
            StdDraw.show();
            //Draw all of the planets.
            for (int i = 0; i < 5; i++) {
                planets[i].draw();
            }
            //Show the offscreen buffer (see the show method of StdDraw).
            StdDraw.show();
            //Pause the animation for 10 milliseconds (see the pause method of StdDraw).
            StdDraw.pause(10);
        }
        //print universe
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
