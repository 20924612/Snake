/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;

/**
 *
 * @author alu20924612v
 */
public class Node {

    private int row;
    private int col;
    private Color color;
    
     public Node(int row, int col) {

        this.row = row;
        this.col = col;
     color= Color.BLACK;
    }
    

    public Node(int row, int col, Color color) {

        this.row = row;
        this.col = col;
        this.color=color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    public void setColor(Color color){
        this.color=color;
    }
    
    public Color getColor(){
        return color;
    }
}
