package Object;

import Algorithm.Node;
import Global.Global;

import java.awt.*;
import java.util.PriorityQueue;
import java.util.Vector;

public class SnakeAI extends Snake {
    private static final char[] DIRECTIONS = {'U', 'D', 'L', 'R'};
    public int[] checkSnakeX = new int[3];
    public int[] checkSnakeY = new int[3];


    public SnakeAI(int initialSize, int unitSize, int game_uint) {
        super(initialSize, unitSize, game_uint);
      //  snakeColorHed = new Color(180, 50, 0);
        snakeColorHed = Color.GREEN;
        snakeColorBody = Color.RED;
    }

    @Override
    public void move() {

        //char nextDirection = calculateNextDirection(frutX, frutY);
        char nextDirection = calculateNextDirection();
        setDirection(nextDirection);

        super.move();


    }
public void move(Vector<Node> v) {




    // Pobierz współrzędne następnego węzła na ścieżce
    int last = v.size() - 1;
    int nextNodeX = v.get(last).getNodeX() * Global.UNIT_SIZE;
    int nextNodeY = v.get(last).getNodeY() * Global.UNIT_SIZE;

   // System.out.println("NextX " + nextNodeX);
   // System.out.println("NextY " + nextNodeY);

    afterLastX = getX()[getBodyParts()];
    afterLastY = getY()[getBodyParts()];

    // Przesuń segmenty ciała węża w kierunku następnego węzła na ścieżce
    for (int i = getBodyParts(); i > 0; i--) {
        setX(i,i-1);
        setY(i,i-1);
    }

    // Określ kierunek poruszania się węża wzdłuż ścieżki
    if (getHeadX() < nextNodeX) {
        setDirection('R');  // Prawo
    } else if (getHeadX() > nextNodeX) {
        setDirection('L');  // Lewo
    } else if (getHeadY() < nextNodeY) {
        setDirection('D');  // Dół
    } else if (getHeadY() > nextNodeY) {
        setDirection('U'); // Góra
    }


    // Przesuń głowę węża do następnego węzła na ścieżce
    setHeadX(nextNodeX);
    setHeadY(nextNodeY);




}
//    public char calculateNextDirection(int appleX, int appleY) {
//        char bestDirection = getDirection();
//        double minDistance = Double.MAX_VALUE;
//
//        for (char direction : DIRECTIONS) {
//            int[] nextHeadPosition = getNextHeadPosition(direction);
//            int nextHeadX = nextHeadPosition[0];
//            int nextHeadY = nextHeadPosition[1];
//
//           // double distance = Math.sqrt(Math.pow(appleX - nextHeadX, 2) + Math.pow(appleY - nextHeadY, 2));
//            double distance = Math.abs(appleX - nextHeadX) + Math.abs(appleY - nextHeadY);
//            if (distance < minDistance && !willCollideWithSelfOrWall(nextHeadX, nextHeadY)) {
//                minDistance = distance;
//                bestDirection = direction;
//            }
//        }
//
//        return bestDirection;
//    }

    public char calculateNextDirection() {
        char bestDirection = getDirection();
        double minDistance = Double.MAX_VALUE;
        char dir;
        for (char direction : DIRECTIONS) {
            dir = direction;
            int[] nextHeadPosition = getNextHeadPosition(direction);
            int nextHeadX = nextHeadPosition[0];
            int nextHeadY = nextHeadPosition[1];
            int[] nextnextHeadPosition = getNextHeadPosition(direction);
            int nextnextHeadX = nextnextHeadPosition[0];
            int nextnextHeadY = nextnextHeadPosition[1];



            //double distance = Math.sqrt(Math.pow(Global.SCREEN_WIDTH/2 - nextHeadX, 2) + Math.pow(Global.SCREEN_HEIGHT/2 - nextHeadY, 2));
            double distance = Math.sqrt(Math.pow(afterLastX - nextHeadX, 2) + Math.pow(afterLastY - nextHeadY, 2));
//
            if (distance < minDistance && !willCollideWithSelfOrWall(nextHeadX, nextHeadY)&& !willCollideWithSelfOrWall(nextnextHeadX,nextnextHeadY)) {
                minDistance = distance;
                bestDirection = direction;
            }

        }

        return bestDirection; // W przeciwnym razie zwracamy oryginalny kierunek
    }

    private boolean willCollideWithSelfOrWall(int x, int y) {

        if (x < 0 || y < 0 || x >= Global.SCREEN_WIDTH || y >= Global.SCREEN_WIDTH) {
            return true;
        }


        for (int i = 1; i < getBodyParts(); i++) {
            if (x == getX()[i] && y == getY()[i]) {
                return true;
            }
        }
        return false;
    }


//    public char calculateNextDirection(int appleX, int appleY) {
//        char bestDirection = getDirection();
//        double minDistance = Double.MAX_VALUE;
//
//
//        for (char direction : DIRECTIONS) {
//            int[] nextHeadPosition = getNextHeadPosition(direction);
//            int nextHeadX = nextHeadPosition[0];
//            int nextHeadY = nextHeadPosition[1];
//
//            double distance = Math.sqrt(Math.pow(appleX - nextHeadX, 2) + Math.pow(appleY - nextHeadY, 2));
//
//            if (distance < minDistance && !willCollideWithSelf(nextHeadX, nextHeadY)) {
//                minDistance = distance;
//                bestDirection = direction;
//            }
//        }
//
//        return bestDirection;
//    }


    private int[] getNextHeadPosition(char direction) {
        int[] currentPosition = {getX()[0], getY()[0]};

        switch (direction) {
            case 'U':
                currentPosition[1] -= Global.UNIT_SIZE;
                break;
            case 'D':
                currentPosition[1] += Global.UNIT_SIZE;
                break;
            case 'L':
                currentPosition[0] -= Global.UNIT_SIZE;
                break;
            case 'R':
                currentPosition[0] += Global.UNIT_SIZE;
                break;
        }

        return currentPosition;
    }




}