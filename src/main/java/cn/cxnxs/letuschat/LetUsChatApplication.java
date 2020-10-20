package cn.cxnxs.letuschat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.cxnxs.letuschat.mapper")
@EnableTransactionManagement
public class LetUsChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetUsChatApplication.class, args);
    }

}
