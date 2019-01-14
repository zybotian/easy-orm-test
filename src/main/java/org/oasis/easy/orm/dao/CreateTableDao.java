package org.oasis.easy.orm.dao;

import org.oasis.easy.orm.annotations.Dao;
import org.oasis.easy.orm.annotations.Sql;
import org.oasis.easy.orm.constant.SqlType;

/**
 * @author Paul
 * @date 2018-12-30
 */
@Dao
public interface CreateTableDao {
    /**
     * 不继承BasicDao时,没有基本增删改查能力,且也不具有自动生成sql的能力,需要手写sql
     */
    String createUserTable = "create table if not exists user"
            + "(id bigint not null auto_increment primary key"
            + ",name varchar(32) not null "
            + ",age int not null"
            + ",group_id int not null"
            + ",address varchar(16) not null"
            + ",married int not null"
            + ",salary decimal(20,2) not null"
            + ",create_time bigint not null"
            + ",update_time bigint not null"
            + ")";

    String dropUserTable = "drop table if exists user";

    @Sql(value = createUserTable, type = SqlType.WRITE)
    void createUserTable();

    @Sql(value = dropUserTable, type = SqlType.WRITE)
    void dropUserTable();
}
