package Panel;

import Algorithm.AStar;
import Global.Global;
import Interface.*;
import Frame.*;
import Object.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements  IterfaceGamePanel {

    static final int SCREEN_WIDTH = Global.SCREEN_WIDTH;
    static final int SCREEN_HEIGHT = Global.SCREEN_HEIGHT;
    static final int UNIT_SIZE = Global.UNIT_SIZE;
    static final int GAME_UNITS = Global.GAME_UNITS;

    private final GameFrame frame;
    boolean keyPressed = false;

    Random random;
    Snake snake;
    Fruit fruit;
    Obstacle obstacle;
    Obstacle obstacle1;
    Frog frog;
    SnakeAI Ai;
    SnakeAI AiFollow;
    AStar as;
    AStar asFollow;
    private Thread aStarThread;
    private Thread aStarFollow;


    boolean running = false;
    boolean Airunning = false;

    boolean AiFollowrunning = false;
    public GamePanel(GameFrame frame) {
        this.frame = frame;

        random = new Random();
        snake = new Snake(3, UNIT_SIZE,GAME_UNITS);
        fruit = new Fruit(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE, snake.getX(), snake.getY(), snake.getBodyParts());
        frog = new Frog(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE, snake.getX(), snake.getY(), snake.getBodyParts());
        Ai = new SnakeAI(3, UNIT_SIZE,GAME_UNITS);
        AiFollow = new SnakeAI(3, UNIT_SIZE,GAME_UNITS);
        obstacle = new Obstacle(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE,10);
     //   obstacle1 = new Obstacle(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE,10);
        Ai.setHeadX(SCREEN_WIDTH-UNIT_SIZE);
        Ai.setHeadY(SCREEN_HEIGHT- UNIT_SIZE);

        AiFollow.setHeadX(0);
        AiFollow.setHeadY(SCREEN_HEIGHT - UNIT_SIZE);
        AiFollow.setBodyColor(new Color(0, 0, 255));

        frog.setX(SCREEN_WIDTH/2);
        frog.setY(SCREEN_HEIGHT/2);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());



        as = new AStar();
        asFollow = new AStar();
//        for (int i = 0; i < obstacle.getSiz(); i++) {
//            asFollow.setSolidNode(obstacle.getX()[i] / UNIT_SIZE, obstacle.getY()[i] / UNIT_SIZE);
//            as.setSolidNode(obstacle.getX()[i] / UNIT_SIZE, obstacle.getY()[i] / UNIT_SIZE);
//        }



        startGame();
    }


    public void startGame() {

        running = true;
        Airunning = true;
        AiFollowrunning = true;

        restartAStarThreads();

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            for (int i = 0; i <= SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                if (i <= SCREEN_HEIGHT / UNIT_SIZE) {
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
            }
            obstacle.draw(g);
            snake.draw(g);
            if(Airunning){
            Ai.draw(g);
            }
            if(AiFollowrunning) {
                AiFollow.draw(g);
            }

           // obstacle1.draw(g);

            fruit.draw(g);
            if (frog.getX() != -1) {
                frog.draw(g);
//                for (int i = 0; i < frog.getCheckSnakeX().length; i++) {
//                    frog.drawCheck(g, frog.getCheckSnakeX()[i], frog.getCheckSnakeY()[i]);
//                }
            }




        } else {


            repaint();
            try {
//                if (aStarThread != null && aStarThread.isAlive()) {
//                    aStarThread.interrupt();
//                }
//                if (aStarFollow != null && aStarFollow.isAlive()) {
//                    aStarFollow.interrupt();
//                }
                frame.switchToGameOverPanel(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }






    public void updateGameLogic(Snake snake, SnakeAI ai, AStar aStar) {
        if (aStar.goalReached && !aStar.getVec().isEmpty()) {
          //  System.out.println("A*A");
            ai.move(aStar.getVec());
            if (!aStar.getVec().isEmpty()) {
                aStar.poptVec();
            }
        } else {
         //   System.out.println("CalcA");
            ai.move();
        }
    }


    public void moveAi(SnakeAI ai, AStar aStar) {

            updateGameLogic(snake, ai, aStar);

    }


    public void move() {
        if (running) {
            snake.move();
            if(AiFollowrunning){
                moveAi(AiFollow, asFollow);
                frog.chcekSnake(AiFollow.getDirection(), AiFollow.getHeadX(), AiFollow.getHeadY());
                frog.move(AiFollow.getDirection(), AiFollow.getHeadX(), AiFollow.getHeadY(), AiFollow.getX(), AiFollow.getY());
                checkAppleAndIncreaseLength(AiFollow, fruit);
            }
            if(Airunning){
                moveAi(Ai, as);
                frog.chcekSnake(Ai.getDirection(), Ai.getHeadX(), Ai.getHeadY());
                frog.move(Ai.getDirection(), Ai.getHeadX(), Ai.getHeadY(), Ai.getX(), Ai.getY());
                checkAppleAndIncreaseLength(Ai, fruit);
            }

            frog.chcekSnake(snake.getDirection(), snake.getHeadX(), snake.getHeadY());
            frog.move(snake.getDirection(), snake.getHeadX(), snake.getHeadY(), snake.getX(), snake.getY());
            checkAppleAndIncreaseLength(snake, fruit);
//            checkAppleAndIncreaseLength(Ai, fruit);
           // checkAppleAndIncreaseLength(AiFollow, fruit);
            checkCollisionWithObstacle();
            checkCollision();
            if(Airunning || AiFollowrunning){
            checkCollisionWithAi(Ai, AiFollow);
            }
            if(Airunning && AiFollowrunning){
            checkCollisionBetweenAi(Ai, AiFollow);
            }

        }
        restartAStarThreads();
    }


    public void restartAStarThreads() {
        try {
        if (aStarThread != null && aStarThread.isAlive()) {
            aStarThread.interrupt(); // przerwa  wątku

        }
        if(Airunning){
            aStarThread = new Thread(this::runAStar);
            aStarThread.start();}


        if (aStarFollow != null && aStarFollow.isAlive()) {
            aStarFollow.interrupt(); // przerwa  wątku
        }
        if(AiFollowrunning){
            aStarFollow = new Thread(this::runAStarFollow);
            aStarFollow.start();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void runAStar() {
        try {
            as=new AStar();
            as.setStartNode(Ai.getHeadX() / UNIT_SIZE, Ai.getHeadY() / UNIT_SIZE);
            as.setGoalNode(fruit.getX() / UNIT_SIZE, fruit.getY() / UNIT_SIZE);
            for (int i = 0; i < obstacle.getSiz(); i++) {
                as.setSolidNode(obstacle.getX()[i] / UNIT_SIZE, obstacle.getY()[i] / UNIT_SIZE);
             //   asFollow.setSolidNode(obstacle.getX()[i] / UNIT_SIZE, obstacle.getY()[i] / UNIT_SIZE);
            }

            for (int i = Ai.getBodyParts(); i > 0; i--) {
                as.setSolidNode(Ai.getX()[i] / UNIT_SIZE, Ai.getY()[i] / UNIT_SIZE);
            }

            for (int i = snake.getBodyParts(); i >= 0; i--) {
                as.setSolidNode(snake.getX()[i] / UNIT_SIZE, snake.getY()[i] / UNIT_SIZE);
            }
            if(AiFollowrunning){
            for (int i = AiFollow.getBodyParts(); i >= 0; i--) {
                as.setSolidNode(AiFollow.getX()[i] / UNIT_SIZE, AiFollow.getY()[i] / UNIT_SIZE);
            }}
            as.setCostOnNodes();
            as.AutoSerch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void runAStarFollow() {
        try {
            asFollow=new AStar();
            asFollow.setStartNode(AiFollow.getHeadX() / UNIT_SIZE, AiFollow.getHeadY() / UNIT_SIZE);
            asFollow.setGoalNode(snake.getHeadX() / UNIT_SIZE, snake.getHeadY() / UNIT_SIZE);
            for (int i = 0; i < obstacle.getSiz(); i++) {
                asFollow.setSolidNode(obstacle.getX()[i] / UNIT_SIZE, obstacle.getY()[i] / UNIT_SIZE);
            }


            for (int i = AiFollow.getBodyParts(); i > 0; i--) {
                asFollow.setSolidNode(AiFollow.getX()[i] / UNIT_SIZE, AiFollow.getY()[i] / UNIT_SIZE);
            }

            for (int i = snake.getBodyParts(); i > 0;i--) {
                asFollow.setSolidNode(snake.getX()[i] / UNIT_SIZE, snake.getY()[i] / UNIT_SIZE);
            }

            if(Airunning){
            for (int i = Ai.getBodyParts(); i >= 0;i--) {
                asFollow.setSolidNode(Ai.getX()[i] / UNIT_SIZE, Ai.getY()[i] / UNIT_SIZE);
            }}
            asFollow.setCostOnNodes();
            asFollow.AutoSerch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkAppleAndIncreaseLength(Snake snake, Fruit fruit) {
        if ((snake.getHeadX() == fruit.getX()) && (snake.getHeadY() == fruit.getY())) {
            snake.increaseLength();
            snake.increaseScore();
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).updateScorePanel(this);
            fruit.generateNewPosition(SCREEN_WIDTH, SCREEN_HEIGHT, snake.getX(), snake.getY(), snake.getBodyParts());
            while (isPositionOnObstacle(fruit.getX(), fruit.getY())) {
                fruit.generateNewPosition(SCREEN_WIDTH, SCREEN_HEIGHT, snake.getX(), snake.getY(), snake.getBodyParts());
            }
        }
        if (fruit.checkNewPosition(snake.getX(), snake.getY(), snake.getBodyParts())) {
            fruit.generateNewPosition(SCREEN_WIDTH, SCREEN_HEIGHT, snake.getX(), snake.getY(), snake.getBodyParts());
            while (isPositionOnObstacle(fruit.getX(), fruit.getY())) {
                fruit.generateNewPosition(SCREEN_WIDTH, SCREEN_HEIGHT, snake.getX(), snake.getY(), snake.getBodyParts());
            }
        }
        if ((snake.getHeadX() == frog.getX()) && (snake.getHeadY() == frog.getY())) {
            snake.increaseLength();
            snake.increaseScore();
            ((GameFrame) SwingUtilities.getWindowAncestor(this)).updateScorePanel(this);
            frog.setEaten(true);
            frog.setX(-1);
            frog.setY(-1);

        }


        // if(snake.getBodyParts() % 10 == 0 && frog.isEaten()){
        if( frog.isEaten()){
            frog.generateNewPosition(SCREEN_WIDTH, SCREEN_HEIGHT, snake.getX(), snake.getY(), snake.getBodyParts());
            frog.set_checkPoint();
            frog.setEaten(false);
        }
    }

    private boolean isPositionOnObstacle(int x, int y) {
        for (int i = 0; i < obstacle.getSiz(); i++) {
            if (obstacle.getX()[i] == x && obstacle.getY()[i] == y) {
                return true;
            }
        }
        return false;
    }

    public void checkCollision() {
        if (snake.checkSelfCollision() || snake.checkBorderCollision(SCREEN_WIDTH, SCREEN_HEIGHT)) {
            running = false;
        }
    }
    public void checkCollisionBetweenAi(SnakeAI ai1, SnakeAI ai2) {
        if (ai2.checkAiCollision(ai1)  ) {
            AiFollowrunning = false;

        }
        if(ai1.checkAiCollision(ai2) ){
            Airunning = false;
        }
    }
    public void checkCollisionWithObstacle() {
        for (int i = 0; i < obstacle.getSiz(); i++) {
            if (snake.getHeadX() == obstacle.getX()[i] && snake.getHeadY() == obstacle.getY()[i]) {
                running = false;
            }
            if (Airunning && Ai.getHeadX() == obstacle.getX()[i] && Ai.getHeadY() == obstacle.getY()[i]) {
                Airunning = false;
            }
            if (AiFollowrunning && AiFollow.getHeadX() == obstacle.getX()[i] && AiFollow.getHeadY() == obstacle.getY()[i]) {
                AiFollowrunning = false;
            }
        }
    }

    public void checkCollisionWithAi(SnakeAI ai1, SnakeAI ai2) {
        if (snake.checkAiCollision(ai1) && Airunning){
            Airunning = false;
            running = false;
        }
        if (snake.checkAiCollision(ai2) && AiFollowrunning){
            AiFollowrunning = false;
            running = false;
        }
        if (AiFollowrunning && (ai2.checkAiCollision(snake) || ai2.checkSelfCollision())) {
            AiFollowrunning = false;

        }

        if (Airunning && (ai1.checkAiCollision(snake) || ai1.checkSelfCollision())) {
            Airunning = false;

        }


    }





    public int getCurrentScore(){
        System.out.println("Score Game: " + snake.getScore());
        return snake.getScore();
    }



    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
           // keyPressed = true;
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
               // case KeyEvent.VK_W:
                    if (snake.getDirection() != 'D') {
                        snake.setDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                //case KeyEvent.VK_S:
                    if (snake.getDirection() != 'U') {
                        snake.setDirection('D');
                    }
                    break;
                case KeyEvent.VK_LEFT:
               // case KeyEvent.VK_A:
                    if (snake.getDirection() != 'R') {
                        snake.setDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
               // case KeyEvent.VK_D:
                    if (snake.getDirection() != 'L') {
                        snake.setDirection('R');
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    System.out.println("PLAYERPLAYETRPLAYER");
                       keyPressed = true;
                       break;
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