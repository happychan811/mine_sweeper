package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

//SQL SCHEMAS = north_korea_mine_sweeper / Table = mine_sweeper / Column 1 = id[int] (PK,NN,UQ,AI) / Column 2 = score[int] (NN)

public class Menu extends JFrame {
    private JLabel title;

    private JPanel panelCenter;
    private JPanel setPanel;

    private JLabel gridSetText;
    private JTextField gridSetField;
    private JLabel mineSetText;
    private JTextField mineSetField;

    private JButton startButton;

    public Menu() {
        setTitle("지뢰찾기");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        title = new JLabel("혁명적인 지뢰찾기");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("궁서체", Font.BOLD, 40));
        add(title, BorderLayout.NORTH);

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

        startButton = new JButton("혁명 시작");
        startButton.setFont(new Font("궁서체", Font.BOLD, 15));
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    if (isInteger(gridSetField.getText()) && isInteger(mineSetField.getText())) {
                        int grid_size = Integer.parseInt(gridSetField.getText());
                        int total_mines = Integer.parseInt(mineSetField.getText());

                        if (grid_size * grid_size <= total_mines) {
                            JOptionPane.showMessageDialog(getContentPane(), "지뢰의 최대값: " + (grid_size * grid_size - 1));

                        }
                        else {
                            new MineSweeperGame(grid_size, total_mines);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(getContentPane(), "입력칸에 숫자를 모두 입력하시오");
                    }
                }
            }
        });
        panelCenter.add(startButton, BorderLayout.SOUTH);
        add(panelCenter, BorderLayout.CENTER);

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
