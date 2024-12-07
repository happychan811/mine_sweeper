package ui;

import java.sql.*;
import java.util.ArrayList;

public class MineDAO {
    public static void addScore(int score,int mine, int map, int time) throws SQLException {
        String sql = "INSERT INTO mine_sweeper (score,mine,map,time) VALUES (?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, score);
            pstmt.setInt(2, mine);
            pstmt.setInt(3, map);
            pstmt.setInt(4, time);
            pstmt.executeUpdate();
        }
    }

    public static int[] getAllId() throws SQLException {
        String sql = "SELECT * FROM mine_sweeper";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (rs.next()) {
                arrayList.add(rs.getInt("id"));
            }
            int[] arr = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++)
                arr[i] = arrayList.get(i);

            return arr;
        }
    }

    public static Score[] getAllScore() throws SQLException {
        String sql = "SELECT * FROM mine_sweeper ORDER BY score DESC";
        ArrayList<Score> arrayList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                arrayList.add(new Score(
                        rs.getInt("id"),
                        rs.getInt("score"),
                        rs.getInt("mine"),
                        rs.getInt("map"),
                        rs.getInt("time")
                ));
            }
        }

        Score[] arr = new Score[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
            arr[i] = arrayList.get(i);

        return arr;
    }
}
