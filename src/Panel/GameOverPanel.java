package Panel;

import Global.Global;

import Frame.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverPanel extends JPanel  {


    private JButton MenuButton;
    private GameFrame frame;
    public int beastScore;
    public int score;
    public String nick;
    public String beastNick;


    public GameOverPanel(GameFrame frame) {

        this.frame = frame;




        MenuButton = new JButton("Back to Menu");
        set_MenuButton();

        //frame.setSize(Global.Global.SCREEN_WIDTH+100, Global.Global.SCREEN_HEIGHT);
        this.setPreferredSize(new Dimension(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        // JPanel menuPanel = new JPanel();
        //menuPanel.setLayout(new BorderLayout());
        setLayout(null);


        add(MenuButton);


    }
    void set_MenuButton(){

        MenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                repaint();
                frame.switchToMenuPanel();
            }
        });
        int buttwidth = 200;
        int buttonheight = 50;
        MenuButton.setBounds(Global.SCREEN_WIDTH/2- buttwidth/2, Global.SCREEN_HEIGHT/2-buttonheight/2, buttwidth, buttonheight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font gameOverFont = new Font("TimesRoman", Font.BOLD, 75);
        g.setFont(gameOverFont);
        FontMetrics fmGameOver = g.getFontMetrics(gameOverFont);
        g.setColor(Color.RED);
        g.drawString("Game Over", (Global.SCREEN_WIDTH - fmGameOver.stringWidth("Game Over")) / 2, (Global.SCREEN_HEIGHT) / 2 - 200);


        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));

        FontMetrics fm2 = g.getFontMetrics(g.getFont());
        g.drawString("Your Score " + score, (Global.SCREEN_WIDTH - fm2.stringWidth("Your Score" + score)) / 2,(Global.SCREEN_WIDTH - g.getFont().getSize())/2+50);
        g.drawString("Beast Score " + beastScore, (Global.SCREEN_WIDTH - fm2.stringWidth("Beast Score" + beastScore)) / 2,(Global.SCREEN_WIDTH - g.getFont().getSize())/2+100);
    }


    public void set_beastScore(int sc){
        beastScore = sc;
    }
    public void set_score(int sc){
        score =sc;
    }
    public void set_nick(String n){
        nick =n;
    }
    public void set_beastNick(String n){
        beastNick =n;
    }



}

