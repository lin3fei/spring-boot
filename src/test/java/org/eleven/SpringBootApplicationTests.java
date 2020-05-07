package org.eleven;

import lombok.extern.slf4j.Slf4j;
import org.eleven.constant.ErrorCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootApplicationTests {

    @Test
    public void test() {
        log.info("test ok");
        Assert.notNull("a", "must be not null");
        ErrorCode.NO_AUTHORIZE.notNull("a");
    }

}
