package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {
    private static QueryRunner runner = new QueryRunner();
    private SqlHelper() {
    }

    private static Connection getConnect() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getCode() {
        var codeSql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConnect();
        var code = runner.query(conn,codeSql,new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connect = getConnect();
        runner.update(connect, "DELETE FROM auth_codes");
        runner.update(connect, "DELETE FROM card_transactions");
        runner.update(connect, "DELETE FROM cards");
        runner.update(connect, "DELETE FROM users");

    }
}
