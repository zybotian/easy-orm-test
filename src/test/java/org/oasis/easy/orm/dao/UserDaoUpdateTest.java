package org.oasis.easy.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import org.oasis.easy.orm.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @author tianbo
 * @date 2019-01-07
 */
public class UserDaoUpdateTest extends AbstractTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUpdate() throws Exception {
        User user = userDao.find(1L);
        Assert.assertTrue(user.getAge() == 18);
        Assert.assertTrue(user.getGroupId() == 100);
        Assert.assertTrue(100 == user.getSalary().intValue());
        Assert.assertEquals("hubei", user.getAddress());

        user.setAge(81);
        user.setGroupId(250);
        user.setAddress("IBM");
        user.setSalary(12000.0);
        boolean update = userDao.update(user);
        Assert.assertTrue(update);

        User user1 = userDao.find(1L);
        Assert.assertTrue(user1.getAge() == 81);
        Assert.assertTrue(user1.getGroupId() == 250);
        Assert.assertTrue(12000 == user.getSalary().intValue());
        Assert.assertEquals("IBM", user1.getAddress());
    }

    @Test
    public void testUpdateAddress() throws Exception {
        User user1 = userDao.find(1L);
        Assert.assertEquals("hubei", user1.getAddress());
        int updated = userDao.updateAddress("上海市", 1L);
        Assert.assertTrue(updated > 0);
        User user2 = userDao.find(1L);
        Assert.assertEquals("上海市", user2.getAddress());
    }

    @Test
    public void testUpdateName() throws Exception {
        User user = userDao.find(1L);
        Assert.assertEquals("test-name", user.getName());
        int updated = userDao.updateName("郭靖大俠", 1L);
        Assert.assertTrue(updated > 0);
        User user1 = userDao.find(1L);
        Assert.assertEquals("郭靖大俠", user1.getName());
    }

    @Test
    public void testUpdateGroupId() throws Exception {
        User user = userDao.find(1L);
        Assert.assertTrue(100 == user.getGroupId());
        int updated = userDao.updateGroupId(235, 1L);
        Assert.assertTrue(updated > 0);
        User user1 = userDao.find(1L);
        Assert.assertTrue(235 == user1.getGroupId());
    }

    @Test
    public void testUpdateNameAddress() throws Exception {
        User user = userDao.find(1L);
        Assert.assertEquals("test-name", user.getName());
        Assert.assertEquals("hubei", user.getAddress());
        int updated = userDao.updateNameAddress("郭靖大俠", "嘉兴醉仙楼", 1L);
        Assert.assertTrue(updated > 0);
        User user1 = userDao.find(1L);
        Assert.assertEquals("郭靖大俠", user1.getName());
        Assert.assertEquals("嘉兴醉仙楼", user1.getAddress());
    }

    @Test
    public void testUpdateByGroupId() throws Exception {
        List<User> users = userDao.findByGroups(Arrays.asList(100));
        Assert.assertTrue(users.size() == 2);
        sort(users);
        Assert.assertEquals("hubei", users.get(0).getAddress());
        Assert.assertEquals("shanghai", users.get(1).getAddress());

        int updated = userDao.updateByGroupId("桃花岛", 100);
        Assert.assertTrue(updated > 0);

        List<User> users1 = userDao.findByGroups(Arrays.asList(100));
        Assert.assertTrue(users1.size() == 2);
        sort(users1);
        Assert.assertEquals("桃花岛", users1.get(0).getAddress());
        Assert.assertEquals("桃花岛", users1.get(1).getAddress());
    }

    @Test
    public void testUpdateNameAddressByGroupList() throws Exception {
        List<User> users = userDao.findByGroups(Arrays.asList(100, 101));
        Assert.assertTrue(users.size() == 4);
        Assert.assertEquals("test-name", users.get(0).getName());
        Assert.assertEquals("hubei", users.get(0).getAddress());
        Assert.assertEquals("test-name", users.get(1).getName());
        Assert.assertEquals("hunan", users.get(1).getAddress());
        Assert.assertEquals("test-name", users.get(2).getName());
        Assert.assertEquals("shanghai", users.get(2).getAddress());
        Assert.assertEquals("test-name", users.get(3).getName());
        Assert.assertEquals("jiangsu", users.get(3).getAddress());

        int updated = userDao.updateNameAddressByGroupList("黄老邪", "桃花岛", Arrays.asList(100));
        Assert.assertTrue(updated > 0);
        List<User> users1 = userDao.findByGroups(Arrays.asList(100, 101));
        Assert.assertTrue(users1.size() == 4);
        Assert.assertEquals("黄老邪", users1.get(0).getName());
        Assert.assertEquals("桃花岛", users1.get(0).getAddress());
        Assert.assertEquals("test-name", users1.get(1).getName());
        Assert.assertEquals("hunan", users1.get(1).getAddress());
        Assert.assertEquals("黄老邪", users1.get(2).getName());
        Assert.assertEquals("桃花岛", users1.get(2).getAddress());
        Assert.assertEquals("test-name", users1.get(3).getName());
        Assert.assertEquals("jiangsu", users1.get(3).getAddress());

        int updated2 = userDao.updateNameAddressByGroupList("黄老邪", "桃花岛", Arrays.asList(101));
        Assert.assertTrue(updated2 > 0);
        List<User> users2 = userDao.findByGroups(Arrays.asList(100, 101));
        Assert.assertTrue(users2.size() == 4);
        for (User user : users2) {
            Assert.assertEquals("黄老邪", user.getName());
            Assert.assertEquals("桃花岛", user.getAddress());
        }
    }

    @Test
    public void testUpdateNameByAgeRangeAddress() throws Exception {
        List<User> list = userDao.findList(0, 10);
        Assert.assertEquals("hubei", list.get(0).getAddress());
        Assert.assertEquals("hunan", list.get(1).getAddress());
        Assert.assertEquals("shanghai", list.get(2).getAddress());
        Assert.assertEquals("jiangsu", list.get(3).getAddress());
        Assert.assertTrue(18 == list.get(0).getAge());
        Assert.assertTrue(19 == list.get(1).getAge());
        Assert.assertTrue(20 == list.get(2).getAge());
        Assert.assertTrue(21 == list.get(3).getAge());

        long timestamp = System.currentTimeMillis();
        // 预期是将id=3的记录修改掉
        int updated = userDao.updateNameByAgeRangeAddress("舟山群岛", timestamp, 18, 20, "%ang%");
        Assert.assertTrue(updated > 0);
        List<User> list1 = userDao.findList(0, 10);
        Assert.assertEquals("test-name", list1.get(0).getName());
        Assert.assertEquals("test-name", list1.get(1).getName());
        Assert.assertEquals("舟山群岛", list1.get(2).getName());
        Assert.assertEquals("test-name", list1.get(3).getName());
    }

    @Test
    public void testUpdateBatch() throws Exception {
        List<User> users = userDao.findList(0, 10);
        for (User user : users) {
            user.setGroupId(user.getGroupId() + 1);
            user.setAge(user.getAge() + 1);
            user.setAddress("address:" + user.getAddress());
        }

        boolean update = userDao.update(users);
        Assert.assertTrue(update);

        List<User> list = userDao.findList(0, 10);
        Assert.assertTrue(list.get(0).getGroupId() == 101);
        Assert.assertTrue(list.get(1).getGroupId() == 102);
        Assert.assertTrue(list.get(2).getGroupId() == 101);
        Assert.assertTrue(list.get(3).getGroupId() == 102);
        Assert.assertTrue(list.get(0).getAge() == 19);
        Assert.assertTrue(list.get(1).getAge() == 20);
        Assert.assertTrue(list.get(2).getAge() == 21);
        Assert.assertTrue(list.get(3).getAge() == 22);
        Assert.assertEquals("address:hubei", list.get(0).getAddress());
        Assert.assertEquals("address:hunan", list.get(1).getAddress());
        Assert.assertEquals("address:shanghai", list.get(2).getAddress());
        Assert.assertEquals("address:jiangsu", list.get(3).getAddress());
    }
}
