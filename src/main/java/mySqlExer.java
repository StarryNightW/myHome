import java.sql.ResultSet;
import java.sql.SQLException;

public class mySqlExer {
    public static void inserData(String sql) {
        mySqlHelper sqlDb = new mySqlHelper(sql);
        //ResultSet ret = null;
        try {
            sqlDb.pst.executeUpdate(sql);
            //System.out.println("插入成功");
            //ret.close();
            sqlDb.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*public static void main(String ars[]) {
        inserData("insert into confirm_detail(confirm_number,sex,age,address) values('确诊病例24','女','28岁','藁城区增村镇黄家庄村人')");
    }*/

}

