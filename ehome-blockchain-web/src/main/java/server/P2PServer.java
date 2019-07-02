package server;

import com.alibaba.fastjson.JSON;
import massage.Constant;
import massage.Message;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import pojo.Block;
import pojo.BlockChain;
import util.BlockFileUtil;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Jun
 * @time:2019/3/26
 */
public class P2PServer {

    private List<WebSocket> webSockets;
    private BlockChain blockService;

    public P2PServer(BlockChain blockService) {
        this.webSockets = new ArrayList<WebSocket>();
        this.blockService = blockService;
    }

    public void initP2PServier(int port){

        final WebSocketServer webSocketServer = new WebSocketServer(new InetSocketAddress(port)) {
            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
                System.err.println(webSocket.getRemoteSocketAddress()+"连接到本节点");
                if(!webSockets.contains(webSocket)) {
                    webSockets.add(webSocket);
                }
            }

            @Override
            public void onClose(WebSocket webSocket, int i, String s, boolean b) {
                System.out.println("<------------webSocketService onClose------------>" + webSocket.getRemoteSocketAddress());
                webSockets.remove(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, String s) {
                System.out.println("<------------webSocketService onMessage------------>"+s);
                handleMessage(webSocket, s);
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
                System.out.println("<------------webSocketServer onError------------>");
                webSockets.remove(webSocket);
            }

            @Override
            public void onStart() {
                System.err.println("<------------webSocketStart------------>");
            }
        };
        webSocketServer.start();
        System.out.println("<------------websocketServer本地监听端口: " + port+"------------>");
    }

    /**
     * 连接到客户端
     * @param peer
     */
    public void connectToPeer(final String peer){
        try {
            final WebSocketClient webSocketClient = new WebSocketClient(new URI(peer)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("<----------------webSocketClients onOpen------------------->");
                    webSockets.add(this);
                    write(this, requestALLBlocksMsg());
                }

                @Override
                public void onMessage(String s) {
                    System.err.println("<------------webSocketClient onMessage--------------->"+s);
                    handleMessage(this, s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("<------------webSocketClient onClose--------------->");
                    webSockets.remove(this);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("<------------webSocketClient onError---------------->");
                    webSockets.remove(this);
                }
            };
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            System.out.println("<------------webSocketClient URISyntaxtException------------>"+ e.getMessage());
        }
    }

    //发送消息方法
    private void write(WebSocket webSocket,String message){
        webSocket.send(message);
    }
    //请求所有区块Message
    private String requestALLBlocksMsg(){
        return JSON.toJSONString(new Message(Constant.REQUEST_ALLBLOCKS));
    }
    //响应"请求所有区块"
    private String responseAllBlocksMsg(){
        return JSON.toJSONString(new Message(Constant.RESPONSE_ALLBLOCKS, JSON.toJSONString(blockService.getBlockChain())));
    }

    //请求最后区块
    private String requestLastBlockMsg(){
        return JSON.toJSONString(new Message(Constant.REQUEST_LASTBLOCK));
    }

    //响应最后区块
    public String responseLastBlockMsg(){
        return JSON.toJSONString(new Message(Constant.RESPONSE_LASTBLOCK,JSON.toJSONString(blockService.getLatestBlock())));
    }

    /**
     * 消息处理函数
     * @param webSocket
     * @param s
     */
    private void handleMessage(WebSocket webSocket,String s){
        try {
            Message message = JSON.parseObject(s,Message.class);
            switch (message.getType()){
                case Constant.REQUEST_ALLBLOCKS:
                    write(webSocket,responseAllBlocksMsg());
                    break;
                case Constant.RESPONSE_ALLBLOCKS:
                    handleAllBlocksResponse(message.getData());
                    break;
                case Constant.REQUEST_LASTBLOCK:
                    write(webSocket,requestLastBlockMsg());
                    break;
                case Constant.RESPONSE_LASTBLOCK:
                    handleLastBlockResponse(message.getData());
                    break;
            }
        } catch (Exception e) {
            System.out.println("<------------消息处理错误------------>"+e.getMessage());
        }
    }

    /**
     * 处理接收到的区块链
     * @param data
     */
    private void handleAllBlocksResponse(String data){
        System.out.println("处理接收到的区块链:"+data);
        List<Block> receiveBlocks = JSON.parseArray(data, Block.class);
        System.out.println("接收到的区块链长度="+receiveBlocks.size());
        System.out.println("接收到的区块链数据="+JSON.toJSONString(receiveBlocks));
//        Collections.sort(receiveBlocks, new Comparator<Block>() {
//            @Override
//            public int compare(Block o1, Block o2) {
//                return o1.getIndex() - o2.getIndex();
//            }
//        });

        Block latestBlockReceived = receiveBlocks.get(receiveBlocks.size() - 1);
        Block latestBlock = blockService.getLatestBlock();

        if (latestBlockReceived.getIndex() > latestBlock.getIndex()) {
            Boolean isBeReplaceed = blockService.replaceChain(receiveBlocks);
            if(isBeReplaceed){
                for (int i = 1; i < receiveBlocks.size();i++)
                    BlockFileUtil.writeBlcok(receiveBlocks.get(i));
                broatcast(responseAllBlocksMsg());
            }
        }
    }

    /**
     * 处理接收到的最新区块
     * @param data
     */
    private void handleLastBlockResponse(String data){
        Block  blockReceived = JSON.parseObject(data,Block.class);
        Block  blockLocaled = blockService.getLatestBlock();
        if(blockReceived.getPreviousHash().equals(blockLocaled.getHash())){
            blockService.addBlock(blockReceived);
            BlockFileUtil.writeBlcok(blockReceived);
            broatcast(responseLastBlockMsg());
        }
    }

    /**
     * 如果本地链更新了就广播
     * @param message
     */
    public void broatcast(String message) {
        System.out.println("<------------广播中------------>");
        for (WebSocket socket : webSockets) {
            this.write(socket, message);
        }
    }

    public List<WebSocket> getSockets() {
        return webSockets;
    }

}
