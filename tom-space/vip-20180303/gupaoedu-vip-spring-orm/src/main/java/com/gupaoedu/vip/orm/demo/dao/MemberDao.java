package com.gupaoedu.vip.orm.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.core.common.jdbc.datasource.DynamicDataSource;
import javax.sql.DataSource;

import com.gupaoedu.vip.orm.demo.model.Member;
import com.gupaoedu.vip.orm.framework.BaseDaoSupport;
import com.gupaoedu.vip.orm.framework.QueryRule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDao extends BaseDaoSupport<Member, Long> {

	@Override
	protected String getPKColumn() {return "id";}

	private JdbcTemplate template;

	private DynamicDataSource dataSource;

	@Resource(name="dynamicDataSource")
	public void setDataSource(DataSource dataSource) {
        this.dataSource = (DynamicDataSource)dataSource;
		this.setDataSourceReadOnly(dataSource);
		this.setDataSourceWrite(dataSource);
	}

//    @Resource(name="readOnlyDataSource")
//	public void setDataSourceReadOnly(DataSource dataSource){
//        super.setDataSourceReadOnly(dataSource);
//    }


//    @Resource(name="writeOnlyDataSource")
//    public void setDataSourceWrite(DataSource dataSource){
//        super.setDataSourceWrite(dataSource);
//    }



//	public List<Member> selectByAge(int age) throws  Exception{
//		String sql = "select * from t_member where age = ?";
//		List<Member> result = template.query(sql, new RowMapper<Member>() {
//
//            public Member mapRow(ResultSet rs, int i) throws SQLException {
//
//                //全自动
//                Member member = new Member();
//                member.setId(rs.getLong("id"));
//                member.setName(rs.getString("name"));
//                member.setAge(rs.getInt("age"));
//                member.setAddr(rs.getString("addr"));
//
//                return member;
//            }
//        }, age);
//
//		return result;
//	}


	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Member> selectByName(String name) throws Exception{
		//构建一个QureyRule 查询规则
		QueryRule queryRule = QueryRule.getInstance();
		//查询一个name= 赋值 结果，List
		queryRule.andEqual("name", name);
		//相当于自己再拼SQL语句
		return super.select(queryRule);
	}


	public List<Member> selectAll() throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		return super.select(queryRule);
	}

	/**
	 * 
	 */
	public boolean insert(Member entity) throws Exception{
	    if(entity.getAge() >= 30){
            this.dataSource.getDataSourceEntry().set("db_two");
        }else{
            this.dataSource.getDataSourceEntry().set("db_one");
        }

		Long id = super.insertAndReturnId(entity);
		entity.setId(id);
		return id > 0;
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public int insertAll(List<Member> memberList) throws Exception{
		return super.insertAll(memberList);
	}


	/**
	 * @throws Exception
	 *
	 */
	public boolean update(Member member) throws Exception{
		return super.update(member);
	}

	public boolean delete(Member member) throws Exception{
		return super.delete(member);
	}

	
}
