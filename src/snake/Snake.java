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
    public static final Color[] SNAKE_COLORS = {Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.RED};
    public static final Node[] INIT_NODE = {new Node(Board.NUM_ROW / 4, Board.NUM_COL / 4),
        new Node(Board.NUM_ROW - Board.NUM_ROW / 4, Board.NUM_COL / 4),
        new Node(Board.NUM_ROW / 2, Board.NUM_COL / 4),
        new Node(Board.NUM_ROW / 2, Board.NUM_COL - Board.NUM_COL / 4)};
    public static final DirectionType[] SNAKE_INIT_DIRECTION = {DirectionType.DOWN, DirectionType.UP, DirectionType.RIGHT, DirectionType.LEFT};
    private ArrayList<Node> listNodes;
    private DirectionType direction;
    private int eatCounter;
    private boolean isAlive;
    private Color color;
    private boolean isTurning;
    private int id;

    public Snake(Color color, Node node, DirectionType direction, int id) {
        this.color = color;
        this.direction = direction;
        initListNodes(node);
        isAlive = true;
        isTurning = false;
        this.id = id;
        eatCounter = 0;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void die() {
        isAlive = false;

        for (Node n : listNodes) {
            n.setColor(Color.BLACK);
        }
    }

    public ArrayList<Node> getListNodes() {
        return listNodes;
    }

    public void changeDirection(DirectionType direction) {

        isTurning = true;
        this.direction = direction;
    }

    public DirectionType getDirection() {

        return direction;
    }

    private void initListNodes(Node n) {
        listNodes = new ArrayList<Node>();
        listNodes.add(n);
        switch (direction) {
            case LEFT:
                listNodes.add(new Node(n.getRow() - 1, n.getCol()));
                break;
            case RIGHT:
                listNodes.add(new Node(n.getRow() + 1, n.getCol()));
                break;
            case UP:
                listNodes.add(new Node(n.getRow(), n.getCol() - 1));
                break;
            case DOWN:
                listNodes.add(new Node(n.getRow(), n.getCol() + 1));
                break;

        }

    }

    public void draw(Graphics g, int squareWidth, int squareHeight) {

        for (Node n : listNodes) {
            Util.drawSquare(g, n, n.getColor(), squareWidth, squareHeight);
        }
    }

    public void move() {

        Node newNode = new Node(listNodes.get(0).getRow(), listNodes.get(0).getCol(), color);
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

    private void changeColor() {

        int colorNumber = 0;
        int counter = 255 / listNodes.size();

        for (Node n : listNodes) {
            colorNumber += counter;
            n.setColor(new Color(colorNumber, colorNumber, colorNumber));
        }
    }
    
    public int getId(){
        return id;
    }

    public boolean checkWithOtherSnake(Snake[] otherSnakes, int row, int col) {

        for (Snake otherSnake : otherSnakes) {
            for (Node n : otherSnake.getListNodes()) {
                if (id != otherSnake.getId() && col == n.getCol() && row == n.getRow()) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean getIsTurning() {

        return isTurning;
    }

    public void setIsTurning(boolean isTurning) {

        this.isTurning = isTurning;
    }
}
