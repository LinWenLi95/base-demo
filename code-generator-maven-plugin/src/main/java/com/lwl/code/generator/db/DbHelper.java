package com.lwl.code.generator.db;

import com.alibaba.fastjson.JSONObject;
import com.lwl.code.generator.config.JdbcConfig;
import com.lwl.code.generator.util.YamlConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 数据库操作辅助类
 * @author Admin
 */
public class DbHelper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
    
    private Connection connection = null;
    private static JdbcConfig jdbcConfig = YamlConfigUtil.getJdbcConfig();

    static {
        // 验证jdbc驱动是否存在
        try {
            Class.forName(jdbcConfig.getDriverClass());
        } catch (ClassNotFoundException var1) {
            LOGGER.error("DbHelper load DriverClass:{},not found", jdbcConfig.getDriverClass());
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    private Connection getConnection() {
        // 是否需要创建连接
        boolean createConn = false;
        if (connection == null) {
            createConn = true;
        } else {
            try {
                createConn = connection.isClosed();
            } catch (SQLException e) {
                LOGGER.error("DbHelper getConnection err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            }
        }
        if (createConn) {
            try {
                this.connection = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUserName(), jdbcConfig.getPassword());
            } catch (SQLException var2) {
                LOGGER.error("DbHelper getConnection err:" + var2.getMessage() + ",sqlState=" + var2.getSQLState());
            }
        }
        return this.connection;
    }

    /**
     * 查询所有表名
     * @param sql 查询语句
     * @param params 条件
     * @return List<String>
     */
    public List<String> queryTableNameList(String sql, Object... params) {
        return executeQuery(rs -> {
            List<String> list = new ArrayList<>();
            if (rs == null) {
                return list;
            }
            try {
                while (rs.next()) {
                    list.add(rs.getString(1));
                }
            } catch (SQLException var13) {
                LOGGER.error("DbHelper queryList1Object err:" + var13.getMessage() + ",sqlState=" + var13.getSQLState());
            }
            return list;
        },sql, params);
    }

    /**
     * 获取指定表信息
     * @param sql sql语句
     * @param justIncludes 仅需的字段
     * @param tableName 表名 查询条件
     * @return Map<String, String>
     */
    public List<JSONObject> queryTableInfoByTableName(String sql, String[] justIncludes, Object... tableName) {
        return executeQuery(rs -> {
            if (rs == null) {
                return null;
            }
            List<JSONObject> list = new ArrayList<>();
            try {
                // 数组转列表
                List<String> includeList;
                if (null == justIncludes || justIncludes.length == 0) {
                    includeList = new ArrayList<>();
                } else {
                    includeList = Arrays.asList(justIncludes);
                }
                // 取得符合条件的数据
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 1; i <= columnCount; i++) {
                        if (includeList.isEmpty()) {
                            jsonObject.put(metaData.getColumnLabel(i), rs.getString(i));
                        } else if (includeList.contains(metaData.getColumnLabel(i))) {
                            jsonObject.put(metaData.getColumnLabel(i), rs.getString(i));
                        }
                    }
                    list.add(jsonObject);
                }
            } catch (SQLException e) {
                LOGGER.error("DbHelper queryList1Object err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            }
            return list;
        }, sql, tableName);
    }


    /**
     * 查询列表
     * @param function 函数式编程接口 对结果集进行处理
     * @param sql 查询语句
     * @param params 查询条件
     * @return List<T>
     */
    private <T> T executeQuery(Function<ResultSet, T> function, String sql, Object... params) {
        Connection connection;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            // 获取连接并执行查询
            connection = getConnection();
            if (connection == null) {
                return null;
            }
            preparedStatement = connection.prepareStatement(sql);
            parameterInit(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("DbHelper queryList1Object err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            this.close(preparedStatement);
            return null;
        }
        // 函数式编程 对结果集进行处理
        T data = function.apply(resultSet);
        this.close(resultSet);
        this.close(preparedStatement);
        return data;
    }

    /**
     * 将查询参数放入预编译对象
     * @param preparedStatement 预编译对象
     * @param params 查询参数
     * @throws SQLException SQL异常
     */
    private void parameterInit(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(1 + i, params[i]);
                System.out.println(preparedStatement.toString());
            }
        }
    }

    /**
     * 关闭结果集对象
     * @param rs 结果集对象
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("DbHelper closeAll err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            }
        }
    }

    /**
     * 关闭SQL预处理对象
     * @param preparedStatement SQL预处理对象
     */
    private void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.error("DbHelper closeAll err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            }
        }
    }

    /**
     * 关闭数据库连接
     * @param connection 数据库连接对象
     */
    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("DbHelper closeAll err:" + e.getMessage() + ",sqlState=" + e.getSQLState());
            }
        }
    }

    /**
     * 数据库操作对象关闭
     * @param connection 数据库连接对象
     * @param preparedStatement SQL语句预编译对象
     * @param resultSet 结果集对象
     */
    public static void closeAll(Connection connection, PreparedStatement preparedStatement,  ResultSet resultSet) {
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

    /**
     * 关闭连接
     */
    public void closeAll() {
        close(connection);
    }

    /**********************下方备用方法***********************/

    private void close(CallableStatement callableStatement) {
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException var3) {
                LOGGER.error("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
        }

    }

    public int update(String sql, Object... params) {
        int effectedLine = 0;
        PreparedStatement preparedStatement = null;

        try {
            this.connection = this.getConnection();
            preparedStatement = this.connection.prepareStatement(sql);
            effectedLine = preparedStatement.executeUpdate();
        } catch (SQLException var9) {
            LOGGER.error("DbHelper exeUpdate err:" + var9.getMessage() + ",sqlState=" + var9.getSQLState());
        } finally {
            this.close(preparedStatement);
        }

        return effectedLine;
    }

    private ResultSet query(String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            this.connection = this.getConnection();
            preparedStatement = this.connection.prepareStatement(sql);
            this.parameterInit(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException var9) {
            LOGGER.error("DbHelper exeUpdate err:" + var9.getMessage() + ",sqlState=" + var9.getSQLState());
        } finally {
            this.close(preparedStatement);
        }

        return resultSet;
    }

}
