import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

import java.awt.Rectangle;

public class GameBoard {
    private int[][] board;
    private int size = 4;

    private boolean addTile()
    {
        ArrayList<Integer> openSpaces = new ArrayList<Integer>();

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                if (board[y][x] == 0)
                    openSpaces.add(y);
                    openSpaces.add(x);
            }
        }

        if (openSpaces.size() > 0)
        {
            // Picking tile
            int tile = 2;
            if (Math.random() > .9)
                tile = 4;

            int index = (int) (Math.random()*openSpaces.size()/2)*2;

            board[openSpaces.get(index)][openSpaces.get(index+1)] = tile;
        }

        return false;
    }

    public GameBoard()
    {
        board = new int[4][4];
        addTile();
        addTile();
    }

    public void draw(Graphics window)
    {
        window.setColor(Color.black);
        int rsize = Math.min(Runner.WIDTH-100, Runner.HEIGHT-100);

        double tileSize = (double) rsize / size;

        System.out.println(tileSize);

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                window.drawRect((int)(x*tileSize), (int)(y*tileSize), (int) tileSize, (int) tileSize);
                window.drawString(Integer.toString(board[y][x]), (int)(x*tileSize), (int)(y*tileSize));
            }
        }
    }
}
