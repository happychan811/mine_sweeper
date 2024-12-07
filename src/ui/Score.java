package ui;

import javax.swing.*;
import java.awt.*;

public class Score extends JLabel {
    int id;
    int score;
    int mine;
    int map;
    int time;

    public Score(int id, int score, int mine, int map, int time) {
        this.id = id;
        this.score = score;
        this.mine = mine;
        this.map = map;
        this.time = time;

        setText("점수: " + score + ", 지뢰수: " + mine + ", 맵 크기: " + map + ", 시간: " + time);
        setFont(new Font("궁서체", Font.PLAIN, 20));
    }
}
