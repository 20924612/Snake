/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javax.swing.JLabel;

/**
 *
 * @author alu20924612v
 */
public class ScoreBoard extends JLabel {

    private int score;

    public ScoreBoard() {
        super();
        score = 0;
        setTextScore();
    }

    private void setTextScore() {
        setText("Score: " + score);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {

        this.score = score;
        setTextScore();
    }

}
