package com.gupao.dal.dao;

import com.gupao.dal.resultmap.BlogPostsResultMap;
import com.gupao.dal.resultmap.BlogResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    int countByExample(BlogExample example);

    int deleteByExample(BlogExample example);

    int deleteByPrimaryKey(Integer bid);

    int insert(Blog record);

    int insertSelective(Blog record);

    List<Blog> selectByExample(BlogExample example);

    Blog selectByPrimaryKey(Integer bid);

    BlogResultMap selectBlogAuthor(Integer bid);

    BlogResultMap selectBlogAuthor2(Integer bid);

    //嵌套结果
    BlogPostsResultMap selectBlogPosts2(Integer bid);

    //嵌套查询
    BlogPostsResultMap selectBlogPosts(Integer bid);

    //嵌套查询
    List<BlogPostsResultMap> selectBlogPostsList(Integer bid);//大于id

    List<Posts> selectByBlogId(Integer bid);

    int updateByExampleSelective(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByExample(@Param("record") Blog record, @Param("example") BlogExample example);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);
}