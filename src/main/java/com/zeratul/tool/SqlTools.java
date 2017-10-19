package com.zeratul.tool;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zeratul.config.EnvConfig;
import com.zeratul.config.SqlConfig;
import com.zeratul.config.TestEnv;
import com.zeratul.exception.ParameterException;
import com.zeratul.exception.QueryDataException;
import com.zeratul.util.Constants;
import org.assertj.db.type.Request;
import org.assertj.db.type.Source;
import org.testng.Reporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库链接工具
 * @author dreamyao
 * @version 1.0.0
 *          Created on 2017/10/19 21:40
 */
public class SqlTools {

    private String ip = "";
    private String dbPassword = "";
    private String dbUser = "";
    private Connection connection;
    private List<Request> requestList = Lists.newArrayList();
    private List<Map<String, Object>> mapList = Lists.newArrayList();
    private static final SqlTools INSTANCE = new SqlTools();

    private SqlTools() {
        TestEnv env = EnvConfig.newInstance().getEnv();
        Map<String, String> config = SqlConfig.getConfig(env);
        ip = config.get("ip");
        dbPassword = config.get("password");
        dbUser = config.get("user");
    }

    public static SqlTools newInstance() {
        return INSTANCE;
    }

    public SqlTools connect(String database) {
        if (database == null || "".equals(database)) {
            throw new ParameterException("数据库名不能为空！");
        }
        try {
            if (connection != null) {
                connection.close();
            }
            Class.forName(Constants.DRIVER_CLASS_NAME).newInstance();
            String connectString = "jdbc:mysql://" + ip + "/" + database + "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=round&useSSL=false";
            connection = DriverManager.getConnection(connectString, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 执行 U（更新）I（插入）D（删除）操作
     * @param sql Sql语句
     * @return 当前类的引用
     */
    public SqlTools execute(String sql, Object... params) {
        if (connection == null) {
            throw new QueryDataException("没有建立数据库连接不能进行数据库操作！");
        }
        excuteSql(connection, sql, params);
        return this;
    }

    /**
     * 执行 S（查询）操作
     * @param sql 查询Sql
     * @return 当前类的引用
     */
    public SqlTools query(String sql, Object... params) {
        try {
            querySql(connection, mapList, sql, params);
        } catch (SQLException e) {
            Reporter.log("[NewSqlTools#query()]:{} ---> 数据查询异常！", true);
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取查询结果的封装对象
     * @param querySql   Sql语句
     * @param params Sql语句参数
     * @return 获取查询结果的封装对象
     */
    public SqlTools request(String querySql, Object... params) {
        if (connection == null) {
            throw new QueryDataException("没有建立数据库连接不能进行数据库操作！");
        }
        try {
            String url = connection.getMetaData().getURL();
            String username = connection.getMetaData().getUserName();
            Source source = new Source(url, dbUser, dbPassword);
            requestList.add(new Request(source, querySql, params));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取结果
     * @return 查询Sql产生的结果
     */
    public <T> List<T> collect(T t) {
        disconnect();
        return toList(t, mapList, requestList);
    }

    public static Map<String, Object> toMap() {
        return Maps.newHashMap();
    }

    public static Request toRequest() {
        return new Request();
    }

    /**
     * 关闭数据库连接
     */
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void excuteSql(Connection connection, String sql, Object[] params) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                pstmt.executeUpdate();
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void querySql(Connection connection, List<Map<String, Object>> list, String sql, Object[] params) throws SQLException {
        if (connection == null) {
            throw new QueryDataException("没有建立数据库连接不能进行数据库操作！");
        }
        PreparedStatement pstmt = connection.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
        ResultSet resultSet = pstmt.executeQuery();
        handleResultSet(list, resultSet);
        pstmt.close();
    }

    private void handleResultSet(List<Map<String, Object>> list, ResultSet resultSet) {
        try {
            if (resultSet != null && resultSet.last()) {
                resultSet.beforeFirst();
                ResultSetMetaData rsmd = resultSet.getMetaData();
                while (resultSet.next()) {
                    Map<String, Object> map = Maps.newHashMap();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        map.put(rsmd.getColumnName(i), resultSet.getString(i));
                    }
                    list.add(map);
                }
                resultSet.beforeFirst();
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> toList(T t, List<Map<String, Object>> mapList, List<Request> requestList) {
        if (Map.class.isInstance(t)) {
            List<Map<String, Object>> list = Lists.newArrayList();
            list.addAll(mapList);
            mapList.clear();
            return (List<T>) list;
        } else {
            List<Request> list = Lists.newArrayList();
            list.addAll(requestList);
            requestList.clear();
            return (List<T>) list;
        }
    }
}
