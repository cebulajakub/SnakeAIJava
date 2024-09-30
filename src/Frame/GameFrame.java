package Frame;

import Interface.*;
import Panel.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import Global.Global;
public class GameFrame extends JFrame {

    private MenuPanel menu ;

    public IterfaceGamePanel game;
    public IterfaceGamePanel Aigame ;
    String FILE_PATH = "highscore.txt";
    private ScorePanel score ;
    private GameOverPanel over = new GameOverPanel(this); ;
    private Timer timer;
    String endNick;
    int endScore;

    public GameFrame() {
        timer = new Timer(Global.DELAY, e -> updateGame());
        Menu();

    }

    private void Menu() {
        getContentPane().removeAll();
        over = new GameOverPanel(this);
        menu = new MenuPanel(this);
     //   game = new GamePanel(this);
     //   Aigame = new GameAiPanel(this);
        getContentPane().add(menu);
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void updateGame() {
        if (game != null) {
            game.updateGame();
        }
        if (Aigame != null) {
            Aigame.updateGame();
        }
    }
    public void startGameTimer() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void stopGameTimer() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }


    public void switchToPanelGame(IterfaceGamePanel gamePanel) {
        endNick = menu.getNick();
        getContentPane().remove(menu);
        menu = null;

        Dimension panelPreferredSize = gamePanel.getBoardSize();


        int frameWidth = panelPreferredSize.width + 16;
        int frameHeight = panelPreferredSize.height + 100;


        this.setSize(frameWidth, frameHeight);


        add((Component) gamePanel, BorderLayout.SOUTH);


        revalidate();
        ((Component) gamePanel).requestFocusInWindow();
        setTitle("Object.Snake");
        //repaint();


        Dimension panelSize = gamePanel.getBoardSize();
        System.out.println("Wielkość panelu gry: " + panelSize.width + " x " + panelSize.height);

        int scoretoView = gamePanel.getCurrentScore();

        score = new ScorePanel(frameHeight - panelSize.height, scoretoView);
        add(score);
        endScore = score.getScore();
        startGameTimer();
    }

    public void updateScorePanel(IterfaceGamePanel gamePanel) {

        score.updateScore(gamePanel.getCurrentScore());
        endScore = score.getScore();
    }

    public void saveHighScore() throws IOException {

        System.out.println("Gracz :" + endNick + " End Score :" + endScore);
        over.set_nick(endNick);
        over.set_score(endScore);
        int highScore = 0;
        String highScorePlayer = "";
        if (Files.exists(Paths.get(FILE_PATH))) {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                highScorePlayer = parts[0];
                highScore = Integer.parseInt(parts[1]);
                over.set_beastScore(highScore);
                over.set_beastNick(highScorePlayer);
            }
            reader.close();
        }
        if (endScore > highScore) {

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            writer.write(endNick + "," + endScore);
            writer.close();
            System.out.println("New high score! Player: " + endNick + " Score: " + endScore);
        } else {
            System.out.println("High score  Player: " + highScorePlayer + " Score: " + highScore);
        }




    }
    public void switchToGameOverPanel(IterfaceGamePanel gamePanel) throws IOException {
        saveHighScore();


        if (gamePanel instanceof Component) {
            remove((Component) gamePanel);
        }
        game = null;
        Aigame = null;

        if (score != null) {
            remove(score);
            score = null;
        }


        repaint();
        add(over);
        revalidate();
        over.requestFocusInWindow();
        setTitle("Object.Snake");
        stopGameTimer();
    }

    public void switchToMenuPanel() {

        remove(over);
        over = null;
        Menu();
    }

    public IterfaceGamePanel getGamePanel(){
        return game;
    }

    public IterfaceGamePanel getAiGamePanel(){
        return Aigame;
    }


}