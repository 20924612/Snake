/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author alu20924612v
 */
public class Util {

    public static void drawSquare(Graphics g, Node node, Color color, int squareWidth, int squareHeight) {
        int x = node.getCol() * squareWidth;
        int y = node.getRow() * squareHeight;
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth - 2,
                squareHeight - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight - 1, x, y);
        g.drawLine(x, y, x + squareWidth - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight - 1,
                x + squareWidth - 1, y + squareHeight - 1);
        g.drawLine(x + squareWidth - 1,
                y + squareHeight - 1,
                x + squareWidth - 1, y + 1);
    }

    public static boolean checkNodeWithNodeList(Node node, ArrayList<Node> nodeList) {

        for (Node n : nodeList) {
            if (node.getRow() == n.getRow() && n.getCol() == node.getCol()) {
                return true;
            }
        }

        return false;
    }
}
