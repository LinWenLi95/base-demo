package com.lwl.base.code.generator;

import com.lwl.base.code.generator.codegenerate.CodeGenConfig;
import com.lwl.base.code.generator.codegenerate.db.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MybatisgenerateApplicationTests {

    @Test
    public void contextLoads() {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "show tables";
        Object[] params = null;
        int columnCount = 0;
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
//            parameterInit(preparedStatement, params);
            rs = preparedStatement.executeQuery();
            columnCount = rs.getMetaData().getColumnCount();
        } catch (SQLException var12) {
            System.out.println("DbHelper queryList1Object err:" + var12.getMessage() + ",sqlState=" + var12.getSQLState());
            return;
        }

        ArrayList<Object> list = new ArrayList<>();

        try {
            while (rs.next()) {
                for (int i = 1; i <= columnCount; ++i) {
                    list.add(rs.getObject(1));
                }
            }
        } catch (SQLException var13) {
            System.out.println("DbHelper queryList1Object err:" + var13.getMessage() + ",sqlState=" + var13.getSQLState());
        } finally {
            try {
                rs.close();
            } catch (SQLException var3) {
                System.out.println("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
            try {
                preparedStatement.close();
            } catch (SQLException var3) {
                System.out.println("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
        }

        for (Object obj : list) {
            System.out.println(obj.toString());
        }
    }


    private static Connection getConnection() {
        String url,user,password;
        try {
            return DriverManager.getConnection("jdbc:mysql://47.106.198.29:3306/db_test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true", "root", "MysqL@951QwdC");
        } catch (SQLException var2) {
            System.out.println("DbHelper getConnection err:" + var2.getMessage() + ",sqlState=" + var2.getSQLState());
        }
        return null;
    }

    private static void parameterInit(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; ++i) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

    }

    @Test
    public void gg() {
        excute("show tables",null);
    }

    private static List<Object> excute(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://47.106.198.29:3306/db_test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true", "root", "MysqL@951QwdC");
            preparedStatement = connection.prepareStatement(sql);
            parameterInit(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("DbHelper queryList1Object err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            close(connection, preparedStatement, resultSet);
            return null;
        }

        List<Object> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(resultSet.getObject(1));
            }
        } catch (SQLException e) {
            System.out.println("DbHelper queryList1Object err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            return null;
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        for (Object obj : list) {
            System.out.println(obj.toString());
        }
        return list;
    }

    /**
     * 数据库操作对象关闭
     * @param connection 数据库连接对象
     * @param preparedStatement SQL语句预编译对象
     * @param resultSet 结果集对象
     */
    public static void close(Connection connection, PreparedStatement preparedStatement,  ResultSet resultSet) {
        try {
            // 关闭顺序与创建顺序相反
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("DbHelper closeAll err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
        }
    }
}
