package com.hana;

import com.hana.client.GoodsClient;
import com.hana.pojo.Goods;
import com.hana.repository.GoodsRepository;
import com.hana.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HanaSearchApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsReponsitory;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex() {
        // 创建索引库，以及映射
        this.template.createIndex(Goods.class);
        this.template.putMapping(Goods.class);

    }


    @Test
    public void saveGoods() {

        // 创建索引库，以及映射
        this.template.createIndex(Goods.class);
        this.template.putMapping(Goods.class);

        Goods goods = new Goods();
        goods.setId(2l);
        goods.setCid1(2L);
        goods.setCid2(2L);
        goods.setCid3(2L);
        goods.setBrandId(2L);


    }

}