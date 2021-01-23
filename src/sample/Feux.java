package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Feux extends Rectangle {

    int Fx;
    int Fy;
    int Htaille;
    int Wtaille;
    Color color;

    public void createFeux(int fx, int fy, int wt, int ht, Color color){
        Rectangle rectangle = new Rectangle();
        rectangle.setX(fx);
        rectangle.setY(fy);
        rectangle.setWidth(wt);
        rectangle.setHeight(ht);
        rectangle.setFill(color);
    }

    public Feux() {
    }

    public Feux(int fx, int fy, int htaille, int wtaille, Color color) {
        Fx = fx;
        Fy = fy;
        Htaille = htaille;
        Wtaille = wtaille;
        this.color = color;
    }

    public Feux(double width, double height, int fx, int fy, int htaille, int wtaille, Color color) {
        super(width, height);
        Fx = fx;
        Fy = fy;
        Htaille = htaille;
        Wtaille = wtaille;
        this.color = color;
    }

    public Feux(double width, double height, Paint fill, int fx, int fy, int htaille, int wtaille, Color color) {
        super(width, height, fill);
        Fx = fx;
        Fy = fy;
        Htaille = htaille;
        Wtaille = wtaille;
        this.color = color;
    }

    public Feux(double x, double y, double width, double height, int fx, int fy, int htaille, int wtaille, Color color) {
        super(x, y, width, height);
        Fx = fx;
        Fy = fy;
        Htaille = htaille;
        Wtaille = wtaille;
        this.color = color;
    }

    public int getFx() {
        return Fx;
    }

    public void setFx(int fx) {
        Fx = fx;
    }

    public int getF() {
        return Fy;
    }

    public void setF(int fy) {
        Fy = fy;
    }

    public int getHtaille() {
        return Htaille;
    }

    public void setHtaille(int htaille) {
        Htaille = htaille;
    }

    public int getWtaille() {
        return Wtaille;
    }

    public void setWtaille(int wtaille) {
        Wtaille = wtaille;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
