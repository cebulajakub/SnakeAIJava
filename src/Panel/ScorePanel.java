package Panel;
import Global.Global;


import javax.swing.*;
import java.awt.*;


public class ScorePanel extends JPanel {

    static final int SCREEN_WIDTH = Global.SCREEN_WIDTH;
    static final int SCREEN_HEIGHT = Global.SCREEN_HEIGHT;
    static final int UNIT_SIZE = Global.UNIT_SIZE;
    static final int GAME_UNITS = Global.GAME_UNITS;
    static final int DELAY = Global.DELAY;
    int score;
    public ScorePanel(int height_screen, int score) {

        this.score = score;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, height_screen));

        this.setBackground(Color.BLACK);


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        FontMetrics fm2 = g.getFontMetrics(g.getFont());
        g.drawString("Score " + score, (Global.SCREEN_WIDTH - fm2.stringWidth("Score" + score)) / 2, g.getFont().getSize());
    }
    public void updateScore(int newScore) {
        score = newScore;
        repaint(); //  aktualizacjia wyniku
    }
    public int getScore(){
        return score;
    }

}
