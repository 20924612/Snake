package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
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
                    if (canMove(DirectionType.UP, snake1)) {
                        snake1.changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMove(DirectionType.DOWN, snake1)) {
                        snake1.changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (canMove(DirectionType.LEFT, snake1)) {
                        snake1.changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMove(DirectionType.RIGHT, snake1)) {
                        snake1.changeDirection(DirectionType.RIGHT);
                    }
                    break;

                default:
                    break;
            }
            repaint();
        }
    }
    
        class MyKeyAdapter2 extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (canMove(DirectionType.UP, snake2)) {
                        snake2.changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_S:
                    if (canMove(DirectionType.DOWN, snake2)) {
                        snake2.changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_A:
                    if (canMove(DirectionType.LEFT, snake2)) {
                        snake2.changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_D:
                    if (canMove(DirectionType.RIGHT, snake2)) {
                        snake2.changeDirection(DirectionType.RIGHT);
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
    private Snake snake1;
    private Timer timer;
    private KeyAdapter keyAdapter1;
    private ScoreBoard scoreBoard;
    private Timer specialFoodTimer;
    private ArrayList<Node> obstacleListNodes;
    private RecordsScore recordScore;
    private JFrame parentFrame;
    private KeyAdapter keyAdapter2;
    private Snake snake2;


    public Board() {
        super();
        initVariables();
    }

    private void initVariables() {

        obstacleListNodes = new ArrayList<Node>();
        snake1 = new Snake(DirectionType.RIGHT);
        snake2 = new Snake(DirectionType.LEFT);
        createObstacles(4);

        deltaTime = 300;
        initFood();
        timer = new Timer(deltaTime, this);
        keyAdapter1 = new MyKeyAdapter();
        keyAdapter2 = new MyKeyAdapter2();
        setFocusable(true);
        scoreBoard = null;
        food = new Food(obstacleListNodes, snake1, snake2);
        recordScore = null;
        parentFrame = null;
    }

    public void InitGame() {
        initVariables();
        timer.start();
        setFocusable(true);
        initFood();
        addKeyListener(keyAdapter1);
        addKeyListener(keyAdapter2);
    }

    private void initFood() {
        if (scoreBoard != null) {
            food = new Food(obstacleListNodes, snake1, snake2);
            specialFood = null;
            if (scoreBoard.getScore() > 0 && scoreBoard.getScore() % 5 == 0) {
                food = null;
                specialFood = new SpecialFood(obstacleListNodes, snake1, snake2);
                specialFoodTimer = new Timer(specialFood.getVisibleTime() * 1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        specialFood = null;
                        food = new Food(obstacleListNodes, snake1, snake2);
                        specialFoodTimer.stop();

                    }
                });

                specialFoodTimer.start();
            } else {
                if (specialFoodTimer != null && specialFoodTimer.isRunning()) {
                    specialFoodTimer.stop();
                }
                food = new Food(obstacleListNodes, snake1, snake2);
                specialFood = null;
            }
        }
    }

    private void createObstacles(int nObstacles) {

        boolean hit;
        int counter = 0;
        while (counter < nObstacles) {
            hit = false;
            int row = (int) (Math.random() * NUM_ROW);
            int col = (int) (Math.random() * NUM_COL);

            
            Node obstacle = new Node(row, col, Color.gray);
            hit = Util.checkNodeWithNodeList(obstacle, snake1.getListNodes());
            if (!hit) {
                hit = Util.checkNodeWithNodeList(obstacle, obstacleListNodes);
            }
            
            if(!hit){
                hit = Util.checkNodeWithNodeList(obstacle, snake2.getListNodes());
            }
            if (!hit) {
                obstacleListNodes.add(obstacle);

                counter++;
            }
            
            
        }

    }

    private void drawObstacles(Graphics g) {

        for (Node obs : obstacleListNodes) {
            Util.drawSquare(g, obs, Color.gray, getSquareWidth(), getSquareHeight());
        }
    }

//Main loop
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!checkCollision(snake1, snake2) && snake1.getIsAlive()) {
            snake1.move();
        }else{
            snake1.die();
        }
        
        if (!checkCollision(snake2, snake1) && snake2.getIsAlive()) {
            snake2.move();
        }else{
            snake2.die();
        }
        
        if(!snake1.getIsAlive() && !snake2.getIsAlive()){
            
            processGameOver();

        }
        
       

        

        if (checkFood(snake1)) {
            if (food != null) {
                snake1.eat(food);
            } else {
                snake1.eat(specialFood);
            }
            createObstacles(1);
            initFood();
            scoreBoard.setScore(scoreBoard.getScore() + 1);

        }
        if (checkFood(snake2)) {
            if (food != null) {
                snake2.eat(food);
            } else {
                snake2.eat(specialFood);
            }
            createObstacles(1);
            initFood();
            scoreBoard.setScore(scoreBoard.getScore() + 1);

        }
            repaint();
    }

    private boolean canMove(DirectionType newDirection, Snake snake) {

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

        super.paintComponent(g);
        drawObstacles(g);
        drawBoard(g);
        snake1.draw(g, getSquareWidth(), getSquareHeight());
        snake2.draw(g, getSquareWidth(), getSquareHeight());
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

    private boolean checkFood(Snake snake) {

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

    private boolean checkCollision(Snake snake, Snake otherSnake) {
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
        Node node = new Node(nextPosRow, nextPosCol, Color.BLACK);
        if (Util.checkNodeWithNodeList(node, obstacleListNodes)) {
            return true;
        }
        if(snake.checkWithItself(nextPosRow, nextPosCol)){
            return true;
        }
        return snake.checkWithOtherSnake(otherSnake,nextPosRow, nextPosCol);
    }

    private void processGameOver() {

        removeKeyListener(keyAdapter1);
        removeKeyListener(keyAdapter2);

        timer.stop();

        System.out.println("Game Over");

        recordScore = new RecordsScore(parentFrame, true, scoreBoard.getScore());

        recordScore.setVisible(true);
        
        
    }

    public void setParentFrame(JFrame parentFrame) {

        this.parentFrame = parentFrame;
    }

}
