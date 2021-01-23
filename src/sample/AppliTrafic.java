package sample;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class AppliTrafic extends Application  {

    ArrayList<Voiture> voitures;
    ArrayList<Noeud> tempoNoeuds= new ArrayList<>();
    List<DessinVoiture> dessinsVoitures;
    List<Noeud> noeuds = ReseauRoutier.getNoeuds();
    ArrayList<Noeud> temNoeuds = new ArrayList<>();
    ArrayList<Rectangle> FeuxList = new ArrayList<>();
    static int voiture = 0;
    int decalage = 80 / 2;
    int radius = decalage / 3;
    Scene scene;
    Group root;

    long tempo = 1000;

    public void start(Stage primaryStage) {
        voitures = new ArrayList<>();
        dessinsVoitures= new ArrayList<>();
        construirePlateauJeu(primaryStage);
    }

    void construirePlateauJeu(Stage primaryStage) {
        root = new Group();
        scene = new Scene(root, 500, 500, Color.LIGHTGRAY);

        dessinEnvironnement(root);
        primaryStage.setTitle("********************TRAFIC********************");
        primaryStage.setScene(scene);
        primaryStage.show();

        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(1000),
                event-> {
                    try {
                        animDeplacement();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }));
        littleCycle.setCycleCount(Timeline.INDEFINITE);
        littleCycle.play();
    }

    void dessinEnvironnement(Group root) {
        ReseauRoutier.creerReseau();



        // Création CARREFOURS

        for (Noeud noeud:noeuds)
        {
            int cx = decalage + noeud.getX() * 80;
            int cy = decalage + noeud.getY() * 80;
            Circle c = new Circle(cx, cy, radius);
            c.setFill(Color.BLACK);
            c.setOpacity(0.3);
            root.getChildren().add(c);
            c.setSmooth(true);
        }
        // Création ROUTES
        for (Noeud noeud:noeuds)
        {
            int ox = decalage + noeud.getX() * 80;
            int oy = decalage + noeud.getY() * 80;
            for (Arc arc:noeud.getArcSortants())
            {
                Noeud dest=arc.getEnd();
                int dx = decalage + dest.getX() * 80;
                int dy = decalage + dest.getY() * 80;
                Line l = new Line(ox, oy, dx, dy);
                l.setStrokeWidth(15);
                l.setStroke(Color.GRAY);
                root.getChildren().add(l);
            }
        }

        for(Noeud noeud : noeuds){
            if(!(noeud.x == 0 || noeud.y == 5 || noeud.x == 5|| noeud.y == 0)){
                temNoeuds.add(noeud);
            }
        }

        // Création FEUX ROUGE
       /* for(Noeud noeud:temNoeuds){
            Feux feux = new Feux();
            feux.setFx((decalage + noeud.getX() * 80)-15);
            feux.setF((decalage + noeud.getY() * 80)+10);
            feux.setWtaille(5);
            feux.setHtaille(5);
            feux.setColor(Color.RED);
            FeuxList.add(feux);
            root.getChildren().add(feux);
        }

        // Création FEUX VERT
        for(Noeud noeud:temNoeuds){
            Feux feux = new Feux();
            feux.setFx((decalage + noeud.getX() * 80)+15);
            feux.setF((decalage + noeud.getY() * 80)+10);
            feux.setWtaille(5);
            feux.setHtaille(5);
            feux.setColor(Color.GREEN);
            FeuxList.add(feux);
            root.getChildren().add(feux);
        }*/
        // Création FEUX
       for(Noeud noeud:temNoeuds){
            Rectangle rectangle = new Rectangle();
            rectangle.setX((decalage + noeud.getX() * 80)-15);
            rectangle.setY((decalage + noeud.getY() * 80)+10);
            rectangle.setWidth(5);
            rectangle.setHeight(5);
            rectangle.setFill(Color.GREEN);
            noeud.ListFeux.add(rectangle);
            root.getChildren().add(rectangle);
        }
        for(Noeud noeud:temNoeuds){
            Rectangle rectangle = new Rectangle();
            rectangle.setX((decalage + noeud.getX() * 80)+15);
            rectangle.setY((decalage + noeud.getY() * 80)+10);
            rectangle.setWidth(5);
            rectangle.setHeight(5);
            rectangle.setFill(Color.RED);
            noeud.ListFeux.add(rectangle);
            root.getChildren().add(rectangle);
        }

        //Création VOITURES
        for (Noeud noeud : noeuds) {
            if (noeud.x == 0 || noeud.y == 5) {
                tempoNoeuds.add(new Noeud(noeud.x, noeud.y));
            }
        }

        //createCar(tempoNoeuds);
    }


    private void animDeplacement() throws FileNotFoundException {
        Noeud n = null;
        ArrayList<Voiture> ltemp = new ArrayList<>();
        System.out.println("TEST");
        createCar(tempoNoeuds);
        SetFeux();
        System.out.println(voitures.size());
            for (int i = 0; i < voitures.size(); i++) {
                Voiture v = voitures.get(i);
                if(GetVert(v.prochainNoeud())){
                    n = v.prochainNoeud();
                } else n = null;

                if (n != null) {//on n'est pas a la fin
                    DessinVoiture AffichageVoiture = dessinsVoitures.get(i);
                    Timeline timeline = new Timeline();
                    int xdest = decalage + n.getX() * 80;
                    int ydest = decalage + n.getY() * 80;
                    v.pause = true;
                    KeyFrame bougeVoiture = new KeyFrame(new Duration(tempo),
                            new KeyValue(AffichageVoiture.centerXProperty(), xdest),
                            new KeyValue(AffichageVoiture.centerYProperty(), ydest));
                    timeline.getKeyFrames().add(bougeVoiture);
                    timeline.play();
                    AffichageVoiture.setAnimation(timeline);
                    System.out.println(voitures.size());
                }
                else//on est sur le dernier noeud
                {
                    System.out.println("ELSE");
                    root.getChildren().remove(dessinsVoitures.get(v.id));
                    ltemp.add(v);
                }
           }
            for(int i = 0; i<ltemp.size(); i++)
            {
               //voitures.remove(ltemp.get(i));
            }

       }

       public Boolean GetVert(Noeud no){
           for(Noeud noeud:temNoeuds)
           {
               if(noeud.equals(no)){
                   for(Rectangle r:noeud.ListFeux){
                       if(r.getFill() == Color.GREEN){
                            return true;
                       }
                   }
               }
           }
           return false;
              /* System.out.println("r : " + r.getX());
               System.out.println("noeud : " + noeud.getX());
               if((r.getX() == noeud.getX()+15) || (r.getX() == noeud.getX()-15) && (r.getY() == noeud.getY()+10) || (r.getY() == noeud.getY()-10)){
                    System.out.println("OK2");
               }
           }*/
       }

    public static void main(String[] args) { launch(args); }

    public void createCar(ArrayList<Noeud> tempoNoeuds) throws FileNotFoundException {

        for(int i = 0; i< tempoNoeuds.size();i ++){
            if(tempoNoeuds.get(i).getX() == 0 ){
                voitures.add(new Voiture(voiture, tempoNoeuds.get(i).getX(),tempoNoeuds.get(i).getY(),5,tempoNoeuds.get(i).getY()));
                DessCar(voitures.get(i));
                voiture++;
            }
            if (tempoNoeuds.get(i).getY()==5){
                voitures.add(new Voiture(voiture, tempoNoeuds.get(i).getX(),tempoNoeuds.get(i).getY(),tempoNoeuds.get(i).getX(),0));
                DessCar(voitures.get(i));
                voiture++;
            }
        }
    }



    public void DessCar(Voiture v) throws FileNotFoundException {
            int cx =  decalage + v.getX() * 80;
            int cy = decalage + v.getY() * 80;
            DessinVoiture dv = new DessinVoiture(cx, cy, radius);
            root.getChildren().add(dv);
            dv.setRadius(5);
            dessinsVoitures.add(dv);
    }

    /*public void SetFeux(Noeud noeud){
        for(Feux feux:FeuxList){
            if(feux.Fx == ((decalage + noeud.getX() * 80)+15) && feux.Fy == (((decalage + noeud.getY() * 80)+10))){
                feux.setColor(Color.RED);
            }else feux.setColor(Color.GREEN);
        }
    }*/

    public void SetFeux(){
        for(Rectangle r:FeuxList)
        {
             if(r.getFill() == Color.GREEN){
                r.setFill(Color.RED);
            }else r.setFill(Color.GREEN);

       }
   }
}

