public class Planet {
    public double xxPos;  //Its current x position
    public double yyPos;  //Its current y position
    public double xxVel;  //Its current velocity in the x direction
    public double yyVel;  //Its current velocity in the y direction
    public double mass;   //Its mass
    public String imgFileName;  //The name of the file(for example, jupiter.gif)
    public static double G = 6.67e-11;
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
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
        return Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos)+
                (yyPos-p.yyPos)*(yyPos-p.yyPos));
    }

    public double calcForceExertedBy(Planet p){
        return (G*this.mass*p.mass)/(this.calcDistance(p)*this.calcDistance(p));
    }

    public double calcForceExertedByX(Planet p) {
        return (this.calcForceExertedBy(p)*(p.xxPos-xxPos))/this.calcDistance(p);
    }
    public double calcForceExertedByY(Planet p) {
        return (this.calcForceExertedBy(p)*(p.yyPos-yyPos))/this.calcDistance(p);
    }

    public double calcNetForceExertedByX(Planet[] p){
        double sum = 0;
        for (int i = 0; i < p.length; i++) {
            if(p[i].equals(this)) continue;
            else{
                sum += this.calcForceExertedByX(p[i]);
            }
        }
        return sum;
    }
    public double calcNetForceExertedByY(Planet[] p){
        double sum = 0;
        for (int i = 0; i < p.length; i++) {
            if(p[i].equals(this)) continue;
            else{
                sum += this.calcForceExertedByY(p[i]);
            }
        }
        return sum;
    }

    public void update(double dt,double fX,double fY){
        double ax = fX/this.mass;
        double ay = fY/this.mass;
        this.xxVel = this.xxVel + dt*ax;
        this.yyVel = this.yyVel + dt*ay;
        this.xxPos = this.xxPos + dt*this.xxVel;
        this.yyPos = this.yyPos + dt*this.yyVel;
    }
    //draw this planet
    public void draw(){
        String filename = "images/"+this.imgFileName;
        StdDraw.picture(this.xxPos,this.yyPos, filename);
    }

}