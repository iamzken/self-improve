package com.gupaoedu.vip.mongo.explorer;

import com.alibaba.fastjson.JSON;
import com.gupaoedu.vip.mongo.explorer.common.config.ExplorerConfig;
import com.gupaoedu.vip.mongo.explorer.repository.UFileRepository;
import com.gupaoedu.vip.mongo.explorer.repository.MemberRepository;
import com.gupaoedu.vip.mongo.explorer.entity.Member;
import com.gupaoedu.vip.mongo.explorer.entity.UFile;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ExplorerApplication.class)
public class ExplorerApplicationTests {

	@Autowired private MemberRepository memberDao;

	@Autowired private UFileRepository fileDao;

	@Autowired private ExplorerConfig explorerConfig;

	@Test
	public void testFindByName(){
		Member member = memberDao.findByLoginName("tom");
        System.out.println(JSON.toJSONString(member));
    }

	@Test
//    @Ignore
	public void testInsertMember(){
		memberDao.insert(new Member(1L,"tom","123456","Tom"));
	}


	@Test
    @Ignore
	public void testInsertFile(){
		UFile file = fileDao.insert(new UFile("/images/photo/tom.png"));
        System.out.println(JSON.toJSONString(file));
    }


    @Test
    @Ignore
    public void testGetProperties(){
        System.out.println("===" + explorerConfig.getFilesDepotDir());
    }


}
