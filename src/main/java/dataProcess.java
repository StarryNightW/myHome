import java.io.File;
import java.util.ArrayList;

/*对从卫健委采集的数据进行预处理D:\workspace\covid19_sta\rawData，处理内容包括：
1.去掉空格、空行
2.标点符号转换，将。，；：全部转换为英文状态下的,
3.处理完毕的数据写入D:\workspace\covid19_sta\newData
* */
public class dataProcess {
    public static ArrayList<String> getFilePath(String foldName){
        File dirFile = new File(foldName);
        ArrayList<String> disStr = new ArrayList<String>();
        if(dirFile.exists()){
            File files[] = dirFile.listFiles();
            for(File file:files){
                if(foldName.endsWith(File.separator)){
                    disStr.add(dirFile.getPath()+file.getName());
                }else{
                    disStr.add(dirFile.getPath()+File.pathSeparator+file.getName())
                }
            }
        }
    }
}
