import java.util.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

import java.awt.Rectangle;
import java.lang.reflect.Array;

public class GameBoard {
    private int[][] board;
    private int size = 4;

    public ArrayList<Integer> getOpenSpaces()
    {
        ArrayList<Integer> openSpaces = new ArrayList<Integer>();

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                if (board[y][x] == 0)
                {
                    openSpaces.add(y);
                    openSpaces.add(x);
                }
            }
        }
        return openSpaces;
    }

    public boolean addTile()
    {
        ArrayList<Integer> openSpaces = getOpenSpaces();

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

    public void addTile(int verse)
    {
        ArrayList<Integer> openSpaces = getOpenSpaces();

        int spaceIndex = verse/2;

        int newTile = 2;
        if (verse%2 == 1)
            newTile = 4;
        
        board[openSpaces.get(spaceIndex*2)][openSpaces.get(spaceIndex*2+1)] = newTile;
    }

    public double score()
    {
        final double maxHypot = Math.hypot(size, size);
        double s = 0;

        // Summing
        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                // Using power so that combinations are better
                double baseScore = Math.pow(board[y][x], 1.5);

                // Bottom right advanatge

                baseScore *= 1 + Math.hypot(x+1, y+1) / maxHypot;

                s += baseScore;
            }
        }


        return s;
    }

    public GameBoard()
    {
        board = new int[4][4];
        addTile();
        addTile();
    }

    // 0 is right, 1 is left, 2 is up, 3 is down
    public int shift(int direction)
    {
        int changes = 1;

        switch (direction)
        {
            // Right
            case 0:
                for (int y = 0; y < size; y++)
                {
                    for (int x = size-2; x  > -1; x--)
                    {
                        if (board[y][x] != 0)  
                        {
                            int place = x;
                            while (place+1 < size && board[y][place+1] == 0)
                                place++;
                            
                            int temp = board[y][x];
                            board[y][x] = 0;
                            board[y][place] = temp;
                        }
                    }
                }
                break;
            
            // Left
            case 1:
                for (int y = 0; y < size; y++)
                {
                    for (int x = 1; x  < size; x++)
                    {
                        if (board[y][x] != 0)  
                        {
                            int place = x;
                            while (place+1 < size && board[y][place-1] == 0)
                                place--;
                            
                            int temp = board[y][x];
                            board[y][x] = 0;
                            board[y][place] = temp;
                        }
                    }
                }
                break;

            // Down
            case 2:
                for (int x = 0; x < size; x++)
                {
                    for (int y = size-2; y  > -1; y--)
                    {
                        if (board[y][x] != 0)  
                        {
                            int place = y;
                            while (place+1 < size && board[place+1][x] == 0)
                                place++;
                            
                            int temp = board[y][x];
                            board[y][x] = 0;
                            board[place][x] = temp;
                        }
                    }
                }
                break;

            // Up
            case 3:
                for (int x = 0; x < size; x++)
                {
                    for (int y = 1; y  < size; y++)
                    {
                        if (board[y][x] != 0)  
                        {
                            int place = y;
                            while (place+1 < size && board[place-1][x] == 0)
                                place--;
                            
                            int temp = board[y][x];
                            board[y][x] = 0;
                            board[place][x] = temp;
                        }
                    }
                }
                break;
        }

        return changes;
    }

    public void combine(int direction)
    {
        switch (direction)
        {
            // Right
            case 0:
                for (int y = 0; y < size; y++)
                {
                    for (int x = size-2; x  > -1; x--)
                    {
                        if (board[y][x] == board[y][x+1])
                        {
                            board[y][x+1] *= 2;
                            board[y][x] = 0;
                        }
                    }
                }
                break;
            
            // Left
            case 1:
                for (int y = 0; y < size; y++)
                {
                    for (int x = 1; x  < size; x++)
                    {
                        if (board[y][x] == board[y][x-1])
                        {
                            board[y][x-1] *= 2;
                            board[y][x] = 0;
                        }
                    }
                }
                break;

            // Down
            case 2:
                for (int x = 0; x < size; x++)
                {
                    for (int y = size-2; y  > -1; y--)
                    {
                        if (board[y][x] == board[y+1][x])
                        {
                            board[y+1][x] *= 2;
                            board[y][x] = 0;
                        }
                    }
                }
                break;
            
            // Up
            case 3:
                for (int x = 0; x < size; x++)
                {
                    for (int y = 1; y  < size; y++)
                    {
                        if (board[y][x] == board[y-1][x])
                        {
                            board[y-1][x] *= 2;
                            board[y][x] = 0;
                        }
                    }
                }
                break;
        }
    }

    public boolean move(int direction)
    {
        int changes = 1;
        shift(direction);
        combine(direction);
        shift(direction);

        return changes == 0;
    }

    public GameBoard(GameBoard parent, int direction, int verse)
    {
        board = parent.board.clone();
        size = parent.size;
        addTile(verse);
    }

    public void draw(Graphics window)
    {
        window.setColor(Color.black);
        int rsize = Math.min(Runner.WIDTH-100, Runner.HEIGHT-100);

        double tileSize = (double) rsize / size;

        for (int y = 0; y < size; y++)
        {
            for (int x = 0; x < size; x++)
            {
                window.drawRect((int)(x*tileSize), (int)(y*tileSize), (int) tileSize, (int) tileSize);

                FontMetrics fontSize = window.getFontMetrics();

                int fontX = (int)((x+.5)*tileSize) - fontSize.stringWidth(Integer.toString(board[y][x]))/2;

                int fontY = (int)((y+.5)*tileSize) + fontSize.getHeight()/4;

                window.drawString(Integer.toString(board[y][x]), fontX, fontY);
            }
        }
    }
}
