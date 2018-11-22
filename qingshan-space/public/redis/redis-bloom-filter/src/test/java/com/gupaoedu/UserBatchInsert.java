package com.gupaoedu;

import com.gupaoedu.entity.User;
import com.gupaoedu.service.UserService;
import com.gupaoedu.util.NameUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: qingshan
 * @Date: 2018/11/9 17:31
 * @Description: 咕泡学院，只为更好的你
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class UserBatchInsert {

    @Autowired
    JdbcTemplate jdbcTemplate;

    List<User> list;

    private static final int USER_COUNT = 100000; // 数量过大可能导致内存溢出，多运行几次

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        list = new ArrayList<>();
        for (int i=0; i< USER_COUNT; i++){
            User user = new User();
            String account = UUID.randomUUID().toString();
            user.setAccount(account);
            int min=18;
            int max=60;
            Random random = new Random();
            // 年龄范围在18-60之间
            int age = random.nextInt(max)%(max-min+1) + min;
            user.setAge(age);
            user.setName(NameUtil.getRandomName());

            list.add(user);
        }

        save(list);
        long end = System.currentTimeMillis();
        System.out.println("批量插入条"+USER_COUNT+"用户数据完毕，总耗时：" + (end - start) + " 毫秒");

    }

    /**
     * 必须要在数据库连接url加上 &rewriteBatchedStatements=true 来开启批处理，否则还是一条一条写入的
     * @param list
     */
    public void save(List<User> list) {
        final List<User> tempList = list;
        String sql = "insert into user(account, name, age) "
                + "values(?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String account =  tempList.get(i).getAccount();
                String name = tempList.get(i).getName();
                Integer age = tempList.get(i).getAge();

                ps.setString(1, account);
                ps.setString(2, name);
                ps.setInt(3, age);
            }

            public int getBatchSize() {
                return tempList.size();
            }
        });

    }
}
