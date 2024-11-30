package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Ranking extends JPanel {
    private JLabel title;

    private JPanel rankingPanel;

    private JScrollPane scrollPane;
    private JPanel textPanel;

    private JButton backButton;

    ArrayList<Score> scoreList;

    public Ranking(Menu menu) {
        setLayout(new BorderLayout());

        title = new JLabel("혁명적인 지뢰찾기");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("궁서체", Font.BOLD, 40));
        add(title, BorderLayout.NORTH);

        rankingPanel = new JPanel();
        rankingPanel.setLayout(new BorderLayout());

        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(textPanel);
        rankingPanel.add(scrollPane);

        add(rankingPanel, BorderLayout.CENTER);

        backButton = new JButton("뒤로");
        backButton.setFont(new Font("궁서체", Font.BOLD, 15));
        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backButton) {
                    menu.cardLayout.next(menu.container);
                }
            }
        });
        add(backButton, BorderLayout.SOUTH);

        scoreList = new ArrayList<>();
    }

    public void UpdateRanking() throws SQLException {
        int[] scores = MineDAO.getAllScores();
        int[] mines = MineDAO.getAllMines();
        int[] maps = MineDAO.getAllMaps();
        int[] times = MineDAO.getAllTimes();

        for (int i = 0; i < textPanel.getComponentCount(); i++) {
            textPanel.remove(i);
        }

        for (int i = 0; i < scores.length; i++) {
            scoreList.add(new Score(scores[i], mines[i], maps[i], times[i]));
            textPanel.add(scoreList.get(i));
        }
    }
}
