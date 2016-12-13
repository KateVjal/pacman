import java.awt.event.KeyEvent;

public class Facade {
    private Level level;
    private Game game;
    public int getLevelY () {
        return level.getY();
    }

    public int getLevelX ()  {
        return level.getY();
    }

    public GameStatus getGameStatus() {
        return level.getGameStatus();
    }
    public int[][] getLevelMap () {
        return level.getLevel();
    }

    public int getScore() {
        return level.getScore();
    }

    public void pacmanGoUP () {
        level.pacmanStep(KeyEvent.VK_UP);
    }

    public void pacmanGoDown () {
        level.pacmanStep(KeyEvent.VK_DOWN);
    }

    public void pacmanGoLeft () {
        level.pacmanStep(KeyEvent.VK_LEFT);
    }

    public void pacmanGoRight () {
        level.pacmanStep(KeyEvent.VK_RIGHT);
    }

    public void restartGame () {
        game.restartGame();
    }

    public void startGame () {
        game.startGame();
    }

    public Facade(Level level, Game game) {
        this.level = level;
        this.game = game;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
