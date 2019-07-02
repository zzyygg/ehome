package cn.ehome.blockchain.main;

import cn.ehome.blockchain.pojo.BlockChain;
import cn.ehome.blockchain.server.HttpServer;
import cn.ehome.blockchain.server.P2PServer;

public class Main {
    public static void main(String[] args) {
        if (args != null && (args.length == 2 || args.length == 3)) {
            try {
                //http 端口 http服务的端口
                int httpPort = Integer.valueOf(args[0]);
                // websoket端口 p2p服务的端口
                int p2pPort = Integer.valueOf(args[1]);

                BlockChain blockService = new BlockChain();

                P2PServer p2pService = new P2PServer(blockService);
                //websoket服务初始化
                p2pService.initP2PServier(p2pPort);
                Thread.sleep(3000);
                //节点初始化的时候，要去连接其他节点，去同步区块链数据
                if (args.length == 3 && args[2] != null) {
                    p2pService.connectToPeer(args[2]);
                }
                //初始化httpserver的一个实例
                HttpServer httpServer = new HttpServer(blockService, p2pService);
                //传入端口，初始化http服务
                httpServer.initHTTPServer(httpPort);
            } catch (Exception e) {
                System.out.println("启动失败:" + e.getMessage());
            }
        } else {
            System.out.println("启动方式如: java -jar blockchain.jar 8080 7001");
        }
    }
}
