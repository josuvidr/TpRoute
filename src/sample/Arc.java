package sample;

import java.util.List;

public class Arc
{
    private Noeud start;
    private Noeud end;
    List<Voiture> cars;
    String name;

    public Arc() {   }

    public Arc(Noeud start, Noeud end) {
        super();
        this.start = start;
        this.end = end;
        this.name = ""+start.id+"-"+end.id;
    }

    public Noeud getStart() { return start; }
    public Noeud getEnd() { return end; }

    public void setStart(Noeud start) { this.start = start; }
    public void setEnd(Noeud end) { this.end = end; }


}