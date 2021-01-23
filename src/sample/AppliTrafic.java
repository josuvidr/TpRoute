package sample;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    ArrayList<Feux> FeuxList = new ArrayList<>();
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

        Timeline littleCycle = new Timeline(new KeyFrame(Duration.millis(2000),
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

        // Création FEUX
        //1 horizontal 2 vertical
       for(Noeud noeud:temNoeuds){
           Feux f1 = new Feux((decalage + noeud.getX() * 80)-15,(decalage + noeud.getY() * 80)+10, 5,5,1);
           f1.setFill(Color.GREEN);
           Feux f2 = new Feux((decalage + noeud.getX() * 80)+15,(decalage + noeud.getY() * 80)+10, 5,5,2);
           f2.setFill(Color.RED);
           noeud.ListFeux.add(f1);
           noeud.ListFeux.add(f2);
            FeuxList.add(f1);
            FeuxList.add(f2);
            root.getChildren().add(f1);
            root.getChildren().add(f2);
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
        boolean avancer = false;
        Noeud  n = new Noeud(0,0);
        Noeud temp = new Noeud(0,0);
        ArrayList<Voiture> ltemp = new ArrayList<>();
        //Test
        System.out.println("TEST");
        //Appel pour créer les voitures
        createCar(tempoNoeuds);
        //Appel pour changer la couleur des feux
        SetFeux();

        // Gestion des feux
        System.out.println(voitures.size());
            for (int i = 0; i < voitures.size(); i++) {
                Voiture v = voitures.get(i);
                //n = v.prochainNoeud();
                if(v.vertical) {
                    if (GetVertVertical(v.prochainNoeud())) {//vertical
                        avancer = true;
                        n = v.prochainNoeud();
                    } else {
                        avancer = false;
                        n = temp;
                    }
                }else {
                    if(!(GetVertVertical(v.prochainNoeud()))){
                        avancer = true;
                        n = v.prochainNoeud();
                    }else
                    {
                        avancer = false;
                        n = temp;
                    }
                }
                temp = v.prochainNoeud();

                if (n != null) {//on n'est pas a la fin
                    DessinVoiture AffichageVoiture = dessinsVoitures.get(i);
                    Timeline timeline = new Timeline();
                    int xdest = decalage + n.getX() * 80;
                    int ydest = decalage + n.getY() * 80;

                    if(avancer) {
                        KeyFrame bougeVoiture = new KeyFrame(new Duration(tempo),
                                new KeyValue(AffichageVoiture.centerXProperty(), xdest),
                                new KeyValue(AffichageVoiture.centerYProperty(), ydest));
                        timeline.getKeyFrames().add(bougeVoiture);
                    }
                        timeline.play();
                        AffichageVoiture.setAnimation(timeline);

                }
                else//on est sur le dernier noeud
                {
                    //Supprime les véhicule de la MAP
                    System.out.println("ELSE");
                    root.getChildren().remove(dessinsVoitures.get(v.id));
                    ltemp.add(v);
                }
           }
            for(int i = 0; i<ltemp.size(); i++)
            {
                // Supprime les véhicules pas utilisés
               //voitures.remove(ltemp.get(i));
            }
       }
//Regarde si le déplacement est vertical ou horizontal
       public Boolean GetVertVertical(Noeud no){
           for(Noeud noeud:temNoeuds)
           {
               if(noeud.equals(no)){
                   for(int i =0; i<noeud.ListFeux.size();i++){
                       if(noeud.ListFeux.get(i).getFid() == 1)
                       {
                            if(noeud.ListFeux.get(i).getFill()==Color.GREEN) return true;
                       }
                   }
               }
           }
           return false;
       }


    public static void main(String[] args) { launch(args); }

    //Création des voitures et appel pour l'affichage des voitures
    public void createCar(ArrayList<Noeud> tempoNoeuds){

        for(int i = 0; i< tempoNoeuds.size();i ++){
            if(tempoNoeuds.get(i).getX() == 0 ){
                voitures.add(new Voiture(voiture, tempoNoeuds.get(i).getX(),tempoNoeuds.get(i).getY(),5,tempoNoeuds.get(i).getY(),true));
                DessCar(voitures.get(i));
                voiture++;
            }
            if (tempoNoeuds.get(i).getY()==5){
                voitures.add(new Voiture(voiture, tempoNoeuds.get(i).getX(),tempoNoeuds.get(i).getY(),tempoNoeuds.get(i).getX(),0,false));
                DessCar(voitures.get(i));
                voiture++;
            }
        }
    }
// Création de l'affichage des voitures
    public void DessCar(Voiture v){
            int cx =  decalage + v.getX() * 80;
            int cy = decalage + v.getY() * 80;
            DessinVoiture dv = new DessinVoiture(cx, cy, radius);
            root.getChildren().add(dv);
            dv.setRadius(5);
            dessinsVoitures.add(dv);
    }

//Change la couleur des feux
    public void SetFeux(){
        for(Rectangle r:FeuxList)
        {
             if(r.getFill() == Color.GREEN){
                r.setFill(Color.RED);
            }else r.setFill(Color.GREEN);

       }
   }
}

