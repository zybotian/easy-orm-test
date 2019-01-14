package org.oasis.easy.orm.dao;

import org.junit.Assert;
import org.junit.Test;
import org.oasis.easy.orm.mapper.sql.Order;
import org.oasis.easy.orm.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author tianbo
 * @date 2019-01-07
 */
public class UserDaoSelectTest extends AbstractTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testFind() throws Exception {
        User user = userDao.find(1L);
        Assert.assertTrue(1 == user.getId());
        Assert.assertTrue(18 == user.getAge());
        Assert.assertTrue(100 == user.getGroupId());
        Assert.assertTrue(100 == user.getSalary().intValue());
        Assert.assertEquals("test-name", user.getName());
        Assert.assertEquals("hubei", user.getAddress());
    }

    @Test
    public void testFindById() throws Exception {
        User user = userDao.findById(1L);
        Assert.assertTrue(1 == user.getId());
        Assert.assertTrue(18 == user.getAge());
        Assert.assertTrue(100 == user.getGroupId());
        Assert.assertTrue(100 == user.getSalary().intValue());
        Assert.assertEquals("test-name", user.getName());
        Assert.assertEquals("hubei", user.getAddress());
    }

    @Test
    public void testFindByAgeRange() throws Exception {
        List<User> users = userDao.findByAgeRange(0, 30);
        Assert.assertTrue(users.size() == 4);
        Assert.assertTrue(1 == users.get(0).getId());
        Assert.assertTrue(2 == users.get(1).getId());
        Assert.assertTrue(3 == users.get(2).getId());
        Assert.assertTrue(4 == users.get(3).getId());
        Assert.assertTrue(18 == users.get(0).getAge());
        Assert.assertTrue(19 == users.get(1).getAge());
        Assert.assertTrue(20 == users.get(2).getAge());
        Assert.assertTrue(21 == users.get(3).getAge());
    }

    @Test
    public void testFindByName() throws Exception {
        List<User> users = userDao.findByName("test%");
        Assert.assertTrue(users.size() == 4);
        Assert.assertTrue(1 == users.get(0).getId());
        Assert.assertTrue(2 == users.get(1).getId());
        Assert.assertTrue(3 == users.get(2).getId());
        Assert.assertTrue(4 == users.get(3).getId());
        List<User> users2 = userDao.findByName("Test%");
        Assert.assertTrue(users2.size() == 0);
    }

    @Test
    public void testFindByIdList() throws Exception {
        List<User> users = userDao.findByIdList(Arrays.asList(1L, 2L, 3L, 4L));
        Assert.assertTrue(users.size() == 4);
        sort(users);
        Assert.assertTrue(1 == users.get(0).getId());
        Assert.assertTrue(2 == users.get(1).getId());
        Assert.assertTrue(3 == users.get(2).getId());
        Assert.assertTrue(4 == users.get(3).getId());
    }

    @Test
    public void testFindByGroups() throws Exception {
        List<User> users1 = userDao.findByGroups(Arrays.asList(1, 2, 3));
        Assert.assertTrue(users1.size() == 0);

        List<User> users2 = userDao.findByGroups(Arrays.asList(100, 101, 301, 404));
        sort(users2);
        Assert.assertTrue(users2.size() == 4);
        Assert.assertTrue(1 == users2.get(0).getId());
        Assert.assertTrue(2 == users2.get(1).getId());
        Assert.assertTrue(3 == users2.get(2).getId());
        Assert.assertTrue(4 == users2.get(3).getId());
    }

    @Test
    public void testFindList() throws Exception {
        List<User> list = userDao.findList(0, 10);
        Assert.assertTrue(list.size() == 4);

        List<User> list1 = userDao.findList(0, 1);
        Assert.assertTrue(list1.size() == 1);

        List<User> list2 = userDao.findList(0, 4);
        Assert.assertTrue(list2.size() == 4);

        List<User> list3 = userDao.findList(2, 1);
        Assert.assertTrue(list3.size() == 1);
        Assert.assertTrue(list3.get(0).getId() == 3);
    }

    @Test
    public void testFindByAgeRangeList() throws Exception {
        // (18,21)
        List<User> users = userDao.findByAgeRange(18, 21, 0, 10);
        Assert.assertTrue(users.size() == 2);
        Assert.assertTrue(users.get(0).getAge() == 19);
        Assert.assertTrue(users.get(1).getAge() == 20);

        List<User> users2 = userDao.findByAgeRange(19, 25, 0, 10);
        Assert.assertTrue(users2.size() == 2);
        Assert.assertTrue(users2.get(0).getAge() == 20);
        Assert.assertTrue(users2.get(1).getAge() == 21);

        List<User> users3 = userDao.findByAgeRange(10, 25, 0, 10);
        Assert.assertTrue(users3.size() == 4);
        Assert.assertTrue(users3.get(0).getAge() == 18);
        Assert.assertTrue(users3.get(1).getAge() == 19);
        Assert.assertTrue(users3.get(2).getAge() == 20);
        Assert.assertTrue(users3.get(3).getAge() == 21);

        List<User> users4 = userDao.findByAgeRange(10, 25, 0, 2);
        Assert.assertTrue(users4.size() == 2);
        Assert.assertTrue(users4.get(0).getAge() == 18);
        Assert.assertTrue(users4.get(1).getAge() == 19);

        List<User> users5 = userDao.findByAgeRange(10, 25, 2, 2);
        Assert.assertTrue(users5.size() == 2);
        Assert.assertTrue(users5.get(0).getAge() == 20);
        Assert.assertTrue(users5.get(1).getAge() == 21);
    }

    @Test
    public void testFindList2() throws Exception {
        List<User> list = userDao.findList(null, null, null, null, null, null, null, null, 0,
                10);
        Assert.assertTrue(list.size() == 4);

        List<User> list2 = userDao.findList(0, null, null, null, null, null, null, null, 0,
                10);
        Assert.assertTrue(list2.size() == 4);

        List<User> list3 = userDao.findList(0, 30, null, null, null, null, null, null, 0,
                10);
        Assert.assertTrue(list3.size() == 4);

        List<User> list4 = userDao.findList(0, 30, "%test%", null, null, null, null, null, 0,
                10);
        Assert.assertTrue(list4.size() == 4);

        List<User> list5 = userDao.findList(0, 30, "%test%", null, Arrays.asList(100, 101), null, null, null, 0,
                10);
        Assert.assertTrue(list5.size() == 4);

        List<User> list6 = userDao.findList(0, 30, "%test%", null, Arrays.asList(100, 101), 0.0, 1000.0, null, 0,
                10);
        Assert.assertTrue(list6.size() == 4);

        List<User> list7 = userDao.findList(0, 30, "%test%", "%h%", Arrays.asList(100, 101), 0.0, 1000.0, null, 0,
                10);
        Assert.assertTrue(list7.size() == 3);

        List<User> list8 = userDao.findList(0, 30, "%test%", "hu%", Arrays.asList(100, 101), 0.0, 1000.0, null, 0,
                10);
        Assert.assertTrue(list8.size() == 2);

        List<User> list9 = userDao.findList(0, 30, "test%", "hu%", Arrays.asList(100, 101), 0.0, 1000.0, null, 0,
                10);
        Assert.assertTrue(list9.size() == 2);
    }

    @Test
    public void testFindListOrderBy() throws Exception {
        Order order = new Order();
        List<User> list = userDao.findList(null, null, null, null, null, null, null, order, 0,
                10);
        Assert.assertTrue(list.size() == 4);

        order.asc("id");
        List<User> list1 = userDao.findList(null, null, null, null, null, null, null, order, 0,
                10);
        Assert.assertTrue(list1.size() == 4);
        Assert.assertTrue(1 == list1.get(0).getId());
        Assert.assertTrue(2 == list1.get(1).getId());
        Assert.assertTrue(3 == list1.get(2).getId());
        Assert.assertTrue(4 == list1.get(3).getId());

        order.reset();
        order.desc("groupId");
        order.asc("id");
        List<User> list2 = userDao.findList(null, null, null, null, null, null, null, order, 0,
                10);
        Assert.assertTrue(list2.size() == 4);
        Assert.assertTrue(2 == list2.get(0).getId());
        Assert.assertTrue(4 == list2.get(1).getId());
        Assert.assertTrue(1 == list2.get(2).getId());
        Assert.assertTrue(3 == list2.get(3).getId());
    }

    @Test
    public void testFindByMarried() throws Exception {
        List<User> users = userDao.findByMarried(true);
        sort(users);
        Assert.assertTrue(users.size() == 2);
        Assert.assertTrue(users.get(0).getId() == 2);
        Assert.assertTrue(users.get(1).getId() == 4);

        List<User> users1 = userDao.findByMarried(false);
        sort(users1);
        Assert.assertTrue(users1.size() == 2);
        Assert.assertTrue(users1.get(0).getId() == 1);
        Assert.assertTrue(users1.get(1).getId() == 3);
    }

    @Test
    public void testSelectForUpdate() throws Exception {
        User user = userDao.selectForUpdate(1L);
        Assert.assertTrue(user.getAge() == 18);
        int updated = userDao.updateAge(user.getAge() + 1, 1L);
        Assert.assertTrue(updated > 0);
        Assert.assertTrue(userDao.find(1L).getAge() == 19);
        User user1 = userDao.selectForUpdate(5L);
        Assert.assertTrue(user1 == null);
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertTrue(userDao.countDistinctId() == 4);
        Assert.assertTrue(userDao.countDistinctGroupId() == 2);
        Assert.assertTrue(userDao.countGroupId() == 4);
    }
}
