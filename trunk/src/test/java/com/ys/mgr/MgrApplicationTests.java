package com.ys.mgr;

import com.ys.mgr.service.SpiderKeyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MgrApplicationTests {

	@Autowired
	private SpiderKeyService spiderKeyService;

	@Test
	public void contextLoads() throws Exception{
		List<String> list = spiderKeyService.selectAllKey();
		System.out.println(list);
		//spiderKeyService.delById(14);
	}

}
