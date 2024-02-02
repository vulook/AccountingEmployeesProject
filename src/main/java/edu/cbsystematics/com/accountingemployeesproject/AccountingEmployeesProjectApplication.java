package edu.cbsystematics.com.accountingemployeesproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * FileName: AccountingEmployeesProjectApplication
 * Author: Andriy Vulook
 * Date: 08.01.2024 19:43
 * Description:
 *
 */


@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AccountingEmployeesProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingEmployeesProjectApplication.class, args);

    }

}
