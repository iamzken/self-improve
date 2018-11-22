package com.gupaoedu.vip.spring.transaction.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.gupaoedu.vip.spring.transaction.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao{

	private JdbcTemplate template;

	@Resource(name="dataSource")
	protected void setDataSource(DataSource dataSource) {

		template = new JdbcTemplate(dataSource);


//		Statement statement;
//		statement.e

		//相当于开启事务,创建Socket连接
		//当我们execute的时候，就和服务端建立连接
		//dataSource.getConnection().createStatement();

		/*





//		dataSource.getConnection().createStatement()

		//事务的回滚
		dataSource.getConnection().rollback();

		//默认的话是自动提交,所有的事务操作框架，都会把autoCommit改成fasle，否则的话无法手动干预
		dataSource.getConnection().setAutoCommit(false);

		//只读事务
		dataSource.getConnection().setReadOnly(true);

		//事务的提交
		dataSource.getConnection().commit();

		*/


	}

	public List<Member> selectAll() throws Exception{
		String sql = "select * from t_member";
		return template.query(sql, new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int i) throws SQLException {
				Member member = new Member();
				member.setName(rs.getString("name"));
				member.setId(rs.getLong("id"));
				member.setAddr(rs.getString("addr"));
				member.setAge(rs.getInt("age"));
				return member;
			}
		});
	}

	public boolean insert(Member m) throws Exception{
		String sql = "insert into t_member(id,name,addr,age) value(?,?,?,?)";
		int count = template.update(sql,m.getId(),m.getName(),m.getAddr(),m.getAge());
		throw new Exception("我自己抛了一个异常，假设是SQL报错了");
//		return count > 0;
	}

	public boolean delete(long id) throws Exception{
		return template.update("delete from t_member where id = ?",id) > 0;
	}

	public boolean update(long id,String name) throws Exception{
		return template.update("update t_member set name = ? where id = ?",name,id) > 0;
	}
	
}
