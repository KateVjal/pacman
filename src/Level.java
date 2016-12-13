import java.awt.event.KeyEvent;

public class Level {
    int x = 19;
    int y = 19;
    GameStatus gs = GameStatus.CONTINUE;
    int score = 0;
    int maxS = 0;

    public int getScore() {
        return score;
    }

    public GameStatus getGameStatus() {
        return gs;
    }

    int[][] level = {
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 2, 2, 0, 2, 2, 0, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 2, 2, 0, 2, 2, 0, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 2, 2},
            {2, 2, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 2, 2},
            {6, 1, 0, 0, 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0, 0, 0, 1, 5},
            {2, 2, 0, 2, 2, 2, 2, 0, 1, 4, 1, 0, 2, 2, 2, 2, 0, 2, 2},
            {2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 2, 2},
            {2, 2, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 2, 2},
            {2, 2, 0, 2, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 0, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 2, 2, 0, 2, 2, 0, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 2, 2, 2, 0, 2, 2, 0, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
    };

    public int[][] getLevel() {
        return level;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
//поиск наибольшего числа очков
    public int maxS() {
        for (int m = 0; m < getX(); m++) {
            for (int n = 0; n < getY(); n++) {
                if (getLevel()[m][n] == 0) {
                    maxS = maxS + 10;
                }
            }
        }
        return maxS;
    }
//движение пэкмена
    public Level pacmanStep(int oldX, int oldY, int newX, int newY) {
        if (level[newY][newX] == 4 || level[newY][newX] == 3 || level[newY][newX] >= 9) {
            level[oldY][oldX] = 1;
            gs = GameStatus.LOOSE;
            return this;
        }
//телепорт
        if (level[newY][newX] == 6) {
            level[8][17] = 8;
            level[oldY][oldX] = 1;
            level[8][0] = 6;
            return this;
        }

        if (level[newY][newX] == 5) {
            level[8][1] = 8;
            level[oldY][oldX] = 1;
            level[8][18] = 5;
            return this;
        }
//добавляем очки
        if (level[newY][newX] != 2) {
            if (level[newY][newX] == 0) {
                score += 10;
                if (score == maxS) {
                    level[newY][newX] = 8;
                    level[oldY][oldX] = 1;
                    gs = GameStatus.WIN;
                    return this;
                }

            }
            level[newY][newX] = 8;
            level[oldY][oldX] = 1;
        }
        return this;
    }

    //ищем пэкмена и перемещаем
    public Level pacmanStep(int type) {

        int pacmanX = 0;
        int pacmanY = 0;
        for (int m = 0; m < y; m++) {
            for (int n = 0; n < x; n++) {
                if (level[m][n] == 8) {
                    pacmanX = n;
                    pacmanY = m;
                }
            }
        }

        switch (type) {
            case KeyEvent.VK_LEFT: {  //лево
                return pacmanStep(pacmanX, pacmanY, pacmanX - 1, pacmanY);
            }
            case KeyEvent.VK_RIGHT: { //право
                return pacmanStep(pacmanX, pacmanY, pacmanX + 1, pacmanY);
            }
            case KeyEvent.VK_UP: {//вверх
                return pacmanStep(pacmanX, pacmanY, pacmanX, pacmanY - 1);
            }
            case KeyEvent.VK_DOWN: {//низ
                return pacmanStep(pacmanX, pacmanY, pacmanX, pacmanY + 1);
            }
        }
        return this;
    }
//движение монстров
    public Level monsterStep(int oldX, int oldY, int newX, int newY, int monsterID) {
        int pmX = -1;
        int pmY = -1;
        for (int m = 0; m < y; m++) {
            for (int n = 0; n < x; n++) {
                if (level[m][n] == 8) {
                    pmY = m;
                    pmX = n;
                }
            }
        }
        if (level[oldY][oldX] == 9) {
            level[oldY][oldX] = 0;
        } else {
            if (level[oldY][oldX] == 10) {
                level[oldY][oldX] = 0;
            } else {
                level[oldY][oldX] = 1;
            }
        }

        if (newX == pmX && newY == pmY) {
            gs = GameStatus.LOOSE;
            level[newY][newX] = monsterID;
            return this;
        }

        if (level[newY][newX] == 0) {
            level[newY][newX] = monsterID + 6;
        } else {
            level[newY][newX] = monsterID;
        }
        return this;
    }
}
