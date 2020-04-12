package com.example.book2play.db.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DBUtils {
    private final static Logger LOG = Logger.getLogger("UTILS");

    public static void quietCloseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.warning("Could not close connection: " + e.getMessage());
            }
        }
    }

    public static void quietCloseStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOG.warning("Could not close callable statement: " + e.getMessage());
            }
        }
    }

    public static void quietCloseResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.warning("Could not close result set: " + e.getMessage());
            }
        }
    }
}
