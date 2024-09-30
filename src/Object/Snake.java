package Object;

import java.awt.*;

public class Snake {
    private int[] x;
    private int[] y;
    private int bodyParts;
    private char direction;
    private final int UNIT_SIZE;
    private int Score;
    public int afterLastX = 0;
    public int afterLastY = 0;
    public Color snakeColorHed = Color.GREEN;
    public Color snakeColorBody = new Color(50, 180, 0);
    public Snake(int initialSize, int unitSize,int game_uint) {
        x = new int[game_uint];
        y = new int[game_uint];
        bodyParts = initialSize;
        UNIT_SIZE = unitSize;
        Score = 0;



        direction = 'U';
    }


    public void draw(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(snakeColorHed);
            } else {
                g.setColor(snakeColorBody);
            }
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }


    public void move() {
        afterLastX = x[bodyParts];
        afterLastY = y[bodyParts];
        for (int i = bodyParts ; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }


    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void setHeadXY(int setx, int sety) {
        this.x[0] = setx;
        this.y[0] = sety;

    }
    public int[] getX() {
        return x;
    }
    public void setX(int i,int j) {
         x[i] =x[j];
    }
    public void setY(int i,int j) {
        y[i] =y[j];
    }
    public int getHeadX() {
        return x[0];
    }
    public int getHeadY() {
        return y[0];
    }
    public void setHeadX(int x) {
         this.x[0] = x;
    }
    public void setHeadY(int y) {
        this.y[0] = y;
    }

    public int[] getY() {
        return y;
    }
    public int getScore() {
        return Score;
    }
    public void increaseScore() {
        Score += 1;
    }

    public void increaseLength() {
        bodyParts++;
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public boolean checkSelfCollision() {
        for (int i = bodyParts ; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                return true;
            }
        }
        return false;
    }
    public boolean checkAiCollision(Snake ai) {
        for (int i = ai.bodyParts ; i >= 0; i--) {
            if (x[0] == ai.x[i] && y[0] == ai.y[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBorderCollision(int screenWidth, int screenHeight) {

        if (x[0] < 0 || x[0] >= screenWidth || y[0] < 0 || y[0] >= screenHeight) {
            return true;
        }
        return false;
    }

    public void setBodyColor(Color c){
        snakeColorBody = c;
    }





}

