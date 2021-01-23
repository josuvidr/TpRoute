package sample;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DessinVoiture extends Circle {

    public static Color couleur = Color.DARKRED;
    Color cj = couleur;
    Timeline animation;


    public DessinVoiture(double centerX, double centerY, double circle){
        super(centerX, centerY, circle);
        setFill(cj);
    }

    public void setAnimation(Timeline animation)
    {
        this.animation = animation;
    }
}
