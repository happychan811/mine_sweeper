package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Random;

public class MineSweeperGame extends JFrame implements ActionListener {
    private JPanel gamePanle;
    private JPanel scorePanle;
    private JButton[][] buttons;
    private JLabel mineCount;
    private JLabel timeLabel;

    private Timer timer;
    private int time = 0;
    private int expectMines;
    private boolean[][] mines;
    private boolean[][] flags;
    private int[][] numbers;
    private int GRID_SIZE = 10;
    private final int BUTTON_SIZE = 45;
    private int TOTAL_MINES; // 총 지뢰 개수
    private boolean[][] breaks;
    private boolean gameOver = false;

    public MineSweeperGame(int grid_size, int total_mines) {
        GRID_SIZE = grid_size;
        TOTAL_MINES = total_mines;
        expectMines = TOTAL_MINES;
        timer = new Timer(1000, this);
        timer.start();

        setTitle("지뢰찾기");
        setLayout(new BorderLayout());

        // 배열 초기화
        buttons = new JButton[GRID_SIZE][GRID_SIZE];
        mines = new boolean[GRID_SIZE][GRID_SIZE];
        flags = new boolean[GRID_SIZE][GRID_SIZE];
        breaks = new boolean[GRID_SIZE][GRID_SIZE];
        numbers = new int[GRID_SIZE][GRID_SIZE];

        // 지뢰 배치 및 숫자 계산
        placeMines();
        calculateNumbers();

        //점수판
        scorePanle = new JPanel();

        mineCount = new JLabel("남조선 괴뢰들의 지뢰 배치 동향 분석 결과 : " + total_mines);
        scorePanle.add(mineCount);

        timeLabel = new JLabel("시간 : 0");
        scorePanle.add(timeLabel);

        add(scorePanle, BorderLayout.NORTH);

        // 버튼 생성 및 이벤트 설정
        gamePanle = new JPanel();
        gamePanle.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        createButtons();
        add(gamePanle, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createButtons() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                button.setFont(new Font("Arial", Font.BOLD, 16));

                final int currentRow = row;
                final int currentCol = col;

                // 마우스 이벤트 처리
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (gameOver) return;

                        // 좌클릭
                        if (e.getButton() == MouseEvent.BUTTON1 && !flags[currentRow][currentCol]) {
                            handleLeftClick(currentRow, currentCol);
                        }
                        // 우클릭
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            handleRightClick(currentRow, currentCol);
                        }
                    }
                });
                //
                buttons[row][col] = button;
                gamePanle.add(button);
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void placeMines() {
        Random random = new Random();

        for (int i = 0; i < TOTAL_MINES; i++) {
            int x = random.nextInt(GRID_SIZE);
            int y = random.nextInt(GRID_SIZE);
            if (!mines[x][y]) {
                mines[x][y] = true;
            }
            else {
                i -= 1;
            }

            if (GRID_SIZE * GRID_SIZE == TOTAL_MINES)
                break;
        }
    }

    private void calculateNumbers() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!mines[row][col]) {
                    numbers[row][col] = countAdjacentMines(row, col);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < GRID_SIZE &&
                        newCol >= 0 && newCol < GRID_SIZE &&
                        mines[newRow][newCol]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void handleLeftClick(int row, int col) {
        if (numbers[row][col] > 0 && breaks[row][col]) {
            numberReveal(row, col);
            return;
        }
        if (buttons[row][col].isEnabled() && !flags[row][col]) {
            if (mines[row][col]) {
                gameOver = true;
                revealAllMines();
                JOptionPane.showMessageDialog(this, "인민 낙원의 공작원이 실패했습네다");
                dispose();
            } else {
                reveal(row, col);
            }
        }
    }

    private void end(){
        MineDAO mineDAO = new MineDAO();
        int count = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if(flags[i][j] == true && flags[i][j] == mines[i][j]){
                    count++;
                }
            }
        }
        if(count == TOTAL_MINES){
            timer.stop();
            JOptionPane.showMessageDialog(this, "인민 낙원의 공작원이 혁명적으로 성공하였습네다");
            try{
                mineDAO.addScore(time,TOTAL_MINES,GRID_SIZE);
            }catch (SQLException e){
            }
        }
    }

    private void handleRightClick(int row, int col) {
        if (buttons[row][col].isEnabled() && !flags[row][col] && !breaks[row][col]) {
            buttons[row][col].setBackground(Color.green);
            flags[row][col] = true;
            expectMines--;
        }else if (buttons[row][col].isEnabled() && flags[row][col] && !breaks[row][col]){
            buttons[row][col].setBackground(Color.LIGHT_GRAY);
            flags[row][col] = false;
            expectMines++;
        }
        if(expectMines == 0){
            end();
        }
        mineCount.setText("남조선 괴뢰들의 지뢰 배치 동향 분석 결과 : " + expectMines);
    }

    private void numberReveal(int row, int col) {
        int flagCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (row+i < 0 || row+i >= GRID_SIZE || col+j < 0 || col+j >= GRID_SIZE)
                    continue;

                if (flags[row+i][col+j])
                    flagCount++;
            }
        }

        if(flagCount == numbers[row][col]){
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (row+i < 0 || row+i >= GRID_SIZE || col+j < 0 || col+j >= GRID_SIZE || (i == 0 && j == 0))
                        continue;

                    if(!flags[row + i][col + j]){
                        reveal(row + i, col + j);
                    }
                }
            }
        }
    }

    private void reveal(int row, int col) {
        if (row < 0 || col < 0 || row >= GRID_SIZE || col >= GRID_SIZE || !buttons[row][col].isEnabled() || breaks[row][col] || flags[row][col]) {
            return;
        }

        breaks[row][col] = true;
        buttons[row][col].setBackground(Color.white);
        if (numbers[row][col] > 0) {
            buttons[row][col].setText(String.valueOf(numbers[row][col]));
            setNumberColor(buttons[row][col], numbers[row][col]);
        }

        if (!mines[row][col] && numbers[row][col] == 0) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    reveal(row + dx, col + dy);
                }
            }
        }
    }


    private void setNumberColor(JButton button, int number) {
        Color[] colors = {
                null,
                Color.BLUE,
                new Color(0, 128, 0),
                Color.RED,         // 3
                new Color(0, 0, 128),
                new Color(128, 0, 0),
                new Color(128, 0, 128),
                Color.BLACK,
                Color.GRAY
        };
        button.setForeground(colors[number]);
    }

    private void revealAllMines() {
        timer.stop();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (mines[i][j] && !flags[i][j]) {
                    buttons[i][j].setBackground(Color.red);
                    buttons[i][j].setEnabled(false);
                } else if (mines[i][j] && flags[i][j]) {
                    buttons[i][j].setBackground(Color.blue);
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time += timer.getDelay() / 1000;
        timeLabel.setText("시간: " + time);
    }
}