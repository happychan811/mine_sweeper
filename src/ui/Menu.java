package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//SQL SCHEMAS = north_korea_mine_sweeper / Table = mine_sweeper / Column 1 = id[int] (PK,NN,UQ,AI) / Column 2 = score[int] (NN) / Column 3 = mine[int] (NN) / Column 4 = map[int] (NN) / Column 5 = time[int] (NN)

public class Menu extends JFrame {
    public CardLayout cardLayout;
    public JPanel container;  // Container를 JPanel로 변경

    private JPanel menuPanel;
    private Ranking ranking;

    private JLabel title;

    private JPanel panelCenter;
    private JPanel setPanel;

    private JLabel gridSetText;
    private JTextField gridSetField;
    private JLabel mineSetText;
    private JTextField mineSetField;

    private JButton startButton;
    private JButton rankButton;

    public Menu() {
        setTitle("지뢰찾기");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout(0, 0);
        setLayout(cardLayout);

        menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        ranking = new Ranking(this);

        container = new JPanel(cardLayout);
        container.add(menuPanel, "card_1");
        container.add(ranking, "card_2");

        title = new JLabel("혁명적인 지뢰찾기");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("궁서체", Font.BOLD, 40));
        menuPanel.add(title, BorderLayout.NORTH);

        panelCenter = new JPanel();
        panelCenter.setLayout(new BorderLayout());
        setPanel = new JPanel();
        setPanel.setLayout(new GridLayout(2, 2));
        panelCenter.add(setPanel, BorderLayout.NORTH);

        gridSetText = new JLabel("남조선 가로 세로 크기 지정");
        gridSetText.setHorizontalAlignment(SwingConstants.CENTER);
        gridSetText.setFont(new Font("궁서체", Font.BOLD, 15));
        setPanel.add(gridSetText);
        gridSetField = new JTextField("10", 10);
        setPanel.add(gridSetField);

        mineSetText = new JLabel("남조선 지뢰들 지정");
        mineSetText.setHorizontalAlignment(SwingConstants.CENTER);
        mineSetText.setFont(new Font("궁서체", Font.BOLD, 15));
        setPanel.add(mineSetText);
        mineSetField = new JTextField("15", 10);
        setPanel.add(mineSetField);

        startButton = new JButton("혁명을 시작합네다");
        startButton.setFont(new Font("궁서체", Font.BOLD, 15));
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    if (isInteger(gridSetField.getText()) && isInteger(mineSetField.getText())) {
                        int grid_size = Integer.parseInt(gridSetField.getText());
                        int total_mines = Integer.parseInt(mineSetField.getText());

                        if (grid_size * grid_size <= total_mines) {
                            JOptionPane.showMessageDialog(getContentPane(), "아무래도 지뢰가 이정도로 많을 것 같진 않습네다.\n이 공간이라면 " + (grid_size * grid_size - 1) + "개 정도의 지뢰가 있을 수 있을 것 으로 추정됩네다");
                        }
                        else {
                            new MineSweeperGame(grid_size, total_mines);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "어디를 조사하라는 겁네까?\n이곳은 지뢰가 없거나 존제하지 않는 땅입네다.");
                    }
                }
            }
        });
        rankButton = new JButton("동지들을 확인합네다");
        rankButton.setFont(new Font("궁서체", Font.BOLD, 15));
        rankButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == rankButton) {
                    cardLayout.next(container);
                }
            }
        });
        panelCenter.add(startButton, BorderLayout.CENTER);
        panelCenter.add(rankButton, BorderLayout.SOUTH);
        menuPanel.add(panelCenter, BorderLayout.CENTER);

        add(container);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static boolean isInteger(String strValue) {
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu();
        });
    }
}
