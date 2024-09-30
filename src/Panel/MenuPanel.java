package Panel;
import Global.Global;

import Frame.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private JButton startButton;
    private JButton startAiButton;
    private GameFrame frame;
    private JTextField nick;

    public MenuPanel(GameFrame frame) {
        System.out.println("TU");
        this.frame = frame;

        nick = new JTextField("Podaj nick", 16);
        set_nickname();

        startButton = new JButton("Single Player");
        set_button();

        startAiButton = new JButton("Ai Player");
        setAi_button();

        //frame.setSize(Global.Global.SCREEN_WIDTH+100, Global.Global.SCREEN_HEIGHT);
        this.setPreferredSize(new Dimension(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
       // JPanel menuPanel = new JPanel();
        //menuPanel.setLayout(new BorderLayout());
        setLayout(null);



        add(startButton);
        add(startAiButton);
        add(nick);

    }

    void create_user(String nick){
        System.out.println("Nick "+nick);
    }

    void set_nickname(){
        nick.setFont(new Font("TimesRoman", Font.BOLD, 20));
        nick.setBackground(Color.BLACK);
        nick.setForeground(Color.WHITE);

        nick.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nick.getText().equals("Podaj nick")) {
                    nick.setText("");
                }
            }
        });


        int nickwidth = 200;
        int nicheight = 200;

        nick.setBounds(Global.SCREEN_WIDTH/2-nickwidth/2, Global.SCREEN_HEIGHT/2-nicheight/2, nickwidth, 50);

    }

    void set_button(){
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create_user(nick.getText());
                repaint();
                frame.game =  new GamePanel(frame);
                frame.switchToPanelGame(frame.getGamePanel());
            }
        });
        int buttwidth = 200;
        int buttonheight = 50;
        startButton.setBounds(Global.SCREEN_WIDTH/2- buttwidth/2, Global.SCREEN_HEIGHT/2-buttonheight/2, buttwidth, buttonheight);
    }
    void setAi_button(){
        startAiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create_user(nick.getText());
                repaint();
                frame.Aigame =  new GameAiPanel(frame);
                frame.switchToPanelGame(frame.getAiGamePanel());
            }
        });
        int buttwidth = 200;
        int buttonheight = 50;
        startAiButton.setBounds(Global.SCREEN_WIDTH/2- buttwidth/2 , Global.SCREEN_HEIGHT/2-buttonheight/2 + 100, buttwidth, buttonheight);
    }

    public String getNick(){
        return nick.getText();
    }


}
