package Object;

import java.awt.*;
import java.util.Random;

public class Obstacle {
    private int[] x;
    private int[] y;
    private final int UNIT_SIZE;
    int obstacle_size;
    public Obstacle(int screenWidth, int screenHeight, int unitSize,int obstacle_size) {
        UNIT_SIZE = unitSize;
        this.obstacle_size = obstacle_size;
        x= new int [obstacle_size];
        y = new int [obstacle_size];
        generateObstaclePos(screenWidth,  screenHeight);
       // generateRandomObstaclePositions( screenWidth,  screenHeight);
    }

    public void generateObstaclePos(int screenWidth, int screenHeight) {
        Random random = new Random();
        x[0] = random.nextInt((screenWidth / UNIT_SIZE) - 1) * UNIT_SIZE;
        y[0] = random.nextInt((screenHeight / UNIT_SIZE) - 1) * UNIT_SIZE;

        boolean generateRight = x[0] < screenWidth / 2;
        boolean generateDown = y[0] < screenHeight / 2;

        for (int i = 1; i < obstacle_size; ++i) {
            // Losowy wybór między generowaniem poziomym lub pionowym
            boolean horizontal = random.nextBoolean();

            if (horizontal) {
                if (generateRight) {
                    x[i] = x[i - 1] + UNIT_SIZE;
                    y[i] = y[i - 1];
                } else {
                    x[i] = x[i - 1] - UNIT_SIZE;
                    y[i] = y[i - 1];
                }
            } else {
                if (generateDown) {
                    y[i] = y[i - 1] + UNIT_SIZE;
                    x[i] = x[i - 1];
                } else {
                    y[i] = y[i - 1] - UNIT_SIZE;
                    x[i] = x[i - 1];
                }
            }


            if (x[i] < 0 || x[i] >= screenWidth || y[i] < 0 || y[i] >= screenHeight) {
                i--;
            }
        }
    }

    public void generateRandomObstaclePositions(int screenWidth, int screenHeight) {
        Random random = new Random();

        for (int i = 0; i < obstacle_size; ++i) {
            boolean validPosition = false;

            while (!validPosition) {
                int newX = random.nextInt(screenWidth / UNIT_SIZE) * UNIT_SIZE;
                int newY = random.nextInt(screenHeight / UNIT_SIZE) * UNIT_SIZE;

                // Sprawdzenie, czy pozycja nie koliduje z innymi przeszkodami
                boolean collision = false;
                for (int j = 0; j < i; j++) {
                    if (x[j] == newX && y[j] == newY) {
                        collision = true;
                        break;
                    }
                }

                if (!collision) {
                    x[i] = newX;
                    y[i] = newY;
                    validPosition = true;
                }
            }
        }


    }


    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);

        for(int i = 1 ; i < obstacle_size;i++){
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

        }
    }

    public int[] getX(){
        return x;
    }
    public int[] getY(){
        return y;
    }
    public int getSiz(){
        return obstacle_size;
    }


}
