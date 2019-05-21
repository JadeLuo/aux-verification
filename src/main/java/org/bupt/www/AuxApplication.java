package org.bupt.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AuxApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuxApplication.class, args);
    }

}
