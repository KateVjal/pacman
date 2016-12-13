import java.util.ArrayList;

public class Core {
    private Level level;
    private ArrayList<Monster> monsters;

    public Core (Level level, ArrayList<Monster> monsters) {
        this.level = level;
        this.monsters = monsters;
    }

    public void moveMonsters() {
        final Thread thread = new Thread() {
            public void run() {
                while (true) {
                   try {
                        for (Monster monster : monsters) {
                            level = MapAssistant.moveMonster(level, monster);
                            if (level.getGameStatus() != GameStatus.CONTINUE) {
                                interrupt();
                                return;
                            }
                        }
                        sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    public Level getLevel () {
        return level;
    }
}
