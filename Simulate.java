import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

public class Simulate {

    private GameBoard current;

    public Simulate()
    {
        current = new GameBoard();
    }


    public void draw(Graphics window)
    {
        current.draw(window);
    }
}
