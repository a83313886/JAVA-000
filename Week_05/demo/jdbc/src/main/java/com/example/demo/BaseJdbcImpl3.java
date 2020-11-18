package com.example.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseJdbcImpl3 {
    private static Connection conn;
    private static HikariDataSource ds;

    public static void main(String[] args) throws SQLException {
        conn = getConnection();
        conn.setAutoCommit(false);

        System.out.println("---original---");
        query();

        add();

        System.out.println("---after add---");

        query();

        delete();

        System.out.println("---after delete---");

        query();

        update();

        System.out.println("---after update---");

        query();
        conn.close();
    }

    private static void update() throws SQLException {
        String sql = "update user set name = 'tommy j' where id = 2";


        conn.createStatement().execute(sql);

        conn.commit();
    }

    private static void initPool() {
        String schemaSqlFile = BaseJdbcImpl3.class.getResource("/db/schema-h2.sql").getPath();
        String dataSqlFile = BaseJdbcImpl3.class.getResource("/db/data-h2.sql").getPath();
        String url = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM '" + schemaSqlFile + "'\\;RUNSCRIPT FROM '" + dataSqlFile + "'";

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername("root");
        config.setPassword("test");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    private static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void query() throws SQLException {

        conn.setReadOnly(true);
        ResultSet rs = conn.createStatement().executeQuery("select id,name,age,email from user");

        List<User> userList = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setEmail(rs.getString("email"));
            userList.add(user);
        }

        conn.commit();

        System.out.println(userList);
    }

    public static void add() throws SQLException {
        String sql = "INSERT INTO user (id, name, age, email) VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = conn.prepareStatement(sql);
        //6,'Justin', 36, 'test6@bsmdnms.com'
        pstm.setLong(1, 6);
        pstm.setString(2, "Justin");
        pstm.setInt(3, 36);
        pstm.setString(4, "test6@bsmdnms.com");

        pstm.addBatch();

        pstm.setLong(1, 7);
        pstm.setString(2, "Justin7");
        pstm.setInt(3, 36);
        pstm.setString(4, "test7@bsmdnms.com");

        pstm.addBatch();
        pstm.executeBatch();
        conn.commit();
    }

    public static void delete() throws SQLException {
        String sql = "delete from user where id in (5,6)";

        conn.createStatement().execute(sql);

        conn.commit();
    }


}
