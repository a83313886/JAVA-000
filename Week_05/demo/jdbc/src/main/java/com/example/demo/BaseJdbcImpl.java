package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseJdbcImpl {
    private static Connection conn;

    public static void main(String[] args) throws SQLException {
        conn = getConnection();

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
    }

    private static Connection getConnection() throws SQLException {
        String schemaSqlFile = BaseJdbcImpl.class.getResource("/db/schema-h2.sql").getPath();
        String dataSqlFile = BaseJdbcImpl.class.getResource("/db/data-h2.sql").getPath();
        String url = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM '" + schemaSqlFile + "'\\;RUNSCRIPT FROM '" + dataSqlFile + "'";
        return DriverManager.
                getConnection(url, "root", "test");
    }

    public static void query() throws SQLException {
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

        System.out.println(userList);
    }

    public static void add() throws SQLException {
        String sql = "INSERT INTO user (id, name, age, email) VALUES(6,'Justin', 36, 'test6@bsmdnms.com')";
        conn.createStatement().execute(sql);
    }

    public static void delete() throws SQLException {
        String sql = "delete from user where id in (5,6)";
        conn.createStatement().execute(sql);
    }


}
