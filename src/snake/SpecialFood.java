/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author alu20924612v
 */
public class SpecialFood extends Food{
    
    private int visibleTime;
    
    public SpecialFood(Snake snake){
        super(snake);
        visibleTime=10;
    }
    
    public int getVisibleTime(){
        return visibleTime;
    }
    
     public void draw(Graphics g, int squareWidth, int squareHeight){
        
        Util.drawSquare(g, super.getPosition(), Color.pink, squareWidth, squareHeight);
        
    }
}

