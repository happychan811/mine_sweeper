package ui;

import javax.swing.*;
import java.awt.*;

public class Score extends JLabel {
    int scores;
    int mines;
    int maps;
    int times;

    public Score(int scores, int mines, int maps, int times) {
        this.scores = scores;
        this.mines = mines;
        this.maps = maps;
        this.times = times;

        setText("점수: " +scores + ", 지뢰수: " + mines + ", 맵 크기: " + maps + ", 시간: " + times);
        setFont(new Font("궁서체", Font.PLAIN, 20));
    }
}
