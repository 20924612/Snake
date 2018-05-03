/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author alu20924612v
 */
public class Food {

    private Node position;
    private Snake snake;
    private int growth;

    public Food(Snake snake, ArrayList <Node> obsList) {

        growth =1;
        this.snake = snake;
        generatePosition(obsList);

    }

    private void generatePosition(ArrayList <Node> obsList) {
        boolean hit = true;
        Node node = null;
        while (hit) {
            hit = true;

            int randomRow = (int) (Math.random() * Board.NUM_ROW);
            int randomCol = (int) (Math.random() * Board.NUM_COL);
            node = new Node(randomRow, randomCol, Color.YELLOW);

            ArrayList<Node> listNodes = snake.getListNodes();
            hit = Util.checkNodeWithNodeList(node, listNodes);
            if(!hit){
            hit= Util.checkNodeWithNodeList(node, obsList);
            }
            
        }
        position = node;

    }

    public void draw(Graphics g, int squareWidth, int squareHeight) {

        Util.drawSquare(g, position,position.getColor(), squareWidth, squareHeight);

    }

    public Node getPosition() {

        return position;
    }
    
    public int getGrowth(){
        return growth;
    }
    
    public void setGrowth(int g){
        
        growth=g;
    }
}
