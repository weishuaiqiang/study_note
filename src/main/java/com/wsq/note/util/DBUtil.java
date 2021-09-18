package com.wsq.note.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    // 1.得到配置文件对象
    private static Properties properties = new Properties();

    static {
        try {
            // 2.加载配置文件 （输入流）
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            // 3.通过load()方法将输入流的内容加载到配置文件对象中
            properties.load(in);
            // 4.通过配置文件对象的getProperty()方法获取驱动名，并加载驱动
            Class.forName(properties.getProperty("jdbcName"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return 返回Connection对象
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // 5.得到数据库连接的相关信息
            String dbUrl = properties.getProperty("dbUrl");
            String dbName = properties.getProperty("dbName");
            String dbPwd = properties.getProperty("dbPwd");
            // 6.得到数据库连接
            connection = DriverManager.getConnection(dbUrl,dbName,dbPwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    /**
     * 关闭以下三个资源
     * @param resultSet 结果集
     * @param preparedStatement 预编译对象
     * @param connection 数据库连接对象
     */
    public static void close(ResultSet resultSet,
                             PreparedStatement preparedStatement,
                             Connection connection) {
        // 7.判断资源对象如果不为空，则关闭
        try{
            if(resultSet != null){
                resultSet.close();
            }
            if(preparedStatement != null){
                preparedStatement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
