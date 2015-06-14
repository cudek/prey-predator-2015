package pl.edu.agh.preypredator.visualizaton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.creature.Animal;
import environment.IEnvironment;

/**
 * 
 * @author cudek
 */

public class Visualizer {

    protected JFrame mainFrame;
    protected DrawingBox ppVisualization;
    private int tileSize = 25;
    private long delay = 1;

    public void initialze(IEnvironment env) {
        MeadowEnvironment meadow = (MeadowEnvironment) env;
        ppVisualization = new DrawingBox(meadow, tileSize);
        mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(meadow.getDimension().getWidth() * tileSize, meadow.getDimension().getHeight()
            * tileSize));
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Simulation exited.");
                System.exit(0);
            }
        });
        mainFrame.add(ppVisualization);
        mainFrame.pack();
        mainFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - (mainFrame.getWidth() / 2)) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - mainFrame.getHeight()) / 2);
        mainFrame.setVisible(true);
    }

    public void step(IEnvironment env) {
        ppVisualization.paint(ppVisualization.getGraphics());
        try {
            Thread.sleep(delay);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

}

/**
 * 
 * Klasa zawierająca metody do rysowania obecnego stanu środowiska P&P
 */

class DrawingBox extends JPanel {
    MeadowEnvironment meadow;
    int tileSize;

    public DrawingBox(IEnvironment env, int tileSize) {
        this.tileSize = tileSize;
        meadow = (MeadowEnvironment) env;
        setBackground(Color.green);
        // @formatter:off
        setPreferredSize(new Dimension(meadow.getDimension().getWidth()*tileSize,
                meadow.getDimension().getHeight()*tileSize));
        setSize(meadow.getDimension().getWidth() * tileSize + 50,
                meadow.getDimension().getHeight() * tileSize + 50);
        // @formatter:on
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.drawRect(0, 0, tileSize * meadow.getDimension().getWidth(), tileSize * meadow.getDimension().getHeight());
        for (Animal animal : meadow.getWolfs()) {
            g.setColor(Color.gray);
            g.fillRect(animal.getPosition().getX() * tileSize, animal.getPosition().getY() * tileSize, tileSize,
                tileSize);
        }
        for (Animal animal : meadow.getSheeps()) {
            g.setColor(Color.white);
            g.fillRect(animal.getPosition().getX() * tileSize, animal.getPosition().getY() * tileSize, tileSize,
                tileSize);
        }
    }
}
