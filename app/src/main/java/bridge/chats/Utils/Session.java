package bridge.chats.Utils;

import bridge.chats.Linker;
import bridge.chats.Platform.Platform;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Session {
  private Connection connection;

  public enum Status { ACTIVE, INACTIVE }

  private Platform platform;
  private Status status;
  private String sessionId;
  private String token;

  public Session(Platform platform, Status status, String sessionId, String token) {
    this.platform = platform;
    this.status = status;
    this.sessionId = sessionId;
    this.token = token;
  }

  public void connect(String path) {
    try {
      connection = DriverManager.getConnection(path);

      PreparedStatement statement = connection.prepareStatement(
          "CREATE TABLE IF NOT EXISTS sessions (platform TEXT, status TEXT, session_id TEXT, token TEXT)");
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void save(Linker link) {
    try {
      PreparedStatement statement = connection.prepareStatement(
          "INSERT INTO sessions (platform, status, session_id, token) VALUES (?, ?, ?, ?)");
      statement.setString(1, platform.toString());
      statement.setString(2, status.toString());
      statement.setString(3, sessionId);
      statement.setString(4, token);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
