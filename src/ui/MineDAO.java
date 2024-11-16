package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MineDAO {

    public static void addScore(int score,int mine, int map) throws SQLException {
        String sql = "INSERT INTO mine_sweeper (score,mine,map) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, score);
            pstmt.setInt(2, mine);
            pstmt.setInt(3, map);
            pstmt.executeUpdate();
        }
    }
}
