package cn.ehome.blockchain.pojo;

import java.util.ArrayList;
import java.util.List;

import cn.ehome.blockchain.util.CryptoUtil;
import com.alibaba.fastjson.JSON;


/**
 * @author Guoxiujun
 * @date 2019/1/28
 */
public class BlockChain {
	/**
	 * 区块链定义 其实就是一个list
	 */
    private List<Block> blockChain;
    /**
     * 区块链服务
     * 1、初始化blockchain
     * 2、区块链加入创世区块
     */
    public BlockChain() {
        this.blockChain = new ArrayList<Block>();
        blockChain.add(this.getFristBlock());
    }
   
    /**
     * 生成下一个区块
     * @param blockData
     * @return Block
     */
    public Block generateNextBlock(String blockData) {
    	//获得前一个区块
        Block previousBlock = this.getLatestBlock();
        Block newBlock = new Block();
        //区块的索引加1
        int nextIndex = previousBlock.getIndex() + 1;
        //现在的时间戳
        long nextTimestamp = System.currentTimeMillis();
//        long nextTimestamp = 1521339156921l;
        //计算hash值
//        String nextHash = calculateHash(nextIndex, previousBlock.getHash(), nextTimestamp, blockData);
//        return new Block(nextIndex, previousBlock.getHash(), nextTimestamp, blockData, nextHash);
        long nonce = 0;
        calculateIncludeNonceHash(nextIndex, previousBlock.getHash(), nextTimestamp, blockData,nonce,newBlock);
        //返回生成的新区块
        System.err.println("生成新区快:"+JSON.toJSONString(newBlock));
//        System.out.println("newBlock:"+newBlock);
        return newBlock;
    }

    /**
     * 获取最后一个区块
     * @return 返回区块内容
     */
    public Block getLatestBlock() {
        return blockChain.get(blockChain.size() - 1);
    }
    /**
     * while循环创建
     * @param index
     * @param previousHash
     * @param timestamp
     * @param data
     * @param nonce
     * @param newBlock
     * @return
     */
    private String calculateIncludeNonceHash(int index, String previousHash, long timestamp, String data,long nonce,Block newBlock) {
    	String str = index+previousHash+timestamp+data+nonce;
        System.out.println("index+previousHash+timestamp+data+nonce="+str);
    	String hash = CryptoUtil.getSHA256(str);
    	do {
    		nonce =	nonce +1;
        	str	= index+previousHash+timestamp+data+nonce;
        	hash = CryptoUtil.getSHA256(str);
        	if(isValidHashDifficulty(hash)) {
            	newBlock.setData(data);
            	newBlock.setHash(hash);
            	newBlock.setIndex(index);
            	newBlock.setNonce(nonce);
            	newBlock.setPreviousHash(previousHash);
            	newBlock.setTimestamp(timestamp);
        	}

    	}while(!isValidHashDifficulty(hash));
        System.err.println("nonce:"+nonce);
    	return hash;
    }
    /**
     * 检测是否符合难度要求，挖矿操作
     * @param hash
     * @return
     */
    private boolean isValidHashDifficulty(String hash) {
    	int dificutty = 4;
		char zero = '0';
		int i;
		for(i=0;i<hash.length();i++) {
			char ichar = hash.charAt(i);
			if(ichar!=zero) {
				break;
			}
		}
    	return i>=dificutty;
    }
    /**
     * 获取-创世区块
     * @return
     */
    private Block getFristBlock() {
//    	Block newBlock = new Block();
//    	calculateIncludeNonceHash(1, "0", 1521339156921l,"Hello Block",0,newBlock);
//    	return newBlock;
    	return new Block(1, "0", 1521339156921l, "Hello Block", "0000dfc2136e967aae82883ddaf29f0f197619301eed4cde5ce06ad324712b4e",17632);
    }

    /**
     * 添加新快
     * @param newBlock
     * @return 是否添加成功
     */
    public boolean addBlock(Block newBlock) {
        if (isValidNewBlock(newBlock, getLatestBlock())) {
            blockChain.add(newBlock);
            return true;
        }
        return false;
    }
    
    /**
     * 共识算法（最基本的共识算法，也就是说符合这四个条件的都能加入到区块链中）
     * 检查区块是否新区块
     * @param newBlock
     * @param previousBlock
     * @return true/false
     */
    private boolean isValidNewBlock(Block newBlock, Block previousBlock) {
    	
        if (previousBlock.getIndex() + 1 != newBlock.getIndex()) {//1、前一个区块的索引加1不等于新区块
            System.err.println(newBlock.getIndex());
        	System.out.println("无效的indexindex="+newBlock.getIndex());
            return false;
        } else if (!previousBlock.getHash().equals(newBlock.getPreviousHash())) {//前一个区块的hash不等于新区块中存储的前一个区块的hash
            System.out.println("无效的previousHash index="+newBlock.getIndex());
            return false;
        } else {
            //index+previousHash+timestamp+data+nonce
            String s = newBlock.getIndex()+newBlock.getPreviousHash()+newBlock.getTimestamp()+newBlock.getData()+newBlock.getNonce();
            String hash = CryptoUtil.getSHA256(s);
            //如果hash和计算后的hash不相等
            if (!hash.equals(newBlock.getHash())) {
                System.out.println("无效的hash: " + hash + " " + newBlock.getHash());
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * 2个节点：
     * 1 区块链长度 100
     * 2 区块链长度 101
     * 新区块链替换成+1的区块链
     * @param newBlocks
     */
    public boolean replaceChain(List<Block> newBlocks) {
        System.out.println(newBlocks.size()+">"+blockChain.size());
        //如果新区块链合法并且新区块链的长度大于现有区块链的长度，则替换
        if (newBlocks.size() > blockChain.size() && isValidBlocks(newBlocks)){
            blockChain = newBlocks;
            return true;
        } else {
            System.out.println("接收到区块链，但无需更新");
            return false;
        }
    }
    /**
     * 验证区块链是否合法
     * @param newBlocks
     * @return true/false
     */
    private boolean isValidBlocks(List<Block> newBlocks) {
        System.out.println("--------验证区块链是否合法-------");
    	//获得第一个区块链
        Block firstBlock = newBlocks.get(0);
        Block block = getFristBlock();
        //判断第一个区块是否是创世区块
        boolean isFirstBlock = (firstBlock.getIndex()== block.getIndex()) && (firstBlock.getPreviousHash().equals(block.getPreviousHash())) && (firstBlock.getTimestamp()==block.getTimestamp()) && (firstBlock.getData().equals(block.getData())) && (firstBlock.getNonce()==block.getNonce()) && (firstBlock.getHash().equals(block.getHash()));
        if(isFirstBlock == false){
            System.err.println("创始区块不合法！！！");
        }

        //循环每个验证区块是否合法
        for (int i = 1; i < newBlocks.size(); i++) {
            if (isValidNewBlock(newBlocks.get(i),newBlocks.get(i-1))) {
               continue;
            } else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 获得区块链
     * @return
     */
    public List<Block> getBlockChain() {
        return blockChain;
    }
    
//    public static void main(String[] args) {
//    	BlockChain bc = new BlockChain();
//
//    	Block secondBlock = bc.generateNextBlock("second Block");
//    	bc.addBlock(secondBlock);
//
//    	System.err.println("secondBlock-->"+JSON.toJSONString(secondBlock));
//    	Block threeBlock = bc.generateNextBlock("{\"order\":\"111111\"}");
//    	System.err.println("threeBlock-->"+JSON.toJSONString(threeBlock));
//    	bc.addBlock(threeBlock);
//    	List<Block> blockchains = bc.getBlockChain();
//
//    	System.err.println("blockchain--->"+JSON.toJSONString(blockchains));
////		System.err.println(bc.isValidHashDifficulty("0000dfc2136e967aae82883ddaf29f0f197619301eed4cde5ce06ad324712b4e"));
////		Block newBlock = new Block();
////		System.err.println(bc.calculateIncludeNonceHash(1,"0",1521339156921l,"Hello First Block",0l,newBlock));
////		System.err.println(JSON.toJSONString(bc.getFristBlock()));
//    }
}
