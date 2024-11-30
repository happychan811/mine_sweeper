package ui;

import java.sql.*;

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
}
