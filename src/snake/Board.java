package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20924612v
 */
public class Board extends JPanel implements ActionListener {

    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (canMove(DirectionType.UP)) {
                        snake.changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMove(DirectionType.DOWN)) {
                        snake.changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (canMove(DirectionType.LEFT)) {
                        snake.changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMove(DirectionType.RIGHT)) {
                        snake.changeDirection(DirectionType.RIGHT);
                    }
                    break;

                default:
                    break;
            }
            repaint();
        }
    }
    public static final int NUM_ROW = 30;
    public static final int NUM_COL = 30;

    private int deltaTime;
    private Food food;
    private SpecialFood specialFood;
    private Snake snake;
    private Timer timer;
    private KeyAdapter keyAdapter;
    private ScoreBoard scoreBoard;
    private Timer specialFoodTimer;
    private ArrayList<Node> obstacleListNodes;

    public Board() {
        super();
        obstacleListNodes = new ArrayList<Node>();
        
        snake = new Snake();
        initObstacles();
        deltaTime = 300;
        initFood();
        timer = new Timer(deltaTime, this);
        keyAdapter = new MyKeyAdapter();
        setFocusable(true);
        scoreBoard = null;
        food = new Food(snake);

    }

    public void InitGame() {
        timer.start();
        setFocusable(true);
        initFood();
        addKeyListener(keyAdapter);
    }

    private void initFood() {
        if (scoreBoard != null) {
            food = new Food(snake);
            specialFood = null;
            if (scoreBoard.getScore() > 0 && scoreBoard.getScore() % 5 == 0) {
                food = null;
                specialFood = new SpecialFood(snake);
                specialFoodTimer = new Timer(specialFood.getVisibleTime() * 1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        specialFood = null;
                        food = new Food(snake);
                        specialFoodTimer.stop();

                    }
                });

                specialFoodTimer.start();
            } else {
                if(specialFoodTimer.isRunning()){
                    specialFoodTimer.stop();
                }
                food = new Food(snake);
                specialFood = null;
            }
        }
    }

    private void initObstacles() {

        boolean hit;
        int counter = 0;
        while (counter < 4) {
            hit=false;
            int row = (int) (Math.random() * NUM_ROW);
            int col = (int) (Math.random() * NUM_COL);

            Node obstacle = new Node(row, col);

            for (Node nowNode : snake.getListNodes()) {
                if (obstacle.getRow() == nowNode.getRow() && obstacle.getCol() == nowNode.getCol()) {
                    hit = true;                                                          
                }
            }
            if(hit = false){
                  obstacleListNodes.add(obstacle);
            counter++;
            }
        }
        
    }

    private void drawObstacles(Graphics g) {

        for (Node obs : obstacleListNodes) {
            Util.drawSquare(g, obs, Color.black, getSquareWidth(), getSquareHeight());
        }
    }

//Main loop
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!checkCollision()) {
            snake.move();
        } else {
            processGameOver();

        }

        repaint();

        if ((food != null || specialFood != null) && checkFood()) {

            snake.eat(food);
            initFood();
            scoreBoard.setScore(scoreBoard.getScore() + 1);
        }
    }

    private boolean canMove(DirectionType newDirection) {

        DirectionType snakeDirection = snake.getDirection();
        switch (newDirection) {
            case UP:
            case DOWN:
                if (snakeDirection == DirectionType.UP || snakeDirection == DirectionType.DOWN) {
                    return false;
                } else {
                    return true;
                }

            case RIGHT:
            case LEFT:
                if (snakeDirection == DirectionType.LEFT || snakeDirection == DirectionType.RIGHT) {
                    return false;
                } else {
                    return true;
                }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {

        drawObstacles(g);
        super.paintComponent(g);
        drawBoard(g);
        snake.draw(g, getSquareWidth(), getSquareHeight());
        if (food != null) {
            food.draw(g, getSquareWidth(), getSquareHeight());
        }
        if (specialFood != null) {
            specialFood.draw(g, getSquareWidth(), getSquareHeight());
        }
    }

    private void drawBoard(Graphics g) {

        g.setColor(Color.gray);
        g.drawRect(0, 0, getSquareWidth() * NUM_COL, getSquareHeight() * NUM_ROW);
    }

    private int getSquareWidth() {
        return getWidth() / NUM_COL;
    }

    private int getSquareHeight() {
        return getHeight() / NUM_ROW;
    }

    private boolean checkFood() {

        Node nodeFood = null;
        Node nodeSnake = snake.getListNodes().get(0);
        if (food != null) {
            nodeFood = food.getPosition();
        } else {
            nodeFood = specialFood.getPosition();

        }
        if (nodeSnake.getRow() == nodeFood.getRow() && nodeSnake.getCol() == nodeFood.getCol()) {
            return true;
        }

        return false;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {

        this.scoreBoard = scoreBoard;

    }

    private boolean checkCollision() {
        int nextPosRow = snake.getListNodes().get(0).getRow();
        int nextPosCol = snake.getListNodes().get(0).getCol();
        DirectionType direction = snake.getDirection();
        switch (direction) {
            case UP:

                nextPosRow--;
                if (nextPosRow < 0) {
                    return true;
                }
                snake.checkWithItself(nextPosRow, nextPosCol);
                break;
            case DOWN:
                nextPosRow++;
                if (nextPosRow > NUM_ROW - 1) {
                    return true;
                }
                break;
            case LEFT:
                nextPosCol--;
                if (nextPosCol < 0) {
                    return true;
                }
                break;
            case RIGHT:
                nextPosCol++;
                if (nextPosCol > NUM_COL - 1) {
                    return true;
                }
                break;
        }
        return snake.checkWithItself(nextPosRow, nextPosCol);

    }

    private void processGameOver() {

        removeKeyListener(keyAdapter);

        timer.stop();
        System.out.println("Game Over");
    }
}
