package Algorithm;

import Global.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {

    public int x, y;
    public    int fCost;
    public    int gCost;
    public   int hCost;
    public    Node parent;
    public    boolean start;
    public   boolean goal;
    public    boolean solid;
    public   boolean open;
    public   boolean checked;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            int buttonSize = Global.UNIT_SIZE; // Ustawiamy rozmiar przycisku na wartość UNIT_SIZE
            setPreferredSize(new Dimension(45, 45));
            //setBackground(Color.WHITE);
            setBackground(Color.WHITE);

          //  setForeground(Color.BLACK);
            addActionListener(this);
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        setBackground(Color.ORANGE);
    }
    public void setAsStart(){
            setBackground(Color.blue);


            start = true;
    }
    public void setAsGoal(){
        setBackground(Color.YELLOW);


        goal = true;
    }
    public void setAsSolid(){
            setBackground(Color.black);
            setForeground(Color.black);
            solid = true;
    }
    public void setAsOpen(){
          open=true;
    }
    public void setAsChcek(){
        if(!start && !goal){
            setBackground(Color.ORANGE);
            setForeground(Color.black);
        }
        checked = true;
    }

    public void setAsPath(){
        setBackground(Color.GREEN);
        setForeground(Color.black);
    }

    public int getNodeX(){
            return x;
    }
    public int getNodeY(){
        return y;
    }



}
