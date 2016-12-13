import java.util.ArrayList;

public class Game {

    private Core core;
    private MainWindow mainWindow;
    private Thread thread;
    private Level level;
    private Facade facade;
    private boolean graph;

    public Game (boolean graph) {
        this.graph = graph;
        level = new Level();
        Monster monster1 = new Monster(9, 8, 3);
        Monster monster2 = new Monster(9, 9, 4);
        ArrayList<Monster> monsters = new ArrayList<>();
        monsters.add(monster1);
        monsters.add(monster2);
        level.maxS=level.maxS();
        facade = new Facade(level, this);
        if (graph) {
            mainWindow = new MainWindow(facade);
            mainWindow.setVisible(true);
        }
        core = new Core(level, monsters);
    }
//обновляем карту
    public void startGame () {
        core.moveMonsters();
        thread = new Thread() {
            public void run() {
                while (! Thread.currentThread().isInterrupted()) {
                    try {
                        level = core.getLevel();
                        facade.setLevel(level);
                        if (graph) {
                            mainWindow.fillMap(facade, false);

                        }
                        if(level.getGameStatus()!= GameStatus.CONTINUE) {
                            interrupt();
                            return;
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

    public Game restartGame () {
        thread.interrupt();
        Game game = new Game(true);
        return game;
    }

}
