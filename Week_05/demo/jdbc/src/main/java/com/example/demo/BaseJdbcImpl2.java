package com.example.demo;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BaseJdbcImpl2 {
    private static Connection conn;

    public static void main(String[] args) throws SQLException {
        Instant start = Instant.now();
        addOrderParallel();
        Instant end = Instant.now();
        System.out.printf("total waste:%s seconds", Duration.between(start, end).getSeconds());
    }

/*    public static void main(String[] args) throws SQLException {
        conn = getMysqlConnection();
        conn.setAutoCommit(false);
        Instant start = Instant.now();
        addOrder();
        Instant end = Instant.now();
        System.out.printf("total waste:%s seconds", Duration.between(start, end).getSeconds());
        conn.close();
    }*/

/*    public static void main(String[] args) throws SQLException {
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
    }*/

    private static void update() throws SQLException {
        String sql = "update user set name = 'tommy j' where id = 2";


        conn.createStatement().execute(sql);

        conn.commit();
    }

    private static Connection getConnection() throws SQLException {
        String schemaSqlFile = BaseJdbcImpl2.class.getResource("/db/schema-h2.sql").getPath();
        String dataSqlFile = BaseJdbcImpl2.class.getResource("/db/data-h2.sql").getPath();
        String url = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM '" + schemaSqlFile + "'\\;RUNSCRIPT FROM '" + dataSqlFile + "'";
        return DriverManager.
                getConnection(url, "root", "test");
    }

    private static Connection getMysqlConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai&useUnicode=true" +
                "&characterEncoding=UTF-8&useSSL=false";
        return DriverManager.
                getConnection(url, "root", "");
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

    public static void addOrder() throws SQLException {
        String sql = "INSERT INTO db.order(`user_id`, `status`, `pay_status`, `price`, `create_by`, `update_by`) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        // Connection conn = getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);

        IntStream.range(1, 10000001).forEach(i -> {
            try {
                pstm.setInt(1, 0);
                pstm.setInt(2, 0);
                pstm.setInt(3, 0);
                // pstm.setString(4, "RAND() * 50000");
                pstm.setInt(4, 4000);
                pstm.setString(5, "sd");
                pstm.setString(6, "232323f");
                pstm.addBatch();

                if (i%5000 == 0) {
                    pstm.executeBatch();
                    conn.commit();
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
        pstm.executeBatch();
        conn.commit();
        // conn.close();
    }

    public static void addOrderParallel() throws SQLException {
        String sql = "INSERT INTO db.order(`user_id`, `status`, `pay_status`, `price`, `create_by`, `update_by`) " +
                "VALUES(?, ?, ?, ?, ?, ?)";

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        IntStream.range(1, 101).forEach(i -> executorService.submit(() -> {
            try {
                Connection conn = getMysqlConnection();
                conn.setAutoCommit(false);
                PreparedStatement pstm = conn.prepareStatement(sql);

                IntStream.range(1, 100001).forEach(q -> {
                    try {
                        pstm.setInt(1, 0);
                        pstm.setInt(2, 0);
                        pstm.setInt(3, 0);
                        // pstm.setString(4, "RAND() * 50000");
                        pstm.setInt(4, 4000);
                        pstm.setString(5, "sd");
                        pstm.setString(6, "232323f");
                        pstm.addBatch();

                        if (i % 5000 == 0) {
                            pstm.executeBatch();
                            conn.commit();
                        }

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                });
                pstm.executeBatch();
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        try {
            executorService.shutdown();
            executorService.awaitTermination(2000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void delete() throws SQLException {
        String sql = "delete from user where id in (5,6)";

        conn.createStatement().execute(sql);

        conn.commit();
    }


}
