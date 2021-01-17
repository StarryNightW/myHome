import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/*
* 对处理后的数据进行格式化处理与抽取，将病例时间、病例号、性别、年龄、所在区域、轨迹描述结构化存入数据库中
* */
public class factorExtract {
    //该方法用于从处理后的txt中读取数据，对数据分割
    public void covertMap(String foldPath){
        ArrayList<String> files = new dataProcess().getFilePath(foldPath);
        for(String file:files){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file)),"UTF-8"));
                String lineTxt = null;
                lineTxt = br.readLine();
                String confirm_detail = null;
                String sex= null;
                String age= null;
                String address= null;

                while(lineTxt!=null){
                    String[] splitLine = lineTxt.split(",");
                    for(int i=0;i<=3;i++){
                        if(i == 0){
                            confirm_detail = splitLine[i];
                        }
                        else if(i == 1){
                            sex = splitLine[i];
                        }
                        else if(i ==2 ){
                            age = splitLine[i];
                        }
                        else if(i == 3){
                            address = splitLine[i];
                        }
                    }
                    String sql = "insert into confirm_detail(confirm_number,sex,age,address) values('"+confirm_detail+"','"+sex+"','"+age+"','"+address+"')";
                    mySqlExer.inserData(sql);
                    lineTxt = br.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(file+"  插入完成");
        }
        System.out.println("任务结束");

    }
}
