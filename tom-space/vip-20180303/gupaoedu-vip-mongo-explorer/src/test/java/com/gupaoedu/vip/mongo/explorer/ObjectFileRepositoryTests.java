package com.gupaoedu.vip.mongo.explorer;

import com.gupaoedu.vip.mongo.explorer.entity.OFile;
import com.gupaoedu.vip.mongo.explorer.repository.DepotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ExplorerApplication.class)
public class ObjectFileRepositoryTests {

	@Autowired
	private DepotRepository objectFileRepository;

	@Test
	public void testPutFile(){
		String fileid = objectFileRepository.putFile(new OFile("E:\\WORK\\字幕.txt"));
		System.out.println(fileid);
	}


}
