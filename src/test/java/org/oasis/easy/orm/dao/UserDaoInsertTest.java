package org.oasis.easy.orm.dao;

import org.junit.*;
import org.oasis.easy.orm.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * @author tianbo
 * @date 2019-01-07
 */
public class UserDaoInsertTest extends AbstractTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setAge(35);
        user.setGroupId(1);
        // 假装福建人
        user.setAddress("hujian");
        user.setName("qiaofeng");
        user.setMarried(false);
        user.setSalary(0.0);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        long id = userDao.insert(user);
        Assert.assertTrue(id > 0);
        User user1 = userDao.find(id);
        Assert.assertTrue(user1.getId() == id);
        Assert.assertTrue(user1.getAge() == 35);
        Assert.assertTrue(user1.getGroupId() == 1);
        Assert.assertTrue(user1.getSalary().intValue() == 0);
        Assert.assertEquals("hujian", user1.getAddress());
        Assert.assertEquals("qiaofeng", user1.getName());
        Assert.assertFalse(user1.getMarried());

        user.setAge(178);
        user.setName("扫地僧");
        user.setAddress("少林寺藏经阁");
        Long id2 = userDao.insert(user);
        User user2 = userDao.find(id2);
        Assert.assertTrue(id2 > 0);
        Assert.assertEquals("扫地僧", user2.getName());
        Assert.assertEquals("少林寺藏经阁", user2.getAddress());
        Assert.assertTrue(user2.getAge() == 178);
    }

    @Test
    public void testInserts() throws Exception {
        User user1 = newUser().setName("乔峰").setAge(35).setAddress("大辽");
        User user2 = newUser().setName("虚竹").setAge(23).setAddress("灵鹫宫缥缈峰");
        User user3 = newUser().setName("段誉").setAge(18).setAddress("大理");
        User user4 = newUser().setName("鸠摩智").setAge(33).setAddress("吐蕃");

        boolean insert = userDao.insert(Arrays.asList(user1, user2, user3, user4));
        Assert.assertTrue(insert);

        User user5 = userDao.find(5L);
        User user6 = userDao.find(6L);
        User user7 = userDao.find(7L);
        User user8 = userDao.find(8L);

        Assert.assertEquals("乔峰", user5.getName());
        Assert.assertEquals("大辽", user5.getAddress());
        Assert.assertTrue(35 == user5.getAge());

        Assert.assertEquals("虚竹", user6.getName());
        Assert.assertEquals("灵鹫宫缥缈峰", user6.getAddress());
        Assert.assertTrue(23 == user6.getAge());

        Assert.assertEquals("段誉", user7.getName());
        Assert.assertEquals("大理", user7.getAddress());
        Assert.assertTrue(18 == user7.getAge());

        Assert.assertEquals("鸠摩智", user8.getName());
        Assert.assertEquals("吐蕃", user8.getAddress());
        Assert.assertTrue(33 == user8.getAge());

    }

    // h2数据库不支持insert ignore操作,这里使用mysql测试之后将case标记为了Ignore
    @Test
    @Ignore
    public void testInsertIgnore() throws Exception {
        Long inserted = userDao.insertIgnore(newUser().setAge(256));
        Assert.assertTrue(inserted == 5L);
        Assert.assertTrue(userDao.find(5L).getAge() == 256);

        Long insertedId = userDao.insertIgnore(userDao.find(5L));
        Assert.assertTrue(insertedId == null);
    }

    private User newUser() {
        User user = new User();
        user.setAge(35);
        user.setGroupId(1);
        // 假装福建人
        user.setAddress("hujian");
        user.setName("qiaofeng");
        user.setMarried(false);
        user.setSalary(0.0);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        return user;
    }
}
