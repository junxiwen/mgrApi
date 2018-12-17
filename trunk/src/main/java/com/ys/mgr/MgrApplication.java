package com.ys.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.ys.mgr","net.miidi.fsj.util.sjp"})
@ServletComponentScan // 可以扫描servlet相关的@WebServlet、@WebFilter and @WebListener
@EnableTransactionManagement
public class MgrApplication{


	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MgrApplication.class);
	}*/
	public static void main(String[] args) {
		SpringApplication.run(MgrApplication.class, args);
	}
}
