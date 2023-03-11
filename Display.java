import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.lang.Thread;

public class Display extends Canvas
{
    private BufferedImage back;
    private Simulate sim;

    public Display()
    {
        setBackground(Color.WHITE);
		setVisible(true);

        sim = new Simulate();
		
		// new Thread(this).start();
    }

    public void update(Graphics window){
        paint(window);
    }

    public void paint(Graphics window)
    {
        Graphics2D twoDGraph = (Graphics2D)window;

        if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));

        Graphics graphToBack = back.createGraphics();

        // Draw here
        sim.draw(graphToBack);

        twoDGraph.drawImage(back, null, 0, 0);
    }
}
