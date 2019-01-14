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
public class UserDaoDeleteTest extends AbstractTestCase {

    @Autowired
    private UserDao userDao;

    @Test
    public void testDelete() throws Exception {
        User user = userDao.find(1L);
        Assert.assertTrue(user != null);
        boolean delete = userDao.delete(1L);
        Assert.assertTrue(delete);
        User user1 = userDao.find(1L);
        Assert.assertTrue(user1 == null);
    }

    @Test
    public void testDeleteById() throws Exception {
        User user = userDao.find(1L);
        Assert.assertTrue(user != null);
        int delete = userDao.deleteById(1L);
        Assert.assertTrue(delete > 0);
        User user1 = userDao.find(1L);
        Assert.assertTrue(user1 == null);
    }

    @Test
    public void testDeleteByGroupId() throws Exception {
        List<User> users = userDao.findList(0, 10);
        Assert.assertTrue(users.size() == 4);
        int delete = userDao.deleteByGroupId(100);
        Assert.assertTrue(delete > 0);
        List<User> users1 = userDao.findByGroups(Arrays.asList(100));
        Assert.assertTrue(users1.size() == 0);

        List<User> users2 = userDao.findByGroups(Arrays.asList(101));
        Assert.assertTrue(users2.size() == 2);
    }

    @Test
    public void testDeleteByName() throws Exception {
        List<User> users = userDao.findList(0, 10);
        Assert.assertTrue(users.size() == 4);
        int delete = userDao.deleteByName("test-name");
        Assert.assertTrue(delete > 0);
        List<User> users1 = userDao.findList(0, 10);
        Assert.assertTrue(users1.size() == 0);
    }

    @Test
    public void testDeleteByMarried() throws Exception {
        List<User> users = userDao.findList(0, 10);
        Assert.assertTrue(users.size() == 4);
        int delete = userDao.deleteByMarried(false);
        Assert.assertTrue(delete > 0);
        List<User> users1 = userDao.findList(0, 10);
        Assert.assertTrue(users1.size() == 2);
        List<User> users2 = userDao.findByMarried(true);
        Assert.assertTrue(users2.size() == 2);
    }

    @Test
    public void testDeleteByNameLike() throws Exception {
        List<User> users = userDao.findList(0, 10);
        Assert.assertTrue(users.size() == 4);
        int delete = userDao.deleteByNameLike("test%");
        Assert.assertTrue(delete > 0);
        List<User> users1 = userDao.findList(0, 10);
        Assert.assertTrue(users1.size() == 0);
    }

    @Test
    public void testDeleteByIdList() throws Exception {
        List<User> list = userDao.findList(0, 10);
        Assert.assertTrue(list.size() == 4);
        int deleted = userDao.deleteByIdList(Arrays.asList(1L, 2L, 3L));
        Assert.assertTrue(deleted > 0);
        List<User> list1 = userDao.findList(0, 10);
        Assert.assertTrue(list1.size() == 1);
        Assert.assertTrue(list1.get(0).getId() == 4);
    }

    @Test
    public void testDeleteByGroupIdList() throws Exception {
        int deleted = userDao.deleteByGroupIdList(Arrays.asList(1));
        Assert.assertTrue(deleted == 0);

        int deleted1 = userDao.deleteByGroupIdList(Arrays.asList(100));
        Assert.assertTrue(deleted1 > 0);
        List<User> users = userDao.findByGroups(Arrays.asList(100));
        Assert.assertTrue(users.size() == 0);
        List<User> users1 = userDao.findByGroups(Arrays.asList(101));
        Assert.assertTrue(users1.size() == 2);

        int deleted2 = userDao.deleteByGroupIdList(Arrays.asList(101));
        Assert.assertTrue(deleted2 > 0);
        List<User> users2 = userDao.findByGroups(Arrays.asList(100));
        Assert.assertTrue(users2.size() == 0);
        List<User> users3 = userDao.findByGroups(Arrays.asList(101));
        Assert.assertTrue(users3.size() == 0);
    }

    @Test
    public void testDeleteAdv() throws Exception {
        int delete = userDao.delete("test-name-1%", Arrays.asList(1, 2), 100, 120);
        Assert.assertTrue(delete == 0);
        Assert.assertTrue(userDao.findList(0, 10).size() == 4);

        // age:[10,18)
        int delete1 = userDao.delete("test-name%", Arrays.asList(100, 101, 201, 301, 404), 10, 18);
        Assert.assertTrue(delete1 == 0);
        Assert.assertTrue(userDao.findList(0, 10).size() == 4);

        // age:[18,18)
        int delete2 = userDao.delete("test-name%", Arrays.asList(100, 101, 201, 301, 404), 18, 18);
        Assert.assertTrue(delete2 == 0);
        Assert.assertTrue(userDao.findList(0, 10).size() == 4);

        // age:[18,19)
        int delete3 = userDao.delete("test-name%", Arrays.asList(100, 101, 201, 301, 404), 18, 19);
        Assert.assertTrue(delete3 == 1);
        Assert.assertTrue(userDao.findList(0, 10).size() == 3);

        int delete4 = userDao.delete("test-name%", Arrays.asList(100, 101, 201, 301, 404), 19, 20);
        Assert.assertTrue(delete4 == 1);
        Assert.assertTrue(userDao.findList(0, 10).size() == 2);

        int delete5 = userDao.delete("test-name%", Arrays.asList(100, 101, 201, 301, 404), 20, 22);
        Assert.assertTrue(delete5 == 2);
        Assert.assertTrue(userDao.findList(0, 10).size() == 0);
    }
}
