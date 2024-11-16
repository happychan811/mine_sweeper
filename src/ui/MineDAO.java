package ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MineDAO {

    public static void addScore(int score,int mine, int map) throws SQLException {
        String sql = "INSERT INTO tetris_board (score) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(2, score);
            pstmt.setInt(3, mine);
            pstmt.setInt(4, map);
            pstmt.executeUpdate();
        }
    }
}
