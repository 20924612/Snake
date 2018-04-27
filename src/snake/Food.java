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

    public Food(Snake snake) {

        this.snake = snake;
        generatePosition();

    }

    private void generatePosition() {
        boolean hit = true;
        Node node = null;
        while (hit) {
            hit=false;
            
            int randomRow = (int) (Math.random() * Board.NUM_ROW);
            int randomCol = (int) (Math.random() * Board.NUM_COL);
            node = new Node(randomRow, randomCol);

            ArrayList<Node> listNodes = snake.getListNodes();
            for (Node n : listNodes) {
                if(node.getRow()==n.getRow() && node.getCol() == n.getCol()){
                    hit=true;
                }
            }
        }
        position = node;
        
    }
    
    public void draw(Graphics g, int squareWidth, int squareHeight){
        
        Util.drawSquare(g, position, Color.yellow, squareWidth, squareHeight);
        
    }
    
    public Node getPosition(){
        
        return position;
    }
}
