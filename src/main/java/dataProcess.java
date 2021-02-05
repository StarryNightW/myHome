import java.io.*;
import java.util.ArrayList;

/*对从卫健委采集的数据进行预处理D:\workspace\covid19_sta\rawData，处理内容包括：
1.去掉空格、空行
2.标点符号转换，将。，；：全部转换为英文状态下的,
3.处理完毕的数据写入D:\workspace\covid19_sta\newData
* */
public class dataProcess {
    //该方法用于获取指定文件夹下的所有文件名
    public ArrayList<String> getFilePath(String foldName) {
        File dirFile = new File(foldName);
        ArrayList<String> disStr = new ArrayList<String>();
        if (dirFile.exists()) {
            File files[] = dirFile.listFiles();
            for (File file : files) {
                if (foldName.endsWith(File.separator)) {
                    disStr.add(dirFile.getPath() + file.getName());
                } else {
                    disStr.add(dirFile.getPath() + File.separator + file.getName());
                }
            }
        }
        return disStr;
    }

    //该方法用于读取一个txt中的内容并进行清洗，将清洗后的数据写入新txt文件中。
    public static void formatData(String filePath) {
        String[] splitPath = filePath.split("\\\\");
        String fileName = splitPath[splitPath.length - 1];
       // String folder = "D:\\idea_java\\covid19_sta\\newData";
        String folder = "D:\\data\\covid19_sta\\newData";
        String newFileName = folder + "\\" + fileName;
        //File newFile = new File(newFileName);
        if (!judgeFileExit(newFileName,folder)) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader
                        (new FileInputStream(new File(filePath)), "UTF-8"));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(newFileName), true),
                        "UTF-8"));
                String lineTxt = null;
                lineTxt = br.readLine();
                while (lineTxt != null) {
                    if (!(lineTxt.trim().equals(""))) {
                        //System.out.println(lineTxt);
                        String line = lineTxt.replace(" ", "").replace("。", ",").replace("，", ",").replace("：", ",");
                        //System.out.println(line);
                        bw.write(line);
                        bw.newLine();
                        bw.flush();
                        System.out.println("已写入文件："+newFileName);
                    }
                    lineTxt = br.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    //该方法用于判断某文件夹下（files[]）是否存在文件名为fileName的文件
    public static  boolean judgeFileExit(String fileName, String folder) {
        File folderFile = new File(folder);
        File[] files = folderFile.listFiles();
        boolean flag = false;
        for (File file : files) {
            String curreniltFileName = folderFile.getPath() + File.separator + file.getName();
            if (fileName.equals(curreniltFileName)) {
                flag = true;
                return flag;
            }

        }
        return flag;
    }

    //运行该main方法
    public static void main(String args[]) throws IOException {
        dataProcess test = new dataProcess();
        String isiFoldPathRaw = "D:\\data\\covid19_sta\\rawData";
        String isiFoldPathNew = "D:\\data\\covid19_sta\\newData";
        //String myPCPath = "";
       /* ArrayList<String> testResult = test.getFilePath(isiFoldPathRaw);
        String firstFile = testResult.get(0);
        for (String file : testResult) {
            formatData(file);
        }*/
       // System.out.println(judgeFileExit("D:\\idea_java\\covid19_sta\\newData\\0105.txt","D:\\idea_java\\covid19_sta\\newData"));

        /*BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\idea_java\\covid19_sta\\newData\\1014.txt"))));
        bw.write("test");
        bw.flush();*/
        //String foldPath = "D:\\idea_java\\covid19_sta\\newData";
       // String foldPath = "D:\\data\\covid19_sta\\rawData";
        factorExtract fe = new factorExtract();
        fe.covertMap(isiFoldPathNew);
    }
}
