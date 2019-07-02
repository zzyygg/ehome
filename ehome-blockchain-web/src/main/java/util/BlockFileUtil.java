package util;

import cn.ehome.common.util.JsonUtil;
import pojo.Block;
import pojo.BlockChain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BlockFileUtil {

    private static File file = new File(System.getProperty("user.home")+"/ehome-blocks/ehome-blocks.json");

    /**
     * @return 本地是否有ehome-blocks.json
     * @throws IOException
     */
    public static boolean  initJsonFile() throws IOException {
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
            file.createNewFile();
            writeBlcok((new BlockChain()).getLatestBlock());
            return false;
        }else{
            if(!file.exists()){
                file.createNewFile();
                writeBlcok((new BlockChain()).getLatestBlock());
                return false;
            }else{
                if(file.length() == 0){
                    writeBlcok((new BlockChain()).getLatestBlock());
                    return false;
                }
            }
        }
        return true;
    }

    public static BlockChain loadLocalBlocks(){
        try {

            if (!initJsonFile()) {
                System.out.println("initJsonFile return false");
                return null;
            }

            FileReader reader = new FileReader(file.getAbsoluteFile());
            BufferedReader bReader = new BufferedReader(reader);
            StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
            String s = "";
            List<Block> blockList = new ArrayList<Block>();
            while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
                Block block = JsonUtil.jsonToPojo(s, Block.class);
                blockList.add(block);
            }
            bReader.close();
            BlockChain blockChain = new BlockChain();
            blockChain.replaceChain(blockList);
            return  blockChain;
        }catch (IOException e){
            System.err.println("初始区块链出错");
        }

//        if(blockList.size()<2){
//            file.delete();
//            System.out.println("无效文件");
//            initJsonFile();
//        }


//        if(!blockChain.replaceChain(blockList)){
//            file.delete();
//            System.out.println("无效文件");
//            initJsonFile();
//            return null;
//        }
//        System.out.println("有效文件");
        return  new BlockChain();
    }

    public static void writeBlcok(Block block){
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            String s = JsonUtil.objectToJson(block);
            fileWriter.write(s + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void clearFile(){
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
