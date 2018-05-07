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
            if (players[0].getSnake().getIsTurning()) {
                return;
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (canMove(DirectionType.UP, players[0].getSnake())) {
                        players[0].getSnake().changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (canMove(DirectionType.DOWN, players[0].getSnake())) {
                        players[0].getSnake().changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (canMove(DirectionType.LEFT, players[0].getSnake())) {
                        players[0].getSnake().changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (canMove(DirectionType.RIGHT, players[0].getSnake())) {
                        players[0].getSnake().changeDirection(DirectionType.RIGHT);
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
            if (players[1].getSnake().getIsTurning()) {
                return;
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (canMove(DirectionType.UP, players[1].getSnake())) {
                        players[1].getSnake().changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_S:
                    if (canMove(DirectionType.DOWN, players[1].getSnake())) {
                        players[1].getSnake().changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_A:
                    if (canMove(DirectionType.LEFT, players[1].getSnake())) {
                        players[1].getSnake().changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_D:
                    if (canMove(DirectionType.RIGHT, players[1].getSnake())) {
                        players[1].getSnake().changeDirection(DirectionType.RIGHT);
                    }
                    break;

                default:
                    break;
            }
            repaint();
        }
    }

    class MyKeyAdapter3 extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (players[2].getSnake().getIsTurning()) {
                return;
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_1:
                    if (canMove(DirectionType.UP, players[2].getSnake())) {
                        players[2].getSnake().changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_2:
                    if (canMove(DirectionType.DOWN, players[2].getSnake())) {
                        players[2].getSnake().changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_3:
                    if (canMove(DirectionType.LEFT, players[2].getSnake())) {
                        players[2].getSnake().changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_4:
                    if (canMove(DirectionType.RIGHT, players[2].getSnake())) {
                        players[2].getSnake().changeDirection(DirectionType.RIGHT);
                    }
                    break;

                default:
                    break;
            }
            repaint();
        }
    }

    class MyKeyAdapter4 extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (players[3].getSnake().getIsTurning()) {
                return;
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_5:
                    if (canMove(DirectionType.UP, players[3].getSnake())) {
                        players[3].getSnake().changeDirection(DirectionType.UP);
                    }
                    break;
                case KeyEvent.VK_6:
                    if (canMove(DirectionType.DOWN, players[3].getSnake())) {
                        players[3].getSnake().changeDirection(DirectionType.DOWN);
                    }
                    break;
                case KeyEvent.VK_7:
                    if (canMove(DirectionType.LEFT, players[3].getSnake())) {
                        players[3].getSnake().changeDirection(DirectionType.LEFT);
                    }
                    break;
                case KeyEvent.VK_8:
                    if (canMove(DirectionType.RIGHT, players[3].getSnake())) {
                        players[3].getSnake().changeDirection(DirectionType.RIGHT);
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
    private Snake[] snakes;
    private int deltaTime;
    private Food food;
    private SpecialFood specialFood;
    private Timer timer;
    private Timer specialFoodTimer;
    private ArrayList<Node> obstacleListNodes;
    private RecordsScore recordScore;
    private JFrame parentFrame;
    private KeyAdapter[] keyAdapter;
    private PlayerSelection playerSelection;
    private Player[] players;
    private ScoreBoard[] scoreBoard;
    private Game game;

    public Board() {
        super();
        scoreBoard = null;
        players = null;
        initKeyAdapters();
        initVariables();

    }

    public void setScoreBoard(ScoreBoard... scoreBoard) {

        this.scoreBoard = scoreBoard;
        setPlayers(scoreBoard.length);
    }

    public void setPlayerSelection(Game game) {
        playerSelection = new PlayerSelection(parentFrame, true, game);
        playerSelection.setVisible(true);
    }

    private void initVariables() {

        obstacleListNodes = new ArrayList<Node>();
        deltaTime = 300;
        initFood();
        timer = new Timer(deltaTime, this);
        setFocusable(true);
        recordScore = null;
        parentFrame = null;
    }

    public void InitGame() {
        initVariables();
        timer.start();
        setFocusable(true);
        initFood();

    }

    private void initKeyAdapters() {
        keyAdapter = new KeyAdapter[4];
        keyAdapter[0] = new MyKeyAdapter();
        keyAdapter[1] = new MyKeyAdapter2();
        keyAdapter[2] = new MyKeyAdapter3();
        keyAdapter[3] = new MyKeyAdapter4();
    }

    private void initFood() {

        if (scoreBoard != null && snakes != null) {
            if (checkSpecialFood()) {
                food = null;
                specialFood = new SpecialFood(obstacleListNodes, snakes);
                specialFoodTimer = new Timer(specialFood.getVisibleTime() * 1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        specialFood = null;
                        food = new Food(obstacleListNodes, snakes);
                        specialFoodTimer.stop();

                    }
                });

                specialFoodTimer.start();
            } else {
                if (specialFoodTimer != null && specialFoodTimer.isRunning()) {
                    specialFoodTimer.stop();
                }
                food = new Food(obstacleListNodes, snakes);
                specialFood = null;
            }
        }
    }

    private boolean checkSpecialFood() {

        for (int i = 0; i < scoreBoard.length; i++) {
            if (scoreBoard[i].getScore() > 0 && scoreBoard[i].getScore() % 5 == 0) {
                return true;
            }
        }

        return false;
    }

    private void createObstacles(int nObstacles) {

        boolean hit;
        int counter = 0;
        while (counter < nObstacles) {
            hit = false;
            int row = (int) (Math.random() * NUM_ROW);
            int col = (int) (Math.random() * NUM_COL);

            Node obstacle = new Node(row, col, Color.gray);
            for (Snake s : snakes) {
                if (!hit) {
                    hit = Util.checkNodeWithNodeList(obstacle, s.getListNodes());
                }
            }
            if (!hit) {
                hit = Util.checkNodeWithNodeList(obstacle, obstacleListNodes);
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

        for (Player p : players) {
            checkPlayer(p);

        }
        repaint();
    }

    private void checkPlayer(Player player) {

        player.getSnake().setIsTurning(false);
        if (checkDeath()) {

            processGameOver();

        }

        if (!checkCollision(player.getSnake(), snakes) && player.getSnake().getIsAlive()) {
            player.getSnake().move();
        } else {
            player.getSnake().die();
        }

        if (checkFood(player.getSnake())) {
            if (food != null) {
                player.getSnake().eat(food);
            } else {
                player.getSnake().eat(specialFood);
            }
            createObstacles(1);
            initFood();
            player.getScoreBoard().setScore(player.getScoreBoard().getScore() + 1);
        }

    }

    private boolean checkDeath() {

        for (Snake s : snakes) {
            if (s.getIsAlive()) {
                return false;
            }
        }

        return true;
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
        if (snakes != null) {
            for (int i = 0; i < snakes.length; i++) {
                snakes[i].draw(g, getSquareWidth(), getSquareHeight());
            }
        }
        if (food != null) {
            food.draw(g, getSquareWidth(), getSquareHeight());
        }

        if (specialFood
                != null) {
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

    private boolean checkCollision(Snake snake, Snake[] otherSnakes) {
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

        if (snake.checkWithItself(nextPosRow, nextPosCol)) {

            return true;
        }

        return snake.checkWithOtherSnake(otherSnakes, nextPosRow, nextPosCol);
    }

    private void processGameOver() {

        setScoreRecord();

        for (int i = 0; i < players.length; i++) {
            removeKeyListener(keyAdapter[i]);

        }

        timer.stop();

        System.out.println("Game Over");

        //recordScore = new RecordsScore(parentFrame, true, scoreBoard.getScore());
        //recordScore.setVisible(true);
    }

    public void setParentFrame(JFrame parentFrame) {

        this.parentFrame = parentFrame;
    }

    private void setScoreRecord() {

        recordScore = new RecordsScore(parentFrame, true, getGreatestScore().getScore(), "Player 1");

        recordScore.setVisible(true);

    }

    private ScoreBoard getGreatestScore() {
        ScoreBoard maxScore = scoreBoard[0];
        for (int i = 1; i < scoreBoard.length; i++) {
            if (scoreBoard[i].getScore() > maxScore.getScore()) {
                maxScore = scoreBoard[i];

            }

        }
        return maxScore;
    }

    public void setPlayers(int numPlayers) {
        players = new Player[numPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(new Snake(Snake.SNAKE_COLORS[i], Snake.INIT_NODE[i], Snake.SNAKE_INIT_DIRECTION[i], i), scoreBoard[i], keyAdapter[i]);
        }
        System.out.println(numPlayers);
        
        snakes = new Snake[players.length];
        for (int i = 0; i < players.length; i++) {

            snakes[i] = players[i].getSnake();

        }

        createObstacles(4);
        food = new Food(obstacleListNodes, snakes);

        for (int i = 0; i < players.length; i++) {

            addKeyListener(keyAdapter[i]);
        }
    }

}
