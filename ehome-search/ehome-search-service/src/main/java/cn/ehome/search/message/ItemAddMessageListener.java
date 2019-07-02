package cn.ehome.search.message;

import cn.ehome.common.pojo.SearchItem;
import cn.ehome.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * @author:Jun
 * @time:2019/4/3
 */
public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待添加的商品插入数据库
            Thread.sleep(1000);
            System.out.println("接收到消息itemid="+itemId);
            SearchItem searchItem = itemMapper.getItemById(itemId);
            System.out.println(searchItem==null);
            System.out.println(searchItem.getId()+"---"+searchItem.getTitle()+"----"+searchItem.getSell_point()+"----"+
                    searchItem.getPrice()+"----"+searchItem.getImage()+"-----"+searchItem.getCategory_name());
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            solrServer.add(document);
            solrServer.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
