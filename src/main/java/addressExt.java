import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class addressExt {
    public static void main(String args[]){
        String sql = "select auto_id,address from confirm_detail";
        mySqlHelper sqlDb = new mySqlHelper(sql);
        addressProcess ap = new addressProcess();
        try {
            ResultSet rs = sqlDb.pst.executeQuery(sql);
            while (rs.next()){
                String address = rs.getString("address");
                int pid = rs.getInt("auto_id");
                List<String > address_segment = ap.getSegment(address);
                for(String add:address_segment){
                    JSONObject checkResult =ap.checkIsAdd(add);
                    boolean flag = checkResult.getBooleanValue("checkFlag");
                    if(flag){
                        int level = checkResult.getInteger("level");
                        String insert_sql = "insert into ext_info(pid,location,level) values ('"+pid+"','"+add+"','"+level+"')";
                        mySqlHelper insertDB = new mySqlHelper(insert_sql);
                        insertDB.pst.executeUpdate(insert_sql);
                        insertDB.close();
                    }
                }
                System.out.println(pid);
            }
            sqlDb.close();
            System.out.println("-------------任务结束---------");
            Set<String> validation =ap.noMatchWords;
            Iterator iterator = validation.iterator();
            while(iterator.hasNext()){
                System.out.println(iterator.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
