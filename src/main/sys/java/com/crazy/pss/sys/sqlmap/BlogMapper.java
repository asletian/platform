package com.crazy.pss.sys.sqlmap;

import org.apache.ibatis.annotations.Insert;

import com.crazy.pss.sys.model.Menu;


public interface BlogMapper {
  //@Insert("SELECT * FROM blog WHERE id = #{id}")
  Menu selectBlog(int id);
}