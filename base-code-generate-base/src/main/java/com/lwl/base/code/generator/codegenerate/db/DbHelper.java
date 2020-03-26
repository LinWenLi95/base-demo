package com.lwl.base.code.generator.codegenerate.db;

import com.lwl.base.code.generator.codegenerate.CodeGenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * 数据库操作辅助类
 */
public class DbHelper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DbHelper.class);
    
    private Connection connection = null;

    static {
        try {
            JdbcConfig jdbcConfig = CodeGenConfig.getJdbcConfig();
            String driverClass = jdbcConfig.getDriverClass();
            Class<?> aClass = Class.forName(driverClass);
        } catch (ClassNotFoundException var1) {
            LOGGER.error("DbHelper load DriverClass:{},not found", CodeGenConfig.getJdbcConfig().getDriverClass());
        }

    }

    private Connection getConnection() {
        JdbcConfig jdbcConfig = CodeGenConfig.getJdbcConfig();
        try {
            this.connection = DriverManager.getConnection(jdbcConfig.getUrl(), jdbcConfig.getUserName(), jdbcConfig.getPassword());
        } catch (SQLException var2) {
            LOGGER.error("DbHelper getConnection err:" + var2.getMessage() + ",sqlState=" + var2.getSQLState());
        }

        return this.connection;
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

    public List<Object> querySingleColumn(String sql, Object... params) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int columnCount = 0;

        try {
            this.connection = this.getConnection();
            preparedStatement = this.connection.prepareStatement(sql);
            this.parameterInit(preparedStatement, params);
            rs = preparedStatement.executeQuery();
            columnCount = rs.getMetaData().getColumnCount();
        } catch (SQLException var12) {
            LOGGER.error("DbHelper queryList1Object err:" + var12.getMessage() + ",sqlState=" + var12.getSQLState());
        }

        ArrayList list = new ArrayList();

        try {
            while(rs.next()) {
                for(int i = 1; i <= columnCount; ++i) {
                    list.add(rs.getObject(1));
                }
            }
        } catch (SQLException var13) {
            LOGGER.error("DbHelper queryList1Object err:" + var13.getMessage() + ",sqlState=" + var13.getSQLState());
        } finally {
            this.close(rs);
            this.close(preparedStatement);
        }

        return list;
    }

    public List<Map<String, Object>> queryList(String sql, Object... params) {
        return this.queryList(sql, (String[])null, params);
    }

    public List<Map<String, Object>> queryList(String sql, String[] justIncludes, Object... params) {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        int columnCount = 0;
        ResultSetMetaData rsmd = null;

        try {
            this.connection = this.getConnection();
            preparedStatement = this.connection.prepareStatement(sql);
            this.parameterInit(preparedStatement, params);
            rs = preparedStatement.executeQuery();
            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException var16) {
            LOGGER.error("DbHelper queryList err:" + var16.getMessage() + ",sqlState=" + var16.getSQLState());
        }

        ArrayList list = new ArrayList();

        try {
            ArrayList includeList = new ArrayList(Arrays.asList(null == justIncludes ? new String[0] : justIncludes));

            while(rs.next()) {
                Map<String, Object> map = new HashMap();

                for(int i = 1; i <= columnCount; ++i) {
                    if (!includeList.isEmpty() && includeList.contains(rsmd.getColumnLabel(i))) {
                        map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                    }

                    if (includeList.isEmpty()) {
                        map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                    }
                }

                list.add(map);
            }
        } catch (SQLException var17) {
            LOGGER.error("DbHelper queryList err:" + var17.getMessage() + ",sqlState=" + var17.getSQLState());
        } finally {
            this.close(rs);
            this.close(preparedStatement);
        }

        return list;
    }

    public Object callProc(String sql, int outParamPos, int SqlType, Object... params) {
        Object object = null;
        this.connection = this.getConnection();
        CallableStatement callableStatement = null;

        try {
            callableStatement = this.connection.prepareCall(sql);
            this.parameterInit(callableStatement, params);
            callableStatement.registerOutParameter(outParamPos, SqlType);
            callableStatement.execute();
            object = callableStatement.getObject(outParamPos);
        } catch (SQLException var11) {
            LOGGER.error("DbHelper callProc err:" + var11.getMessage() + ",sqlState=" + var11.getSQLState());
        } finally {
            this.close(callableStatement);
        }

        return object;
    }

    private void parameterInit(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null) {
            for(int i = 0; i < params.length; ++i) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException var3) {
                LOGGER.error("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
        }

    }

    private void close(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException var3) {
                LOGGER.error("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
        }

    }

    private void close(CallableStatement callableStatement) {
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException var3) {
                LOGGER.error("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
        }

    }

    private void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException var3) {
                LOGGER.error("DbHelper closeAll err:" + var3.getMessage() + ",sqlState=" + var3.getSQLState());
            }
        }

    }

    public void closeAll() {
        this.close(this.connection);
    }


}
