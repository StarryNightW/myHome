import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * mysql统一数据处理工具，包括数据写入与查询
 * */
public class mySqlHelper {
    public static final String url = "jdbc:mysql://127.0.0.1/covid19_sjz?serverTimezone = GMT";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    //isi密码：12345678，myPC 密码：123456
    public static final String password = "12345678";
    public Connection conn = null;
    public PreparedStatement pst = null;

    public mySqlHelper(String sql) {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
            pst = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
