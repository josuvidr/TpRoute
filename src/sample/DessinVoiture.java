package sample;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DessinVoiture extends Circle {
    /**vrai si le jeton est actuellement selectionne*/
    boolean selected;
    /**position du jeton dans la grille (null si non posé)*/
    private Point position;
    /**ancienne position du jeton dans la grille (null si non posé)*/
    private Point anciennePosition;
    /**couleur pour voiture*/
    public static Color couleur = Color.LIME;
    /**couleur courante du jeton*/
    Color cj = couleur;
    Timeline animation;



    public DessinVoiture(double centerX, double centerY, double circle) throws FileNotFoundException {
        //
        super(centerX, centerY, circle);
        selected = false;
        setFill(cj);
    }



    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point _position) {
        this.position = _position;
    }

    /**
     * @return the anciennePosition
     */
    public Point getAnciennePosition() {
        return anciennePosition;
    }

    public void moveToPosition(Point _point)
    {
        this.anciennePosition.setLocation(this.position);
        this.position.setLocation(_point);
    }

    /**
     * @param anciennePosition the anciennePosition to set
     */
    public void setAnciennePosition(Point anciennePosition) {
        this.anciennePosition = anciennePosition;
    }

    public String toString()
    {
        return "("+position.x+","+position.y+") avant en "+ "("+anciennePosition.x+","+anciennePosition.y+") ";
    }

    public Timeline getAnimation()
    {
        return animation;
    }

    public void setAnimation(Timeline animation)
    {
        this.animation = animation;
    }
}
