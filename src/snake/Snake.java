package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20924612v
 */
public class Snake {

    /**
     * @param args the command line arguments
     */
    private ArrayList<Node> listNodes;
    private DirectionType direction;
    private int eatCounter;

    public Snake() {

        initListNodes();
        direction = DirectionType.RIGHT;
        eatCounter = 0;
    }

    public ArrayList<Node> getListNodes() {
        return listNodes;
    }

    public void changeDirection(DirectionType direction) {

        this.direction = direction;
    }

    public DirectionType getDirection() {

        return direction;
    }

    private void initListNodes() {
        listNodes = new ArrayList<Node>();
        listNodes.add(new Node(Board.NUM_ROW / 2, Board.NUM_COL / 2, Color.BLACK));
        listNodes.add(new Node(Board.NUM_ROW / 2 - 1, Board.NUM_COL / 2, Color.BLACK));

    }

    public void draw(Graphics g, int squareWidth, int squareHeight) {

        for (Node n : listNodes) {
            Util.drawSquare(g, n, n.getColor(), squareWidth, squareHeight);
        }
    }

    public void move() {

        Node newNode = new Node(listNodes.get(0).getRow(), listNodes.get(0).getCol(), Color.BLACK);
        switch (direction) {
            case UP:
                newNode.setRow(newNode.getRow() - 1);

                break;
            case DOWN:
                newNode.setRow(newNode.getRow() + 1);
                break;
            case LEFT:
                newNode.setCol(newNode.getCol() - 1);
                break;
            case RIGHT:
                newNode.setCol(newNode.getCol() + 1);
                break;
        }

        if (eatCounter > 0) {
            eatCounter--;
            changeColor();
        } else {
            listNodes.remove(listNodes.size() - 1);
        }
        listNodes.add(0, newNode);
    }

    public void eat(Food food) {
        eatCounter += food.getGrowth();

    }

    public boolean checkWithItself(int row, int col) {

        for (Node n : getListNodes()) {
            if (col == n.getCol() && row == n.getRow()) {
                return true;
            }

        }
        return false;
    }
    
    private void changeColor(){
       
        int colorNumber=0;
        int counter=255/listNodes.size();
        
        for(Node n: listNodes){
            colorNumber += counter;
            n.setColor(new Color(colorNumber, colorNumber, colorNumber));
        }
    }
}
