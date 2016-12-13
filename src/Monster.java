public class Monster {

    private int x;
    private int y;
    private int id;

    public Monster (int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void move (int newX, int newY) {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}
