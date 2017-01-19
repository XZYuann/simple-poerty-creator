package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * create by Intellij IDEA
 * Author: Al-assad
 * E-mail: yulinying@1994.com
 * Github: https://github.com/Al-assad
 * Date: 2017/1/13 10:50
 * Description:
 * 简易诗句生成器，从语料库中获取相关素材，随机产生古诗；
 *
 * @TODO
 * 后期升级预料库后，根据词项单元的平仄，尾韵母，风格倾向等信息，重新编排古诗；
 * 诗句生成模式分为：5言绝句，5言律诗，7言绝句，7言律诗；
 */

public class SimpleCreator {

    private static String corpus1 = "./corpus_simple/segmentation_1.txt";
    private static String corpus2 = "./corpus_simple/segmentation_2.txt";

    private List<String> list1 = new ArrayList<String>();  //储存语料库
    private List<String> list2 = new ArrayList<String>();

    //构造函数，从两个语料库读取数据，装载到两个ArrayList中；
    public SimpleCreator(String corpus1,String corpus2){ //参数为两个语料库的文件地址
        try{
            BufferedReader in1 = new BufferedReader(new FileReader(new File(corpus1)));
            BufferedReader in2 = new BufferedReader(new FileReader(new File(corpus2)));
            String readStr = "";
            while((readStr = in1.readLine()) != null){
                readStr = readStr.trim();
                if(readStr == "")
                    continue;
                list1.add(readStr);
            }
            while((readStr = in2.readLine()) != null){
                readStr = readStr.trim();
                if(readStr == "")
                    continue;
                list2.add(readStr);
            }
            in1.close();
            in2.close();

        }catch(IOException ex){
            ex.printStackTrace();
        }

    }
    //产生一组 row * col 随机数组，查找ArraList产生随机诗句线性表
    public ArrayList<String> createPoerty(){
        return createPoerty(4,3);
    }
    public ArrayList<String> createPoerty(int row,int col) {
        ArrayList<String> lines = new ArrayList<String>();
        //产生一组随机数组
        int[][] randoms = new int[row][col];
        Random rand = new Random();
        for(int r=0;r<row;r++){
            for(int c=0;c<col-1;c++){
                randoms[r][c] = rand.nextInt(list1.size());
            }
            randoms[r][col-1] = rand.nextInt(list2.size());
        }
        //由该随机数组查找素材
        for(int r=0;r<row;r++){
            String line = "";
            for(int c = 0;c<col-1;c++){
                line += list1.get(randoms[r][c]);
            }
            line += list2.get(randoms[r][col-1]);
            lines.add(line);
        }

        return lines;

    }
    //打印该诗句
    public static void showPeotry(List<String> poerty){
        System.out.println(new Date().toString());
        for(int i=0;i<poerty.size();i++){
            System.out.println(poerty.get(i));
        }
        System.out.println();

    }

    //main
    public static void main(String args[]){
        final int SIZE = 10;
        SimpleCreator creator = new SimpleCreator(corpus1,corpus2);
        for(int i=0;i<SIZE;i++){
            //随机生成10个7言古诗
            List<String> poerty =creator.createPoerty(4,3);
            showPeotry(poerty);
        }

    }





}
