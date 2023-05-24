package org.eleven;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.eleven.constant.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Collections;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
@Slf4j
public class SpringBootApplicationTests {

    @Test
    public void test() {
        Object obj = "null";
        Assert.notNull(obj, "must be not null");
        ErrorCode.SERVER_ERROR.notNull(obj);
        // generator();
        log.info("test ok");
    }

    private void generator() {
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
        String username = "root";
        String password = "123456";
        String author = "lin3fei@126.com";
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder ->
                        builder.author(author)
                                .disableOpenDir()
                                .dateType(DateType.TIME_PACK)
                                .outputDir(projectPath + "/src/main/java")
                )
                // 包配置
                .packageConfig(builder ->
                        builder.parent("org.eleven")
                                .entity("model")
                                .pathInfo(Collections.singletonMap(OutputFile.xml,
                                        projectPath + "/src/main/resources/mapper/"))
                )
                // 策略配置
                .strategyConfig(builder ->
                        builder.addInclude("sys_user")
                                .addTablePrefix("sys_")
                                .entityBuilder()
                                .enableLombok()
                                .enableFileOverride()
                                .idType(IdType.ASSIGN_ID)
                                .disableSerialVersionUID()
                                .logicDeleteColumnName("deleted")
                                .mapperBuilder()
                                .mapperAnnotation(Mapper.class)
                                .enableFileOverride()
                )
                .templateConfig(builder -> builder.disable(TemplateType.CONTROLLER,
                        TemplateType.SERVICE, TemplateType.SERVICE_IMPL))
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
