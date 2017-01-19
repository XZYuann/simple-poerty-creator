package Main;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

/**
 * create by Intellij IDEA
 * Author: Al-assad
 * E-mail: yulinying@1994.com
 * Github: https://github.com/Al-assad
 * Date: 2017/1/13 10:42
 * Description:
 * 对源诗句素材进行分词，词语分解为2字词、3字词两种种类，分别储存在style2.txt，style3.txt中；
 *
 * @TODO
 * 后期将词项单元同时储存为 {词项名，词数，平仄，尾音韵母，风格倾向} 数据结构，持久储存为JSON，XML或数据库文件；
 */
public class Segmentation {

    private static String srcFile = "./corpus_simple/Poetry_src.txt";
    private static String outFile1 = "./corpus_simple/segmentation_1.txt";
    private static String outFile2 = "./corpus_simple/segmentation_2.txt";

    //使用HashSet记录词项，避免重复项的产生；
    private HashSet<String> set1 = new HashSet<String>();  //记录2词项
    private HashSet<String> set2 = new HashSet<String>();  //记录3词项

    public Segmentation(String srcFile, String outFile1,String outFile2){    //outFile1储存style2类型词项，outFile储存style3词项；

        try{
            //读取源文件，逐行读取，分不同词数词项 添加得到两个HashSet；
            BufferedReader reader = new BufferedReader(new FileReader(srcFile));
            String lineStr = "";
            while((lineStr = reader.readLine())!=null){
                lineStr = lineStr.trim();
                //以下分为行诗句为5言，7言的情况；
                if(lineStr.length() == 7){
                    String str1 = lineStr.substring(0,2);
                    String str2 = lineStr.substring(2,4);
                    String str3 = lineStr.substring(4,7);
                    set1.add(str1);
                    set1.add(str2);
                    set2.add(str3);
                }else if(lineStr.length() == 5){
                    String str1 = lineStr.substring(0,2);
                    String str2 = lineStr.substring(2,5);
                    set1.add(str1);
                    set2.add(str2);
                }else{
                    continue;
                }
            }
            reader.close();

            //将set1，set2的内容写入两个输出文件中
            PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(outFile1)));
            PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter(outFile2)));
            Iterator<String> iter1 = set1.iterator();
            Iterator<String> iter2 = set2.iterator();
            while(iter1.hasNext())
                out1.println(iter1.next());
            while(iter2.hasNext())
                out2.println(iter2.next());
            out1.close();
            out2.close();

            System.out.println("poetry segmentation finished!");

        }catch(IOException ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args){
        new Segmentation(srcFile,outFile1,outFile2);
    }
}
