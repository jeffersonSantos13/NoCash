package br.com.urway.nocash.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAOJDBC {
    
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://carteiravirtual-ws-pi.database.windows.net:1433;database=carteiravirtual";
    private static final String USER = "CarteiraVirtualAdmin@carteiravirtual-ws-pi";
    private static final String PASS = "CarteiraVirtual@PIV";

    private String driver;
    private String url;
    private String user;
    private String pass;

    public DAOJDBC() {
        this.driver = DRIVER;
        this.url = URL;
        this.user = USER;
        this.pass = PASS;
    }
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pass);
    }
    
    public void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public void close(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
            }
        }
    }
    
}
