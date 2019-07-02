package cn.ehome.blockchain.server;

import cn.ehome.blockchain.pojo.Block;
import cn.ehome.blockchain.pojo.BlockChain;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.java_websocket.WebSocket;

import com.alibaba.fastjson.JSON;

/**
 * @author:Jun
 * @time:2019/3/26
 */
public class HttpServer {
    /**
     * 区块链服务
	 */
    private static BlockChain blockService;
    /**
     * 点对点服务
     */
    private static P2PServer  p2pService;
    /**
     * 构造函数初始化
     * @param blockService
     * @param p2pService
     */
    public HttpServer(BlockChain blockService, P2PServer p2pService) {
        this.blockService = blockService;
        this.p2pService = p2pService;
    }
    /**
     * 初始化服务端口
     * @param port
     */
    public void initHTTPServer(int port) {
        try {
            Server server = new Server(port);
            System.out.println("<------------http服务端口: " + port+"------------>");
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/blockchain/");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
            context.addServlet(new ServletHolder(new BlocksServlet()), "/blocks");
            context.addServlet(new ServletHolder(new MineBlockServlet()), "/mineBlock");
            context.addServlet(new ServletHolder(new PeersServlet()), "/peers");
            context.addServlet(new ServletHolder(new AddPeerServlet()), "/addPeer");
            server.start();
            server.join();
        } catch (Exception e) {
            System.out.println("<------------初始化http服务失败:" + e.getMessage()+"------------>");
        }
    }

    private class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(JSON.toJSONString("ok"));
        }
    }

    private class BlocksServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(JSON.toJSONString(blockService.getBlockChain()));
        }
    }

    private class MineBlockServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doPost(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setCharacterEncoding("UTF-8");
            String data = req.getParameter("data");
            Block newBlock = blockService.generateNextBlock(data);
            System.out.println(JSON.toJSONString(newBlock));
            blockService.addBlock(newBlock);
            System.out.println("after add"+JSON.toJSONString(newBlock));
            p2pService.broatcast(p2pService.responseLastBlockMsg());
            String s = JSON.toJSONString(newBlock);
            System.out.println("after broadcast"+s);
            resp.getWriter().print(s);
        }
    }

    private class AddPeerServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            this.doPost(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setCharacterEncoding("UTF-8");
            String peer = req.getParameter("peer");
            /**
             * ws://127.0.0.1:7001
             */
//            String hostAdd = peer.split(":")[1].split("//")[1];
//            for (WebSocket socket : p2pService.getSockets()) {
//            	InetSocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
//            	if(remoteSocketAddress.getHostName()==hostAdd) {
//            		resp.getWriter().print("is aready connect");
//            	}
//            }
            System.out.println("peer");
            p2pService.connectToPeer(peer);
            resp.getWriter().print(JSON.toJSONString("ok"));
        }
    }

    private class PeersServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setCharacterEncoding("UTF-8");
            List<Map<String,String>> wsList = new ArrayList<Map<String,String>>();

            for (WebSocket socket : p2pService.getSockets()) {
                InetSocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
                Map<String,String> wsMap = new HashMap<String,String>();
                wsMap.put("remoteHost", remoteSocketAddress.getHostName());
                wsMap.put("remotePort", remoteSocketAddress.getPort()+"");
                wsList.add(wsMap);
//                resp.getWriter().print(remoteSocketAddress.getHostName() + ":" + remoteSocketAddress.getPort());
            }
            resp.getWriter().print(JSON.toJSONString(wsList));
        }
    }
//    public static void main(String[] args) {
//        BlockChain bcService = new BlockChain();
//        P2PServer p2pService = new P2PServer(bcService);
//        p2pService.initP2PServier(7001);
//        HttpServer hs = new HttpServer(bcService,p2pService);
//        hs.initHTTPServer(8080);
//    }
}
