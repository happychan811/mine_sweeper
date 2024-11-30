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

    public static int[] getAllScores() throws SQLException {
        String sql = "SELECT * FROM mine_sweeper";
        System.out.println("a");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (rs.next()) {
                arrayList.add(rs.getInt("score"));
            }
            int[] arr = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++)
                arr[i] = arrayList.get(i);

            return arr;
        }
    }

    public static int[] getAllMines() throws SQLException {
        String sql = "SELECT * FROM mine_sweeper";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (rs.next()) {
                arrayList.add(rs.getInt("mine"));
            }
            int[] arr = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++)
                arr[i] = arrayList.get(i);

            return arr;
        }
    }

    public static int[] getAllMaps() throws SQLException {
        String sql = "SELECT * FROM mine_sweeper";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (rs.next()) {
                arrayList.add(rs.getInt("map"));
            }
            int[] arr = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++)
                arr[i] = arrayList.get(i);

            return arr;
        }
    }

    public static int[] getAllTimes() throws SQLException {
        String sql = "SELECT * FROM mine_sweeper";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (rs.next()) {
                arrayList.add(rs.getInt("time"));
            }
            int[] arr = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++)
                arr[i] = arrayList.get(i);

            return arr;
        }
    }
}
