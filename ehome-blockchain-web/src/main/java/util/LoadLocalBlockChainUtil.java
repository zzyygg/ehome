package util;

import cn.ehome.common.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import pojo.Block;
import pojo.BlockChain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadLocalBlockChainUtil {
    private static File file = new File(System.getProperty("user.home")+"/ehome-blocks/ehome-blocks.json");

//    public static List<Block> loadLocalBlocks() throws IOException {
//        FileReader reader = new FileReader(file.getAbsoluteFile());
//        BufferedReader bReader = new BufferedReader(reader);
//        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
//        String s = "";
//        List<Block> blockList = new ArrayList<Block>();
//        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
//            Block block = JsonUtil.jsonToPojo(s,Block.class);
//            blockList.add(block);
//        }
//        bReader.close();
//        return  blockList;
//    }
}
