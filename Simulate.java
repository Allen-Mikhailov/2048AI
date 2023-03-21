import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

class Move
{
    private GameBoard state;
    public double score;
    public int bestMove;

    private final int maxItt = 2;
    private int itteration;

    private void getBestAction()
    {
        if (itteration == maxItt)
            score = state.score();
        else {
            double highestAverageScore = 0;
            int bestAction = 0;

            for (int i = 0; i < 4; i++)
            {   
                double scoreSum = 0;

                GameBoard c = new GameBoard(state, i);
                int openSpaces = c.getOpenSpaces().size();

                for (int j = 0; j < openSpaces*2; j++)
                {
                    Move m = new Move(c, itteration+1, j);
                    scoreSum += m.score * (j%2 == 0 ? .9 : .1);
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
}

public class Simulate {

    private GameBoard current;

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
    }
}
