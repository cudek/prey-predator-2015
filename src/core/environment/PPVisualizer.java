
package core.environment;

import core.environment.agent.Animal;
import core.simulation.Environment;
import core.visualization.Visualizer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.tools.JavaFileManager.Location;

/**
 *
 * @author cudek
 */

/**
 *
 * Klasa zawierająca metody do rysowania obecnego stanu środowiska P&P
 */

class DrawingBox extends JPanel{
    Meadow meadow;
    int tileSize;

    public DrawingBox(Environment env, int tileSize){
        this.tileSize = tileSize;
        meadow = (Meadow)env;
        Meadow meadow = (Meadow)env;
        setBackground(Color.green);
        setPreferredSize(new Dimension(meadow.getWidth()*tileSize,
                meadow.getLength()*tileSize));
        setSize(meadow.getWidth() * tileSize + 50,
                meadow.getLength() * tileSize + 50);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.black);
        g.drawRect(0, 0,
                tileSize*meadow.getWidth(), tileSize*meadow.getLength());
        for (Animal animal : meadow.getAnimals()){
            if (!animal.isPrey())
                g.setColor(Color.gray);
            else
                g.setColor(Color.white);
            g.fillRect(animal.getPoint().getX() * tileSize,
                    animal.getPoint().getY() * tileSize, tileSize, tileSize);
        }
    }
}

public class PPVisualizer extends Visualizer {

    protected JFrame mainFrame;
    protected DrawingBox ppVisualization;
    int tileSize = 5;

    @Override
    public void initialze(Environment env) {
        Meadow meadow= (Meadow)env;
        ppVisualization = new DrawingBox(meadow, tileSize);
        mainFrame = new JFrame();
        mainFrame.setSize(new Dimension(meadow.getWidth()*tileSize,
                meadow.getLength()*tileSize));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Simulation exited.");
                System.exit(0);
            }
        });
        mainFrame.add(ppVisualization);
        mainFrame.pack();
        mainFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width
                - (mainFrame.getWidth()/2)) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height
                - mainFrame.getHeight()) / 2);
        mainFrame.setVisible(true);          
    }


    @Override
    public void step(Environment env) {
        super.step(env);
        ppVisualization.paint(ppVisualization.getGraphics());
    }

}
