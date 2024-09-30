package Object;

import java.awt.*;
import java.util.Random;

public class Fruit {
    private int x;
    private int y;
    private final int UNIT_SIZE;

    public Fruit(int screenWidth, int screenHeight, int unitSize,int[] snakeX, int[] snakeY,int numSnakeBody) {
        UNIT_SIZE = unitSize;
        generateNewPosition(screenWidth, screenHeight,  snakeX,  snakeY, numSnakeBody);
    }




    public void generateNewPosition(int screenWidth, int screenHeight, int[] snakeX, int[] snakeY,int numSnakeBody) {
        Random random = new Random();
        x = random.nextInt((screenWidth / UNIT_SIZE)) * UNIT_SIZE;
        y = random.nextInt((screenHeight / UNIT_SIZE)) * UNIT_SIZE;

        for (int i = numSnakeBody ; i >= 0; i--) {
            if (x == snakeX[i] && y == snakeY[i]) {
                System.out.println("AJJJ");
                generateNewPosition(screenWidth, screenHeight, snakeX, snakeY, i);
            }
        }
      //  System.out.println("Next apple X:" + x/UNIT_SIZE + "Y "+ y/UNIT_SIZE );

    }



    public boolean checkNewPosition( int[] snakeX, int[] snakeY,int numSnakeBody) {

        for (int i = numSnakeBody ; i >= 0; i--) {
            if (x == snakeX[i] && y == snakeY[i]) {
                System.out.println("AJJJ");
                return true;
            }

        }
      //  System.out.println("Next apple X:" + x/UNIT_SIZE + "Y "+ y/UNIT_SIZE );
        return false;
    }



    // Metoda do rysowania owocu
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
    }

    // Metody getter dla pozycji owocu
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setX(int set_x) {
        x = set_x;
    }

    public void setY(int set_y) {
        y = set_y;
    }

    public int getUNIT_SIZE(){
        return UNIT_SIZE;
    }



}