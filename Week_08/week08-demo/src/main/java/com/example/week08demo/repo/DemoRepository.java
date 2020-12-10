package com.example.week08demo.repo;

import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public DemoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    // @ShardingTransactionType(TransactionType.XA)
    public TransactionType insertOrderDetail(int count) {
        return jdbcTemplate.execute("INSERT INTO `order_detail` (`order_id`, `product_id`, `price`, `create_by`," +
                        " `update_by`, `quality`) VALUES (?, ?, ?, ?, ?, ?)",
                (PreparedStatementCallback<TransactionType>) preparedStatement -> {
                    for (int i = 0; i < count; i++) {
                        preparedStatement.setObject(1, i);
                        preparedStatement.setObject(2, i);
                        preparedStatement.setObject(3, i);
                        preparedStatement.setObject(4, "aa");
                        preparedStatement.setObject(5, "bb");
                        preparedStatement.setObject(6, i);
                        preparedStatement.executeUpdate();
                    }
                    return TransactionTypeHolder.get();
                });
    }

    public TransactionType insertProductFailed(int count) {
        return jdbcTemplate.execute("INSERT INTO `product` (`name`, `create_by`, `update_by`, `available_quality`) " +
                        "VALUES (?, ?, ?, ?)",
                (PreparedStatementCallback<TransactionType>) preparedStatement -> {
                    for (int i = 0; i < count; i++) {
                        preparedStatement.setObject(1, "name" + i);
                        preparedStatement.setObject(2, "bb");
                        preparedStatement.setObject(3, "bb");
                        preparedStatement.setObject(4, i);
                        preparedStatement.executeUpdate();
                    }
                    throw new RuntimeException("mock transaction failed");
                    // return TransactionTypeHolder.get();
                });
    }

    public TransactionType insertProduct(int count) {
        return jdbcTemplate.execute("INSERT INTO `product` (`name`, `create_by`, `update_by`, `available_quality`) " +
                        "VALUES (?, ?, ?, ?)",
                (PreparedStatementCallback<TransactionType>) preparedStatement -> {
                    for (int i = 0; i < count; i++) {
                        preparedStatement.setObject(1, "name" + i);
                        preparedStatement.setObject(2, "bb");
                        preparedStatement.setObject(3, "bb");
                        preparedStatement.setObject(4, i);
                        preparedStatement.executeUpdate();
                    }
                    return TransactionTypeHolder.get();
                });
    }

    @Transactional
    public void insertWithoutXA(int count) {
        doExecute(count);
    }

    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public void insertWithXA(int count) {
        doExecute(count);
    }

    private void doExecute(int count) {
        TransactionType transactionType = insertOrderDetail(count);
        System.out.printf("transactionType of insertOrderDetail is %s%n", transactionType);
        TransactionType transactionType1 = insertProduct(count);
        System.out.printf("transactionType of insertProductFailed is %s%n", transactionType1);
    }
}
