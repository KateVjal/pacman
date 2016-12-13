import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.*;


public class MainWindow extends JFrame {
    private JPanel[][] panelHolder;
    private Facade facade;
    private String name;
    private boolean keyReleased = true;
    private boolean gameStart = false;
    private JPanel panel1;
    private JLabel label1;
    private JButton startButton;
    private JButton exitButton;
    private JLabel label2;
    private JTextField scoreTextField;
    private BufferedImage dotPicture;
    private BufferedImage flPicture;
    private BufferedImage wallPicture;
    private BufferedImage mob1_lPicture;
    private BufferedImage mob2_rP;
    private BufferedImage fl_rP;
    private BufferedImage fl_lP;
    private BufferedImage b_dotP;
    private BufferedImage pmP;
    BufferedImage mob1_rPicture;
    BufferedImage mob2_lP;
    BufferedImage mons;
    BufferedImage pm2;
    private java.util.List<Pair<String, Integer>> tableOfResult;

    public MainWindow(Facade facade) {
        //считываем таблицу из файла
        tableOfResult = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("res/results.txt"));
            String line;
            java.util.List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            for (String l : lines) {
                String[] buf = l.split(" ");
                tableOfResult.add(new Pair(buf[0], new Integer(buf[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();//инициализация графики
        this.facade = facade;
        panelHolder = new JPanel[facade.getLevelY()][facade.getLevelX()];//инициализация отображения карты
        for (int m = 0; m < facade.getLevelY(); m++) {
            for (int n = 0; n < facade.getLevelX(); n++) {
                panelHolder[m][n] = new JPanel(new BorderLayout(0, 0));
                panel1.add(panelHolder[m][n]);
            }
        }
        panel1.setFocusable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //запись результата в файл
    private void fillResults() {
        tableOfResult.add(new Pair<>(name, facade.getScore()));
        tableOfResult.sort((a, b) -> -a.getValue().compareTo(b.getValue()));
        if (tableOfResult.size() > 10)
            tableOfResult = tableOfResult.subList(0, 10);

        try {
            PrintWriter writer = new PrintWriter("res/results.txt", "UTF-8");
            for (Pair<String, Integer> res : tableOfResult)
                writer.println(res.getKey() + " " + res.getValue());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
//отрисовка карты и статус игры
    public void fillMap(Facade facade, boolean fillAll) {
        if (facade.getGameStatus() == GameStatus.LOOSE) {
            label1.setText("GameOver");
            fillResults();
        }
        if (facade.getGameStatus() == GameStatus.WIN) {
            label1.setText("Win");
            fillResults();
        }

        this.facade = facade;
        int map[][] = facade.getLevelMap();
        for (int m = 0; m < facade.getLevelY(); m++) {
            for (int n = 0; n < facade.getLevelX(); n++) {
                if (!fillAll) {
                    boolean doContinue = true;
                    //список отклонений от проверяемой точки
                    java.util.List<Pair<Integer, Integer>> directions = new ArrayList<>();
                    directions.add(new Pair<>(-1, 0));
                    directions.add(new Pair<>(1, 0));
                    directions.add(new Pair<>(0, -1));
                    directions.add(new Pair<>(0, 1));
                    directions.add(new Pair<>(0, 0));

                    for (Pair<Integer, Integer> p : directions) {
                        //проверка на выход из границы карты
                        if (m + p.getKey() < 0 || m + p.getKey() > facade.getLevelY() - 1 || n + p.getValue() < 0 || n + p.getValue() > facade.getLevelX() - 1)
                            continue;
                        int cellVal = map[m + p.getKey()][n + p.getValue()];
                        if (cellVal >2) {
                            doContinue = false;
                            break;
                        }
                    }
                    if (doContinue)
                        continue;
                }
                //отрисовка
                try {
                    BufferedImage myPicture;
                    switch (map[m][n]) {
                        case 0: {
                            myPicture = this.dotPicture;
                            break;
                        }
                        case 1: {
                            myPicture = this.flPicture;
                            break;
                        }
                        case 2: {
                            myPicture = this.wallPicture;
                            break;
                        }
                        case 3: {
                            myPicture = this.mons;
                            break;
                        }
                        case 4: {
                            myPicture = this.mons;
                            break;
                        }
                        case 5: {
                            myPicture = this.fl_rP;
                            break;
                        }
                        case 6: {
                            myPicture = this.fl_lP;
                            break;
                        }
                        case 7: {
                            myPicture = this.b_dotP;
                            break;
                        }
                        case 8: {
                            myPicture = this.pmP;
                            break;
                        }
                        case 9: {
                            myPicture = this.mons;
                            break;
                        }
                        case 10: {
                            myPicture = this.mons;
                            break;
                        }
                        default: {
                            myPicture = ImageIO.read(new File("img/wall.png"));
                            break;
                        }
                    }

                    JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                    panelHolder[m][n].removeAll();
                    panelHolder[m][n].add(picLabel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.revalidate();
        scoreTextField.setText(String.valueOf(facade.getScore()));
        panel1.setFocusable(true);
    }
//обработка нажатой клавиши
    private void panel1KeyPressed(KeyEvent e) {
        if(facade.getGameStatus()!=GameStatus.CONTINUE){
            return;
        }
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                facade.pacmanGoUP();
                keyReleased = false;
                fillMap(facade, false);
                break;
            case KeyEvent.VK_DOWN:
                facade.pacmanGoDown();
                keyReleased = false;
                fillMap(facade, false);
                break;
            case KeyEvent.VK_LEFT:
                facade.pacmanGoLeft();
                keyReleased = false;
                fillMap(facade, false);
                break;
            case KeyEvent.VK_RIGHT:
                facade.pacmanGoRight();
                keyReleased = false;
                fillMap(facade, false);
                break;
        }
    }
//залипанеи клавиши
    private void panel1KeyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            keyReleased = true;
        }
    }

//выход при нажатии на ехит
    private void exitButtonActionPerformed(ActionEvent e) {
        System.exit(0);
    }
//действие при нажимании на старт
    private void startButtonActionPerformed(ActionEvent e) {
        if (gameStart) {
            this.dispose();
            facade.restartGame();
        } else {
            name = JOptionPane.showInputDialog(this, "Name");
            facade.startGame();
            gameStart = true;
            startButton.setText("restart");
        }
        fillMap(facade, true);
    }
//инициализация графики
    private void initComponents() {
        try {
            this.dotPicture = ImageIO.read(new File("img/dot.png"));
            this.flPicture = ImageIO.read(new File("img/fl.png"));
            this.wallPicture = ImageIO.read(new File("img/wall.png"));
            this.mob1_lPicture = ImageIO.read(new File("img/mob1_l.png"));
            this.mob2_rP = ImageIO.read(new File("img/mob2_r.png"));
            this.fl_rP = ImageIO.read(new File("img/fl-r.png"));
            this.fl_lP = ImageIO.read(new File("img/fl-l.png"));
            this.b_dotP = ImageIO.read(new File("img/b_dot.png"));
            this.pmP = ImageIO.read(new File("img/pm.png"));
            this.mob1_rPicture = ImageIO.read(new File("img/mob1_r.png"));
            this.mob2_lP = ImageIO.read(new File("img/mob2_l.png"));
            this.mons = ImageIO.read(new File("img/mon.png"));
            this.pm2 = ImageIO.read(new File("img/pm_r.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        panel1 = new JPanel();
        label1 = new JLabel();
        startButton = new JButton();
        exitButton = new JButton();
        label2 = new JLabel();
        scoreTextField = new JTextField();

        setFocusable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

            panel1.setBackground(Color.black);
            panel1.setBorder(null);
            panel1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (keyReleased)
                        panel1KeyPressed(e);
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    panel1KeyReleased(e);
                }
            });
            panel1.setLayout(new GridLayout(19, 19, 2, 0));

        contentPane.add(panel1);
        panel1.setBounds(20, 110, 440, 410);

        label1.setText("PacMan");
        label1.setFont(label1.getFont().deriveFont(label1.getFont().getSize() + 8f));
        contentPane.add(label1);
        label1.setBounds(655, 45, 190, label1.getPreferredSize().height);

        startButton.setText("Start");
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButtonActionPerformed(e);
            }
        });
        contentPane.add(startButton);
        startButton.setBounds(600, 130, 180, startButton.getPreferredSize().height);

        exitButton.setText("Exit");
        exitButton.setFocusable(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButtonActionPerformed(e);
            }
        });
        contentPane.add(exitButton);
        exitButton.setBounds(600, 180, 175, exitButton.getPreferredSize().height);

        label2.setText("Score");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(665, 350), label2.getPreferredSize()));

        scoreTextField.setFocusable(false);
        contentPane.add(scoreTextField);
        scoreTextField.setBounds(595, 380, 180, scoreTextField.getPreferredSize().height);

        setSize(890, 610);
        setLocationRelativeTo(getOwner());
    }
}
