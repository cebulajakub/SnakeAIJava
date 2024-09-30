package Object;

import Global.Global;


import java.awt.*;
import java.util.Random;

public class Frog extends Fruit {
    private boolean eaten;
    private boolean draw_frog = true;
    private  int[]  checkSnakeX = new int[12];
    private  int[]  checkSnakeY = new int[12];
    //private char snakeDir;
    public Frog(int screenWidth, int screenHeight, int unitSize, int[] snakeX, int[] snakeY, int numSnakeBody) {
        super(screenWidth, screenHeight, unitSize, snakeX, snakeY, numSnakeBody);
        eaten = false;

        printFrog();
        set_checkPoint();
       // printCheckSnake();

    }
    @Override
    public void generateNewPosition(int screenWidth, int screenHeight, int[] snakeX, int[] snakeY,int numSnakeBody) {
        Random random = new Random();
        setX(random.nextInt((screenWidth / getUNIT_SIZE())) * getUNIT_SIZE());
        setY (random.nextInt((screenHeight / getUNIT_SIZE())) * getUNIT_SIZE());

        for (int i = numSnakeBody ; i >= 0; i--) {
            if (getX() == snakeX[i] && getY() == snakeY[i]) {
                System.out.println("AJJJ");
                generateNewPosition(screenWidth, screenHeight, snakeX, snakeY, i);

            }
        }
        eaten = false;
        draw_frog = true;
       // set_checkPoint();

    }


    @Override
    public void draw(Graphics g) {
        //if (eaten) {
            g.setColor(Color.GREEN);
        //} else {
         //   g.setColor(Color.RED);g
       // }
        g.fillOval(getX(), getY(), getUNIT_SIZE(), getUNIT_SIZE());
    }

    public void set_checkPoint(){
        int frogX = getX();
        int frogY = getY();

        int[][] surroundings = {
                {-2 , -2 },  // Lewy górny róg
                {0, -2},   // Górna krawędź
                {2, -2},   // Prawy górny róg
                {-1, -1},  // x-1 y+1
                {1, -1},   // x+1 y+1
                {-2, 0},  // Lewa krawędź
                {2, 0},   // Prawa krawędź
                {-1, 1}, // x-1 y-1
                {1, 1},  // x+1 y-1
                {-2, 2}, // Lewy dolny róg
                {0, 2},  // Dolna krawędź
                {2, 2}   // Prawy dolny róg
        };


        for (int i = 0; i < checkSnakeX.length; i++) {
            checkSnakeX[i] = frogX + surroundings[i][0] *  getUNIT_SIZE();
            checkSnakeY[i] = frogY + surroundings[i][1] * getUNIT_SIZE();
        }
    }
    public void move(char snakeDir, int snakeHeadX, int snakeHeadY,int []snakeBodyX,int[] snakeBodyY) {

        for (int i = 0; i < checkSnakeX.length; i++) {
            if (snakeHeadX == checkSnakeX[i] && snakeHeadY == checkSnakeY[i]) {
                int newX = getX();
                int newY = getY();

                boolean nearBorder = newX <= 0 || newX >= Global.SCREEN_WIDTH - Global.UNIT_SIZE || newY <= 0 || newY >= Global.SCREEN_HEIGHT - Global.UNIT_SIZE;

                if (nearBorder) {
                    Random random = new Random();
                    int randomDirection = random.nextInt(4);

                    switch (randomDirection) {
                        case 0:
                            newX -= getUNIT_SIZE();
                            break;
                        case 1:
                            newX += getUNIT_SIZE();
                            break;
                        case 2:
                            newY -= getUNIT_SIZE();
                            break;
                        case 3:
                            newY += getUNIT_SIZE();
                            break;
                    }
                } else {

                    boolean intersectsSnake = false;
                    for (int j = 0; j < snakeBodyX.length; j++) {
                        if (newX == snakeBodyX[j] && newY == snakeBodyY[j]) {
                            intersectsSnake = true;
                            break;
                        }
                    }


                    if (!intersectsSnake) {
                        switch (snakeDir) {
                            case 'U':
                                newY -= getUNIT_SIZE();
                                break;
                            case 'D':
                                newY += getUNIT_SIZE();
                                break;
                            case 'L':
                                newX -= getUNIT_SIZE();
                                break;
                            case 'R':
                                newX += getUNIT_SIZE();
                                break;
                        }
                    }else {

                        Random random = new Random();
                        newX = random.nextInt((Global.SCREEN_WIDTH / getUNIT_SIZE())) * getUNIT_SIZE();
                        newY = random.nextInt((Global.SCREEN_HEIGHT / getUNIT_SIZE())) * getUNIT_SIZE();
                    }
                }
                boolean intersectsSnake = false;
                for (int j = 0; j < snakeBodyX.length; j++) {
                    if (newX == snakeBodyX[j] && newY == snakeBodyY[j]) {
                        intersectsSnake = true;
                        break;
                    }
                }

                if (newX >= 0 && newX < Global.SCREEN_WIDTH && newY >= 0 && newY < Global.SCREEN_HEIGHT && !intersectsSnake) {
                    setX(newX);
                    setY(newY);
                    set_checkPoint();
                }
                return;
            }
        }
    }


    public  int[] getCheckSnakeX(){
        return  checkSnakeX;
    }
    public  int[] getCheckSnakeY(){
        return  checkSnakeY;
    }

    public void drawCheck(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, getUNIT_SIZE(), getUNIT_SIZE());
    }

    public void printCheckSnake() {
        System.out.println("Punkty otoczenia frog'a:");

        // Wyświetlenie zawartości tablic checkSnakeX i checkSnakeY
        for (int i = 0; i < checkSnakeX.length; i++) {

            System.out.println("Punkt " + i + ": (" + checkSnakeX[i] + ", " + checkSnakeY[i] + ")");
        }
    }

    public void printFrog() {
        System.out.println("frog:");

        // Wyświetlenie zawartości tablic checkSnakeX i checkSnakeY

        System.out.println("Punkt X " + getX() + ": Y" + getY() );

    }




    public void move(char snakeDir){

    }

    public void chcekSnake(char snakeDir,int HeadX, int HeadY){
       // System.out.println("Object.Snake dir "+snakeDir);
    }


    public void setEaten(boolean set) {
        eaten = set;
    }
    public boolean isEaten(){
        return eaten;
    }
    public void setDraw_frog(boolean drawable){
        draw_frog = drawable;
    }
    public boolean isDrawable(){
        return draw_frog;
    }

}