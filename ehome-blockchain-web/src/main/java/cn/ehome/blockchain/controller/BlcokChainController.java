package cn.ehome.blockchain.controller;

import cn.ehome.pojo.TbItem;
import cn.ehome.service.ItemService;
import com.alibaba.fastjson.JSON;
import org.java_websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pojo.Block;
import pojo.BlockChain;
import pojo.ProductInfo;
import server.P2PServer;
import util.BlockFileUtil;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class BlcokChainController implements MessageListener{
    @Value("${WEBSOCKET_SERVER_PORT}")
    private int WEBSOCKET_SERVER_PORT;

    private static BlockChain blockChain;
    private static P2PServer p2PServer;

    @Autowired
    private ItemService itemService;

    @PostConstruct
    public void inti() throws IOException{
        blockChain = BlockFileUtil.loadLocalBlocks();
        if(blockChain == null){
            blockChain = new BlockChain();
        }
        p2PServer = new P2PServer(blockChain);
        p2PServer.initP2PServier(WEBSOCKET_SERVER_PORT);
    }

    @RequestMapping(value = "/blocks")
    public String blocks(){
        return "blocks";
    }

    @RequestMapping(value="/getBlocks",method = {RequestMethod.GET})
    @ResponseBody
    public String getBlocks() throws IOException {
        List<Block> blockchain = BlockFileUtil.loadLocalBlocks().getBlockChain();
        return JSON.toJSONString(blockchain);
    }

    @RequestMapping(value = "/miner")
    public String miner(){
        return "miner";
    }

    @RequestMapping(value="/mineBlock",method = {RequestMethod.GET})
    @ResponseBody
    public String mineBlock(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String data = new String(request.getParameter("data").getBytes("iso-8859-1"), "utf-8");
        System.out.println(data);
        Block block = blockChain.generateNextBlock(data);
        blockChain.addBlock(block);
        BlockFileUtil.writeBlcok(block);
        System.out.println("写入区块成功");
        p2PServer.broatcast(p2PServer.responseLastBlockMsg());
        System.out.println("广播中");
        return JSON.toJSONString(block);
    }

    @RequestMapping(value = "/peer")
    public String peer(){
        return "peer";
    }

    @RequestMapping(value = "/getSockets")
    @ResponseBody
    public String getPeers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("peers");
        resp.setCharacterEncoding("UTF-8");
        List<String> list = new ArrayList<String>();
        for (WebSocket socket : p2PServer.getSockets()) {
            InetSocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            list.add("ws://"+remoteSocketAddress.getHostName()+":"+remoteSocketAddress.getPort());
        }
        System.out.println(JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/addPeer")
    public String addPeer(){
        return "addPeer";
    }

    @RequestMapping(value = "/addSocket",method = {RequestMethod.GET})
    @ResponseBody
    public String addPeer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        resp.setCharacterEncoding("UTF-8");
        String peer = req.getParameter("peer");
        System.out.println("peer");
        p2PServer.connectToPeer(peer);
        return JSON.toJSONString("ok");
    }

    @RequestMapping(value = "/search")
    public String search(){
        System.out.println("search");
        return "search";
    }

    @RequestMapping(value = "/searchBlocks",method = {RequestMethod.GET})
    @ResponseBody
    public String searchBlocks(HttpServletRequest request,HttpServletResponse response){
        System.out.println("searchBlocks");
        System.out.println("ItemID="+request.getParameter("data"));
        List<Block> list = searchBlockById(request.getParameter("itemID"));
        System.out.println(list.size());
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/searchItem")
    public String searchById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String itemID = new String(req.getParameter("itemID").getBytes("iso-8859-1"), "utf-8");
        System.out.println("查找区块："+itemID);
        List<Block> list = searchBlockById(itemID);
        System.out.println(list.size());
        req.setAttribute("blocks",list);
        return "searchItem";
    }

    List<Block> searchBlockById(String id){
        List<Block> list = new ArrayList<Block>();
        List<Block> blockchain = null;
        blockchain = BlockFileUtil.loadLocalBlocks().getBlockChain();
        for(Block block : blockchain){
            String data = block.getData();
            if(data.contains("id="+id)){
                list.add(block);
            }
        }
        return list;
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待添加的商品插入数据库
            Thread.sleep(1000);
            System.out.println("接收到消息itemid="+itemId);
            TbItem item = itemService.getItemById(itemId);
            System.out.println(item.getUpdated());
            System.out.println(item==null);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            ProductInfo info = new ProductInfo(item, "admin",df.format(new Date()));
//            String data = JsonUtil.objectToJson(info);
            Block block = blockChain.generateNextBlock(info.toString());
            blockChain.addBlock(block);
            BlockFileUtil.writeBlcok(block);
            System.out.println("写入区块成功");
            p2PServer.broatcast(p2PServer.responseLastBlockMsg());
            System.out.println("广播中");

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
