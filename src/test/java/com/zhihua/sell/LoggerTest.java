package com.zhihua.sell;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public  void  test() {

        String name = "imooc";
        String password = "123456";
        /*log.info("name = {} , password = {}",name,password);
        log.warn("warn...");
        log.error("error...");*/
        logger.debug("debug...");
        logger.info("name = {} , password = {}",name,password);
        logger.warn("warn...");
        logger.error("error...");

    }
}
