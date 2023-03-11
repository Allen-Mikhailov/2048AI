import javax.swing.JFrame;
import java.awt.Component;

public class Runner extends JFrame {

    public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

    public Runner()
    {
        setSize(WIDTH,HEIGHT);

        Display sim = new Display();

        ((Component)sim).setFocusable(true);
		getContentPane().add(sim);

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Runner run = new Runner();
    }
}
