import java.util.ArrayList;
import java.util.List;

public class MapAssistant {
    public static Level moveMonster (Level level, Monster monster) {
        class StepNode {
            int x;
            int y;

            public StepNode(int monsterX, int monsterY) {
                x = monsterX;
                y = monsterY;
            }
        }

        int monsterX = monster.getX();
        int monsterY = monster.getY();

        int pacmanX = 0;
        int pacmanY = 0;

        for(int m = 0; m < level.getY(); m++) {
            for(int n = 0; n < level.getX(); n++) {
                if (level.getLevel()[m][n] == 8) {
                    pacmanX = n;
                    pacmanY = m;
                }
            }
        }
//ищем наименьшую траекторию
        double minDist = Double.MAX_VALUE;
        StepNode step = new StepNode(monsterX,monsterY);
        List<StepNode> directions= new ArrayList<>();
        directions.add(new StepNode(-1, 0));
        directions.add(new StepNode(1, 0));
        directions.add(new StepNode(0, -1));
        directions.add(new StepNode(0, 1));
        for (StepNode direction: directions) {
            int newX = monsterX + direction.x;
            int newY = monsterY + direction.y;

            if (level.getLevel()[newY][newX] == 8) {
                step = new StepNode(newX, newY);
                break;
            }

            if (level.getLevel()[newY][newX] != 0 && level.getLevel()[newY][newX] != 1)
                continue;
            double dist = calcDict(newX, newY, pacmanX, pacmanY);
            if (dist < minDist) {
                minDist = dist;
                step = new StepNode(newX, newY);
            }
        }
        Level lvl = level.monsterStep(monsterX, monsterY, step.x, step.y, monster.getId());
        monster.move(step.x, step.y);
        return lvl;
    }
//считаем дистанцию
    private static double calcDict(int monsterX, int monsterY, int pacmanX, int pacmanY) {
        return Math.sqrt(Math.pow(monsterX-pacmanX, 2) + Math.pow(monsterY-pacmanY, 2));
    }
}
