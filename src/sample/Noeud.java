package sample;

import java.util.ArrayList;
import java.util.List;


public class Noeud {

    int x;
    int y;
    List<Noeud> noeudsAccessibles;
    List<Arc> arcEntrants;
    List<Arc> arcSortants;
    boolean supp = false;
    double id;
    ArrayList<Feux> ListFeux = new ArrayList<>();

    Noeud(int _x, int _y)
    {
        x = _x;
        y = _y;
        double yf = y/((Math.floor(Math.log10(y))+1)*10);
        id = (double)x+yf;
        noeudsAccessibles = new ArrayList<>();
        arcEntrants = new ArrayList<>();
        arcSortants = new ArrayList<>();
    }


    public String toString()
    {
        return "noeud (" + x +"," + y + ")" ;
    }


    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noeud autre = (Noeud)o;
        return id==autre.id;
    }

    public List<Noeud> getVoisins() { return noeudsAccessibles; }
    public int getX() { return x; }
    public int getY() { return y; }
    public List<Arc> getArcSortants() { return arcSortants; }
    public double getId() { return id; }

}