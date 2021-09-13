package core;

public class Position {

    public static int PROXIMITY_RANGE = 5;
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public int intX(){
        return (int)Math.round(x);
    }

    public int intY(){
        return (int)Math.round(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void apply(Movement movement) {
        Vector2D vector = movement.getVector();
        x += vector.getX();
        y += vector.getY();
    }

    public boolean isInRangeOf(Position position) {
        return Math.abs(x - position.getX()) < Position.PROXIMITY_RANGE && Math.abs(y - position.getY()) < Position.PROXIMITY_RANGE;
    }
}
