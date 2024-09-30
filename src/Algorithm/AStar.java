package Algorithm;



import Global.Global;
import Interface.IterfaceGamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

public class AStar{


   Vector<Node> v=new Vector<>();
   public Node [][] node = new Node[Global.MAX_X][Global.MAX_Y];
   Node startNode,goalNode,currentNode;
   int colX ;
   int rowY ;
   ArrayList<Node> openList = new ArrayList<>();
   ArrayList<Node> checkedList = new ArrayList<>();
   public boolean goalReached = false;

   public boolean pathTracking = false;
   private Node currentPathNode;


   public AStar(){

      for (rowY = 0; rowY < Global.MAX_Y; rowY++) {
         for (colX = 0; colX < Global.MAX_X; colX++) {
            node[colX][rowY] = new Node(colX, rowY);

         }
      }
   }

   public Node[][] GetNodes(){
      return node;
   }
   public Node GetNode(int x, int y){
      return node[x][y];
   }



   public  void setStartNode(int col,int row){
      node[col][row].setAsStart();
      startNode = node[col][row];
      currentNode = startNode;
   }
   public  void setGoalNode(int col,int row) {
      node[col][row].setAsGoal();
      goalNode = node[col][row];

   }
   public  void setSolidNode(int col,int row){
      node[col][row].setAsSolid();
   }
   public  void setCostOnNodes(){
      int col =0;
      int row = 0;
      while(col < Global.MAX_X && row < Global.MAX_Y){
      //   getCost(node[col][row]);
         getCost(node[col][row]);
         col++;
         if(col == Global.MAX_X){
            col = 0 ;
            row++;
         }
      }
   }


   private  void getCost(Node node){
      int x = Math.abs(node.x - startNode.x) ;
      int y = Math.abs(node.y - startNode.y);
      node.gCost = x + y;

      x = Math.abs(node.x - goalNode.x);
      y = Math.abs(node.y - goalNode.y);
      node.hCost = x + y;

      node.fCost = node.gCost + node.hCost;
    //  if(node != startNode && node != goalNode){

         node.setText("<html>F:"+ node.fCost + "<br>G:"+node.gCost+ "<br>H:"+node.hCost+ "</html>");
     // }
   }

   private  void getLongCost(Node node){
      int x = Math.abs(node.x - startNode.x) ;
      int y = Math.abs(node.y - startNode.y);
      node.gCost = x + y;

      x = Math.abs(node.x - goalNode.x);
      y = Math.abs(node.y - goalNode.y);
      node.hCost = x + y;

      node.fCost = node.gCost + node.hCost;
      //  if(node != startNode && node != goalNode){

      node.setText("<html>F:"+ node.fCost + "<br>G:"+node.gCost+ "<br>H:"+node.hCost+ "</html>");
      // }
   }

   public boolean serch(){
      if(!goalReached){
         int col = currentNode.x;
         int row = currentNode.y;

         currentNode.setAsChcek();
         checkedList.add(currentNode);
         openList.remove(currentNode);

         if(row -1 >= 0){
            openNode(node[col][row-1]);
         }
         if(col-1 >=0){
            openNode(node[col-1][row]);
         }
         if(row + 1<Global.MAX_Y){
            openNode(node[col][row+1]);
         }
         if(col+1 < Global.MAX_X) {
            openNode(node[col + 1][row]);
         }

         boolean allVisited = true;
         for (Node n : openList) {
            if (!n.checked) {
               allVisited = false;
               break;
            }
         }
         if (allVisited) {

            return true;
         }

         int beastNode = 0;
         int beastNodefCost = 999;
         for(int i = 0;i <openList.size();i++){
            if(openList.get(i).fCost < beastNodefCost){
               beastNode = i;
               beastNodefCost = openList.get(i).fCost;

            }
            else if(openList.get(i).fCost == beastNodefCost){
               if(openList.get(i).hCost > openList.get(beastNode).hCost){
                  beastNode = i;
               }
            }
         }
         currentNode = openList.get(beastNode);
         currentNode.setBackground(Color.CYAN);
         if(currentNode == goalNode){
            goalReached = true;
            System.out.println("znal");
            v.add(currentNode);
           // trackPath();
            currentPathNode = goalNode;
            pathTracking = true;
         }

      }
      return false;
   }

   public boolean AutoSerch(){

      while(!goalReached){
         int col = currentNode.x;
         int row = currentNode.y;

         currentNode.setAsChcek();
         checkedList.add(currentNode);
         openList.remove(currentNode);

         if(row -1 >= 0){
            openNode(node[col][row-1]);
         }
         if(col-1 >=0){
            openNode(node[col-1][row]);
         }
         if(row + 1<Global.MAX_Y){
            openNode(node[col][row+1]);
         }
         if(col+1 < Global.MAX_X) {
            openNode(node[col + 1][row]);
         }

         boolean allVisited = true;
         for (Node n : openList) {
            if (!n.checked) {
               allVisited = false;
               break;
            }
         }
         if (allVisited) {

            return true;
         }


         int beastNode = 0;
         int beastNodefCost = 999;
         for(int i = 0;i <openList.size();i++){
            if(openList.get(i).fCost < beastNodefCost){
               beastNode = i;
               beastNodefCost = openList.get(i).fCost;
            }
            else if(openList.get(i).fCost == beastNodefCost){
               if(openList.get(i).gCost < openList.get(beastNode).gCost){
                  beastNode = i;
               }
            }
         }

         currentNode = openList.get(beastNode);
         if(currentNode == goalNode){

            goalReached = true;
            v.add(currentNode);

            trackPath();

         }

      }


      return false;
   }


   public void trackPathStep() {
      if (currentPathNode != startNode) {
         currentPathNode = currentPathNode.parent;
         currentPathNode.parent.setBackground(Color.MAGENTA);
         if (currentPathNode != startNode) {
           // System.out.println("w");
            currentPathNode.setAsPath();
            v.add(currentPathNode);
         }
      } else {
         pathTracking = false;
      }
   }


   public int a = 0;
   public boolean AutoLongSerch(){

      //while(checkedList.size() != (Global.MAX_X * Global.MAX_Y )){
      while(!goalReached){
         int col = currentNode.x;
         int row = currentNode.y;

         currentNode.setAsChcek();
         checkedList.add(currentNode);
         openList.remove(currentNode);

         if(row -1 >= 0){
            openNode(node[col][row-1]);

         }
         if(col-1 >=0){
            openNode(node[col-1][row]);

         }
         if(row + 1<Global.MAX_Y){
            openNode(node[col][row+1]);

         }
         if(col+1 < Global.MAX_X) {
            openNode(node[col + 1][row]);

         }
//
//         boolean allVisited = true;
//         for (Node n : openList) {
//            if (!n.checked) {
//              // System.out.println("ALL FALSE");
//               allVisited = false;
//              // break;
//            }
//         }
//         if (allVisited) {
//            System.out.println("ALL TRUEE");
//            return true;
//         }


         int beastNode = 0;
         int beastNodefCost = 0;
         for (int i = 0; i < openList.size(); i++) {
            if(openList.get(i).fCost > beastNodefCost){
               beastNode = i;
               beastNodefCost = openList.get(i).fCost;
            }
            else if(openList.get(i).fCost == beastNodefCost){
               if(openList.get(i).gCost < openList.get(beastNode).gCost){
                  beastNode = i;
               }
            }
         }
         currentNode = openList.get(beastNode);
         if(currentNode == goalNode){


         //   System.out.println("XD");
            goalReached = true;
            v.add(currentNode);
            trackPath();

         }

      }

      return false;
   }





   public void openNode(Node node){
      if(!node.open && !node.checked && !node.solid){
         node.setAsOpen();
         node.parent = currentNode;
         openList.add(node);
         node.setBackground(Color.PINK);
      }
   }

   public  void trackPath(){
      Node current = goalNode;
      while(current != startNode){
         current = current.parent;
         if(current!= startNode){
           // System.out.println("w");
            current.setAsPath();
            v.add(current);

         }
      }
   }


   public Vector<Node> getVec(){
      return v;
   }
   public void poptVec() {
      if (!v.isEmpty()) {
       //  System.out.println("vec size przed " + v.size());
         v.remove(v.size() - 1); // Usuń ostatni element z wektora
        // System.out.println("vec size " + v.size());
      }
   }




}
