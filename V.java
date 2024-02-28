public class V {
    public double x;
    public double y;
    private double length = -1;

    public V(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        if (this.length == -1) {
            this.length = Math.sqrt(this.x * this.x + this.y * this.y);
        }
        return this.length;
    }

    public V mult(double scalar) {
        return new V(this.x * scalar, this.y * scalar);
    }

    public V add(V other) {
        return new V(this.x + other.x, this.y + other.y);
    }

    public V sub(V other) {
        return this.add(other.mult(-1));
    }

    public V normalized() {
        return mult(1 / length());
    }

    @Override
    public boolean equals(Object other) {
        V other_v = (V) other;
        return this.x == other_v.x && this.y == other_v.y;
    }

    public static double lerp(double x1, double x2, double t) {
        return (x2 - x1) * t + x1;
    }

    public V clamp(V lower, V upper) {
        return new V(Math.min(Math.max(this.x, lower.x), upper.x), Math.min(Math.max(this.y, lower.y), upper.y));
    }
}
