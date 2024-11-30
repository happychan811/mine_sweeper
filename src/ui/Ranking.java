package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Ranking extends JPanel {
    private JLabel title;

    private JPanel rankingPanel;

    private JLabel text;
    private JScrollPane scrollPane;

    private JButton backButton;



    public Ranking(Menu menu) {
        setLayout(new BorderLayout());

        title = new JLabel("혁명적인 지뢰찾기");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("궁서체", Font.BOLD, 40));
        add(title, BorderLayout.NORTH);

        rankingPanel = new JPanel();
        rankingPanel.setLayout(new BorderLayout());

        text = new JLabel();
        scrollPane = new JScrollPane(text);
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
    }

    public void UpdateRanking() {

    }
}
