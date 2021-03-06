package GameTools;

import javafx.util.Pair;

import javax.swing.*;

public class Square extends JButton {

    private int i;
    private int j;
    private String s;

    public Square(int i, int j) {
        this.i = i;
        this.j = j;
    }

    Square() {
        super();
    }


    public void setCoordinates(int i, int j) {
        this.i = i;
        this.j = j;
    }


    public Pair getCoordinates(){
        return new Pair<>(i, j);
    }


}
