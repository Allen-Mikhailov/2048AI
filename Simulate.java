import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

class Move
{
    private GameBoard state;
    public double score;
    private int bestMove;

    private final int maxItt = 5;
    
    public Move(GameBoard s, int dir, int itt, int verse)
    {

        state = s;
        s.move(dir);
        s.addTile(verse);

        if (itt == maxItt)
            score = s.score();
        else {
            int highestAverageScore = 0;
            int bestAction = 0;

            for (int i = 0; i < 4; i++)
            {
                
            }

            score = highestAverageScore;
            bestMove = bestAction;
        }
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
        
        current.move(0);
        current.addTile();
        current.draw(window);
    }
}
