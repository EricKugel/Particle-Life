import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int NUMBER_OF_PARTICLES = 200;
    public static final int FPS = 48;

    private JPanel main;

    private ArrayList<Particle> particles = new ArrayList<Particle>();
    public static final Colors colors = new Colors("color_grid.csv");

    public Main() {
        setTitle("Particle Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        initParticles();
        initGUI();
        pack();
    }

    private void initGUI() {
        main = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };
        main.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setContentPane(main);

        Timer timer = new Timer(1000 / FPS, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
                main.repaint();
            }
        });
        timer.start();
    }

    private void initParticles() {
        for (int i = 0; i < NUMBER_OF_PARTICLES; i += 1) {
            double x = Math.random() * WIDTH;
            double y = Math.random() * HEIGHT;
            Object[] keySet;
            String colorString = (String) (keySet = Colors.COLOR_DEFINITIONS.keySet().toArray())[(int) (Math.random() * keySet.length)];
            Particle particle = new Particle(new V(x, y), new V(Math.random() * 4 - 2, Math.random() * 4 - 2), colorString);
            particles.add(particle);
        }
    }

    private void update() {
        for (Particle particle : particles) {
            particle.update(particles);
        }
    }

    private void draw(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for (Particle particle : particles) {
            particle.draw(g);
        }
    }

    public static void main(String[] arg0) {
        new Main();
    }
}