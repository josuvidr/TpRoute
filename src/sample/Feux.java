package sample;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Feux extends Rectangle {

    public int Fid;

    public Feux(int fid) {
        Fid = fid;
    }

    public Feux(double width, double height, int fid) {
        super(width, height);
        Fid = fid;
    }

    public Feux(double width, double height, Paint fill, int fid) {
        super(width, height, fill);
        Fid = fid;
    }

    public Feux(double x, double y, double width, double height, int fid) {
        super(x, y, width, height);
        Fid = fid;
    }

    public int getFid() {
        return Fid;
    }

    public void setFid(int fid) {
        Fid = fid;
    }
}
