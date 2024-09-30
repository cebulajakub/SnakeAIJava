package Panel;


import Global.Global;
import Interface.*;
import Frame.*;
import Object.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import Algorithm.Node;
import Algorithm.AStar;
public class GameAiPanel extends JPanel implements  IterfaceGamePanel {

    static final int SCREEN_WIDTH = Global.SCREEN_WIDTH;
    static final int SCREEN_HEIGHT = Global.SCREEN_HEIGHT;
    static final int UNIT_SIZE = Global.UNIT_SIZE;
    static final int GAME_UNITS = Global.GAME_UNITS;
    static final int DELAY = Global.DELAY;
    private GameFrame frame;
    boolean keyPressed = false;


    private Thread aStarThread;



    Random random;
    SnakeAI snake;
    Fruit fruit;


    AStar as;
    boolean running = false;




    public int checkOneMore = 0;
    public int aftertmpX = 0;
    public int aftertmpY = 0;


    public GameAiPanel(GameFrame frame) {
        this.frame = frame;

        random = new Random();
        snake = new SnakeAI(3, UNIT_SIZE,GAME_UNITS);

        fruit = new Fruit(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE, snake.getX(), snake.getY(), snake.getBodyParts());



        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new GameAiPanel.MyKeyAdapter());

        as= new AStar();


        startGame();



       // System.out.println("col " + tmp + "row "+ rowY);
       // System.out.println("col " + Global.MAX_X + "row "+ Global.MAX_Y);



    }

    private void startAStarThread() {
        try {
        if (aStarThread != null && aStarThread.isAlive()) {
            aStarThread.interrupt();
        }
        aStarThread = new Thread(() -> {
            as = new AStar();
            as.setStartNode(snake.getHeadX() / UNIT_SIZE, snake.getHeadY() / UNIT_SIZE);
            as.setGoalNode(fruit.getX() / UNIT_SIZE, fruit.getY() / UNIT_SIZE);
            for (int i = snake.getBodyParts(); i > 0; i--) {
                as.setSolidNode(snake.getX()[i] / UNIT_SIZE, snake.getY()[i] / UNIT_SIZE);
            }
            as.setCostOnNodes();
            as.AutoSerch();
        });
       } catch (Exception e) {
            e.printStackTrace();
        }

    }




    public void startGame() {
        running = true;

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {

            for (int i = 0; i <= Global.MAX_X; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                if (i <= SCREEN_HEIGHT / UNIT_SIZE) {
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }

            }
            snake.draw(g);
            fruit.draw(g);

          //  for (int i = 0; i < snake.getCheckSnakeX().length; i++) {
          //      snake.drawCheck(g, snake.getCheckSnakeX()[i], snake.getCheckSnakeY()[i]);
           // }


        } else {
            System.out.println("Ostatni " + snake.getDirection());
            System.out.println("Fruit X " + fruit.getX() + " Y " + fruit.getY());
            System.out.println("Snake X " + snake.getHeadX() + " Y " + snake.getHeadY());
           // timer.stop();
            repaint();
            try {
                frame.switchToGameOverPanel(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public void move() {
        if (running) {
            checkApple_Frog();
            if (as.goalReached && !as.getVec().isEmpty()) {
                snake.move(as.getVec());
                if (!as.getVec().isEmpty()) {
                    as.poptVec();
                }
            } else {
                snake.move();
                startAStarThread();
                aStarThread.start();
            }
            checkCollision();
        }
    }




    public void checkApple_Frog() {
        if ((snake.getHeadX() == fruit.getX()) && (snake.getHeadY() == fruit.getY())) {
            snake.increaseLength();
            snake.increaseScore();
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).updateScorePanel(this);
            fruit.generateNewPosition(SCREEN_WIDTH, SCREEN_HEIGHT, snake.getX(), snake.getY(), snake.getBodyParts());

            startAStarThread();
            aStarThread.start();
        }
    }








    public void checkCollision() {


        // kolizję z samym sobą
        if (snake.checkSelfCollision() ) {
            running = false;
        }

        //  kolizję z granicami ekranu
        if (snake.checkBorderCollision(SCREEN_WIDTH, SCREEN_HEIGHT)) {
            running = false;
        }
    }




    public int getCurrentScore(){
        System.out.println("Score Game: " + snake.getScore());
        return snake.getScore();
    }



    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()){
                case KeyEvent.VK_SPACE:
                    System.out.println("AIAIAIAIAIAIA");

                    if ( keyPressed ) {
                        keyPressed = false;
                    }
                        else{
                            keyPressed = true;
                        }
                    break;
                case KeyEvent.VK_ENTER:
                    as.AutoLongSerch();
                    if ( keyPressed ) {
                        keyPressed = false;
                    }
                    else{
                        keyPressed = true;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    running = false;
                    //as.serch();

            }
        }
    }



    @Override
    public Dimension getBoardSize() {
        return this.getPreferredSize();
    }

    @Override
    public void updateGame() {
        if (keyPressed) {
            move();
        }
        repaint();
    }

}
