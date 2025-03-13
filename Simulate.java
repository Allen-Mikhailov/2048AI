import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.util.HashMap;

class Move
{
    private GameBoard state;
    public double score;
    public int bestMove = -1;

    private static HashMap<String, Move> allMoves = new HashMap<String, Move>();

    private final int maxItt = 3;
    private int itteration;

    private void getBestAction()
    {
        String s = toString();
        Move get = allMoves.get(s);
        if (get != null && get.itteration <= itteration && false)
        {
            // System.out.println("Saved");
            bestMove = get.bestMove;
            return;
        } else {
            allMoves.put(s, this);
        }


        if (itteration == maxItt)
            score = state.score();
        else {
            double highestAverageScore = 0;
            int bestAction = 0;

            for (int i = 0; i < 4; i++)
            {   
                double scoreSum = 0;

                GameBoard c = new GameBoard(state, i);
                int openSpaces = c.getOpenSpaces().size()/2;
                
                if (openSpaces == 0)
                {
                    scoreSum -= c.score();
                    continue;
                }
                

                for (int j = 0; j < openSpaces; j++)
                {
                    Move m = new Move(c, itteration+1, j);
                    scoreSum += m.score; //* (j%2 == 0 ? .9 : .1);
                }

                if (highestAverageScore < scoreSum/openSpaces)
                {
                    highestAverageScore = scoreSum/openSpaces;
                    bestAction = i;
                }
            }

            score = highestAverageScore;
            bestMove = bestAction;
        }
    }
    
    public Move(GameBoard s)
    {
        state = s;
        itteration = 0;
        getBestAction();
    }

    public Move(GameBoard s, int itt, int verse)
    {
        s = new GameBoard(s);
        s.addTile(verse);
        state = s;

        itteration = itt;
        getBestAction();
    }

    public String toString()
    {
        String str = "";
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
            {
                str+= state.board[x][y]+",";
            }
        }
        return str;
    }
}

public class Simulate {

    private GameBoard current;
    private int move = 0;

    public Simulate()
    {
        current = new GameBoard();
    }


    public void draw(Graphics window)
    {
        Move m = new Move(current);
        current.move(m.bestMove);
        current.addTile();
        current.draw(window);

        move++;
        window.drawString(""+move, 600, 100);
    }
}
