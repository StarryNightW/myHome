import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.cfg.DefaultConfig;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.wltea.analyzer.dic.Dictionary;

public class addressProcess {
    public  Set<String> noMatchWords = new HashSet<String>();
    //根据IK进行分词，自行添加石家庄市、县、镇、乡四级信息作为扩展词典
    public List<String > getSegment(String sentence) throws IOException {
        List<String> result = new ArrayList<String>();
        StringReader sr = new StringReader(sentence);
        //测试一：IK分词
        IKSegmenter ik = new IKSegmenter(sr,true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            System.out.print(lex.getLexemeText()+"|");
            result.add(lex.getLexemeText());
        }
        return result;
    }

    //判断分词结果是否为一个地名，根据area数据表进行匹配
    public JSONObject checkIsAdd(String seg) throws SQLException {
        boolean checkFlag = false;
        int level;
        JSONObject checkResult = new JSONObject();
        checkResult.put("checkFlag",false);
        //String sql = "select level from area where name like '%"+seg+"%'";
        String joint_in_sql =jointInSql(seg);
        String sql = "select level from area where name in ( "+joint_in_sql+")";
        mySqlHelper sqlHelper = new mySqlHelper(sql);
        ResultSet rs = sqlHelper.pst.executeQuery(sql);
        List<Integer> levelList = new ArrayList<Integer>();

        while (rs.next()){
            checkResult.put("checkFlag",true);
            levelList.add(rs.getInt("level"));
        }
        if(levelList.size()!=0){
            Collections.sort(levelList);
            level = levelList.get(levelList.size()-1);
            checkResult.put("level",level);
        }
        else{
            noMatchWords.add(seg);
        }
        sqlHelper.close();
        return  checkResult;
    }
    public static String jointInSql(String seg){
        String result = null;
        result = "'"+seg+"','"+seg + "街道居委会','"+seg+"居委会','"+seg+"村委会','"+seg+"委会','"+seg+"村民委员会','"+seg+"街道','"+seg+"委会','"+seg+"街道办事处','"+seg+"办事处'";
        return result;
    }

/*
    public static void main(String args[]) throws IOException {

        String txt= "藁城区增村镇刘家佐村人其他";
        StringReader sr = new StringReader(txt);
      //测试一：IK分词
        IKSegmenter ik = new IKSegmenter(sr,true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            System.out.print(lex.getLexemeText()+"|");
        }
       // 测试2

*/
/*
        IKAnalyzer anal = new IKAnalyzer(true);
        Configuration cfg = DefaultConfig.getInstance();
        Dictionary.initial(cfg);
        TokenStream ts = anal.tokenStream("",sr);
        CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
        ts.reset();
        while(ts.incrementToken()){
            System.out.println(term.toString()+"|");
        }
        ts.close();
        sr.close();*//*

    }
*/

}
