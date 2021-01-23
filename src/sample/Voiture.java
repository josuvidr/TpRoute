package sample;

import java.util.ArrayList;
import java.util.List;

public class Voiture
{

    public int id;
    int x;
    int y;
    /**liste des noeuds du trajet prevu*/
    public List<Noeud> trajet;
    public List<Noeud> routeRestante;//liste des noeuds restants
    public Noeud origine;
    public Noeud destination;
    public boolean vertical;


    Voiture(){}

    Voiture(int id){
        this.id = id;
        trajet = new ArrayList<>();
        routeRestante = new ArrayList<>();
    }
    public Voiture(Voiture v) {
        this(v.id);
        this.origine = v.origine;
        x = this.origine.x;
        y = this.origine.y;
        this.destination = v.destination;
        calculerRoute();
        routeRestante.addAll(trajet);
    }
    public Voiture(int no, Noeud origine, Noeud destination) {
        this(no);
        this.origine = origine;
        x = origine.x;
        y = origine.y;
        this.destination = destination;
        calculerRoute();
        routeRestante.addAll(trajet);
    }

    public Voiture(int no, int xo, int yo, int xd, int yd, boolean vertical) {
        this(no);
        this.origine = ReseauRoutier.getNoeud(xo, yo);
        x = origine.x;
        y = origine.y;
        this.destination = ReseauRoutier.getNoeud(xd,yd);
        calculerRoute();
        routeRestante.addAll(trajet);
        this.vertical = vertical;
    }

    public void calculerRoute()
    {
        boolean enLigne;
        enLigne = origine.getY() == destination.getY();

        if (enLigne)
        {
            int ligne = origine.getY();
            int sens = destination.getX() - origine.getX();
            if(sens!=0) sens = sens/Math.abs(sens);
            for(int i=origine.getX(); i!=destination.getX(); i+=sens)
            {
                Noeud n = ReseauRoutier.getNoeud(i, ligne);
                trajet.add(n);
            }
        }
        else
        {
            int colonne = origine.getX();
            int sens = destination.getY() - origine.getY();
            if(sens!=0) sens = sens/Math.abs(sens);
            for(int i=origine.getY(); i!=destination.getY(); i+=sens)
            {
                Noeud n = ReseauRoutier.getNoeud(colonne, i);
                trajet.add(n);
            }
        }
        trajet.add(destination);
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Noeud prochainNoeud()
    {
        Noeud suivant = null;

        if(!routeRestante.isEmpty())
        {
            suivant = routeRestante.get(0);
            routeRestante.remove(0);
        }
        return suivant;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("voiture ").append(id);
        sb.append(". chemin = ");
        for(Noeud n:trajet) sb.append(n).append("-");
        return sb.toString();
    }

    public int getX() { return x; }
    public int getY() { return y; }
}