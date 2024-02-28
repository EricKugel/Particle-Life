import java.awt.*;
import java.util.ArrayList;

public class Particle {
    public static final double ATTRACTING_FACTOR = .5;
    public static final double R_MIN = 6;
    public static final double R_MAX = 400;
    public static final double R_RANGE = R_MAX - R_MIN;
    public static final int SIZE = 6;
    public static final int MAX_SPEED = 4;

    public V position;
    public V velocity;
    public Color color;
    public String colorString;

    public Particle(V position, V velocity, String colorString) {
        this.position = position;
        this.velocity = velocity;
        this.colorString = colorString;
        this.color = Colors.COLOR_DEFINITIONS.get(colorString);
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect((int) this.position.x - SIZE / 2, (int) this.position.y - SIZE / 2, SIZE, SIZE);
    }

    public void update(ArrayList<Particle> particles) {
        if (this.velocity.length() > MAX_SPEED) {
            this.velocity = this.velocity.normalized().mult(MAX_SPEED);
        }
        this.position = this.position.add(this.velocity);
        this.position = this.position.clamp(new V(0, 0), new V(Main.WIDTH, Main.HEIGHT));

        for (Particle other : particles) {
            if (this == other) continue;
            interact(other.position, 1 * Main.colors.get(this.colorString, other.colorString));
        }

        repelWalls(new V(this.position.x, 0));
        repelWalls(new V(0, this.position.y));
        repelWalls(new V(this.position.x, Main.HEIGHT));
        repelWalls(new V(Main.WIDTH, this.position.y));
    }

    private void repelWalls(V point) {
        V difference = this.position.sub(point);
        double distance = Math.max(.1, difference.length());
        if (distance > R_MIN) return;
        if (point.x == 0) {
            feelForce(new V(1, 0), 1 / distance);
        } else if (point.x == Main.WIDTH) {
            feelForce(new V(-1, 0), 1 / distance);
        } else if (point.y == 0) {
            feelForce(new V(0, 1), 1 / distance);
        } else {
            feelForce(new V(0, -1), 1 / distance);
        }
    }

    private void feelForce(V direction, double force) {
        this.velocity = this.velocity.add(direction.normalized().mult(force));
    }

    public void genericRepel(V point) {
        V difference = this.position.sub(point);
        double distance = Math.max(.1, difference.length());
        if (distance < R_MIN) {
            this.velocity = this.velocity.add(difference.normalized().mult(Main.NUMBER_OF_PARTICLES / distance));
        }
    }

    public void interact(V point, double factor) {
        V difference = this.position.sub(point);
        double distance = Math.max(0.01, difference.length());
        if (distance > R_MIN && distance < R_MAX) {
            boolean positive_slope = distance < R_RANGE / 2;
            double t = (distance / (R_RANGE / 2)) % 1;
            double force = factor * (positive_slope ? t : 1 - t);
            this.velocity = this.velocity.add(difference.normalized().mult(force));
        } else if (distance < R_MIN) {
            genericRepel(point);
        }
    }
}
