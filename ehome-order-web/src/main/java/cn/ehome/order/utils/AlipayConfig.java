package cn.ehome.order.utils;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016093000633203";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpYCebReJ4+f78HLM7XKAnpHHJNE52CrbNGUxwnG1FYxG+/I19cOccI+4tLGXi6it74Xgf2UPgKNDsqcmqltj0zOYVK0rCXou4r27nU5cOHjGtu8y9tlKgHqDenYwgBktgQSZ777yTDN9ysmoCOdQG8Q9lZ4xFAuiPs3E3TpasqLPTCIhlVVipKAqDJqr8aNCiZxJd7M3Wsa/HbUkgGc2eJnJt/n58EYtx40CNDivfa6bQB7y9ZEkBkXn1mAc3vVNFFrlBzqhtyLggaOmVANsYPOPOYRl0lnIkTEPXraTh/6AJk0y4WovhtBveyLUAzrBpiKJP0vI1NC3yQZzYRR8FAgMBAAECggEABZkN9g3LPGBW0xJHwjB/go/mytWVukUEYabyZc5ltiNeFMfYDUtL+Bwf5RJQk71VjpSXGcZefewba5b4+VBe/0g+WnjB0ca6K3Jw4ehbV7YbwjgwvhxmgrBAdNsUJILQvu+oS/RScTG9uNa4tjYfEdOpxJo1gppkAUvW9BL+29nxw97Ob3g6yAYi0QMciQJKpdIXOgn7L4otLXlu4tar8QSpLFOBntfgfSrpwl9Bqg9Qe4hTFQFNAFmXrRKgZFAYA+wk4Xlbey6cZENvWcernR7i2fy/9MzPeBC+rNXBD17BDjZYEsp9yFeShWSK+2xu+v5MY6f2qt/sX5k1oNK/wQKBgQDQsY3JRBNkTTOm5NByi3rnLbtWc04p78OJax0txiuE0CgkqhC2hWD+YryfGGZ+oTk1vA/gjRzxBf0MV7pZplV2rOOtytHONWSSsAOmJEHrKEwCTmcicyRLo5dHwt36QCsnEFsZaLqV4+5BABD3QU5AOMmwZLXG9qCXqrURKMnIOQKBgQDPxPy4PgR3SLMMfqubeGVTGsJNgvE6iHQXvww5GmWQdu8nHmHZpp24MX8p7oHZ/+nPDqwlTzMDot3wfie6dTgS9cmGMP2vXyWOSeLTuaquIUkN1hDmgyDNwa+yYVkEFmMyLnnhY6aDAwyqxUyd1zV1ON44XXr5ROOSjc2kQ+JVLQKBgC5a0fCbs2b7EvcGKhrXSnRhHxSDxh/ghuFWLoQFWZNFnTUPpWi1VacLjXQVOmb0Lr31yN0K2xvaRavhLJ5PKPqUSnpG67VO/MjsWdMkiJPQPpngil4Hj09Rxf+FlELK0Ar8Qg/xyEgU+c3sUtho5BW1x27r38iS7XbizxdBpRhZAoGAViogkJwPqBR7RMRHkOtmQgfH6vNR7W9nje2KOShMVJQubC+6btQddqT4UsB6E5mBMoCoPbTRuEFT/4eiHD6+qYZLNB6fJpqz7YWQzzOhqCNfs6BCVAavlQUtQzICA6LxMn95xlJYXN7n4u597ruwjNOvvPpvpG7AXif2z5fjhmUCgYEAgB3VP+VgWG552SJZQby/JsPPJZ2AV54mrxeKssAtu1WrWBgxVUpeuYKBIw3AiLEyfZIBQC6gd02rFEDUvycw/6rHfNEZ5B11ZMG1A2MOgVi1P5fEmGZ7sCTf99k3Xi/Yi9ZYR0IfU7QEmvSLaFjk5hF1cTqdKD+FYqeRgKyC1wc=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo61UYMTSmUbnBGv/V0A7NBWSo2UbefHge+YioFn6v5AxzajQqYQJzpSseZ5jlQZ+w2AfmSINwqycuYYEud3YMY6oV5EGKHSW1IRAuiQQeVCsZuTEZDI6J3e555XsI3q2LUUJxTfYV6l9xGWGRlVTTJdM9G9a+JTmvrMygRJuUCzUHfROKyv667zGCB5jKrsgj8+iDp8Lnyt16tcCLt8vAzyqPrR0DnkzrDbzBrsIoXZRjzVFPNTr/7/pCOKox/mgeWu3MMCo/B41TREHn7Fb7eF9rOasVx+gLO5DEDwvVwbllF46Ny36vYX3TsdUSpK90+S3KRuc6f1x9ghsQIp0ZwIDAQAB";
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo61UYMTSmUbnBGv/V0A7NBWSo2UbefHge+YioFn6v5AxzajQqYQJzpSseZ5jlQZ+w2AfmSINwqycuYYEud3YMY6oV5EGKHSW1IRAuiQQeVCsZuTEZDI6J3e555XsI3q2LUUJxTfYV6l9xGWGRlVTTJdM9G9a+JTmvrMygRJuUCzUHfROKyv667zGCB5jKrsgj8+iDp8Lnyt16tcCLt8vAzyqPrR0DnkzrDbzBrsIoXZRjzVFPNTr/7/pCOKox/mgeWu3MMCo/B41TREHn7Fb7eF9rOasVx+gLO5DEDwvVwbllF46Ny36vYX3TsdUSpK90+S3KRuc6f1x9ghsQIp0ZwIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "E:\\Alipay\\logs";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
