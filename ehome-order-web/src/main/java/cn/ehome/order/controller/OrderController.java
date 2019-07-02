package cn.ehome.order.controller;

import cn.ehome.cart.service.CartService;
import cn.ehome.common.util.EhomeResult;
import cn.ehome.order.pojo.OrderInfo;
import cn.ehome.order.service.OrderService;
import cn.ehome.order.utils.AlipayConfig;
import cn.ehome.pojo.TbItem;
import cn.ehome.pojo.TbOrderItem;
import cn.ehome.pojo.TbUser;
import cn.ehome.service.ItemService;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author:Jun
 * @time:2019/4/23
 */
@Controller
public class OrderController {
    final static Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        //取用户id
        TbUser user= (TbUser) request.getAttribute("user");
        //根据用户id取收货地址列表
        //使用静态数据。。。
        //取支付方式列表
        //静态数据
        //根据用户id取购物车列表
        List<TbItem> cartList = cartService.getCartList(user.getId());
        //把购物车列表传递给jsp
        request.setAttribute("cartList", cartList);
        //返回页面
        return "order-cart";
    }

    @RequestMapping(value="/order/create", method= RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, HttpServletRequest request) {
        System.out.println(orderInfo.getBuyerNick());
        System.out.println(orderInfo.getOrderItems().get(0).getTitle());
        System.out.println(orderInfo.getOrderShipping().getReceiverAddress());
        //取用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //把用户信息添加到orderInfo中。
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //调用服务生成订单
        EhomeResult ehomeResult = orderService.createOrder(orderInfo);
        //如果订单生成成功，需要删除购物车
        if (ehomeResult.getStatus() == 200) {
            //清空购物车
            cartService.clearCartItem(user.getId());
        }
        //把订单号传递给页面
        request.setAttribute("orderId", ehomeResult.getData());
        request.setAttribute("payment", orderInfo.getPayment());
        //返回逻辑视图
        return "success";
    }

    /**
     * @Description: 前往支付宝第三方网关进行支付
     */
    @RequestMapping(value = "/order/goAlipay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String goAlipay(String orderId, HttpServletRequest request, HttpServletRequest response) throws Exception {
        System.out.println(orderId);
        TbOrderItem order = orderService.getOrderItemByOrderId(orderId);
        TbItem product = itemService.getItemById(Long.parseLong(order.getItemId()));
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderId;
        //付款金额，必填
        String total_amount = order.getTotalFee()/100+"";
        //订单名称，必填
        String subject = product.getTitle();
        //商品描述，可空
        String body = "用户订购商品个数：" + order.getNum();

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        /**
         * result:
         * <form
         *  name="punchout_form"
         *  method="post"
         *  action="https://openapi.alipaydev.com/gateway.do?charset=utf-8&method=alipay.trade.page.pay&sign=buw72dYFMgL3iAEISIaudl4VfVVzfadZ2hAPjHrdXUVhKidzLos1AAgZ6zr4ZWjT%2FOBPPIC5ZLZA2gWOu%2BbIqpl%2BlDTDFm16nc8SKp01DbGJZNjT3gNRmVtKuQy3bwmb5WqFcKW01kyIQf5F5s92J4MoocnB3ysjtKkGVfRrnEyKsKew7FpJ9ze7l9vKoyQMXqEmqSYqBJG4RmViyf7q9MbpO%2FPV5xWrhD08QdY8vKOBf69hSkVz6%2Fva3sIYCT8phUpYJ1e8fLoDzIo4QSHrJWkRXwF0EKfhFVHmEH9042MsCmpVnPTOvx%2BJF7CBXiexbXTvjBuRYfO4fX1bql2HgA%3D%3D&return_url=http%3A%2F%2Flocalhost%3A8080%2Falipay.trade.page.pay-JAVA-UTF-8%2Freturn_url.jsp&notify_url=http%3A%2F%2Flocalhost%3A8080%2Falipay.trade.page.pay-JAVA-UTF-8%2Fnotify_url.jsp&version=1.0&app_id=2016091300504420&sign_type=RSA2&timestamp=2018-07-03+15%3A27%3A08&alipay_sdk=alipay-sdk-java-3.1.0&format=json"
         * >
         * <input type="hidden" name="biz_content" value="{&quot;out_trade_no&quot;:&quot;180703B0RDC41SY8&quot;,&quot;total_amount&quot;:&quot;0.01&quot;,&quot;subject&quot;:&quot;冰棍&quot;,&quot;body&quot;:&quot;鐢ㄦ埛璁㈣喘鍟嗗搧涓暟锛?1&quot;,&quot;timeout_express&quot;:&quot;1c&quot;,&quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;}">
         * <input type="submit" value="立即支付" style="display:none" >
         * </form>
         */
        log.info("生成支付的form："+result);
        System.out.println("result="+result);
        return result;
    }

    /**
     *
     * @Description: 支付宝同步通知页面
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        System.out.println("支付宝同步通知页面");
        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        ModelAndView mv = new ModelAndView("alipaySuccess");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            System.out.println("Pay successed");
            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//            orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
//
//
//            Orders order = orderService.getOrderById(out_trade_no);
//            Product product = productService.getProductById(order.getProductId());

            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
//            log.info("* 购买产品: {}", product.getName());
            log.info("***************************************************************");


            mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
//            mv.addObject("productName", product.getName());

        }else {
            log.info("支付, 验签失败...");
        }

        return mv;
    }

    /**
     * @Description: 支付宝异步 通知页面
     */
    @RequestMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        System.out.println("支付宝异步 通知页面");
        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        // TODO

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//                orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);

//                Orders order = orderService.getOrderById(out_trade_no);
//                Product product = productService.getProductById(order.getProductId());

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
//                log.info("* 购买产品: {}", product.getName());
                log.info("***************************************************************");
            }
            log.info("支付成功...");

        }else {//验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }
}
