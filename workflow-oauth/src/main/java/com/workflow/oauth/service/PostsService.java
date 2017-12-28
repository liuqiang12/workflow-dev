package com.workflow.oauth.service;

import com.workflow.common.base.BaseService;
import com.workflow.common.entity.Posts;
import org.springframework.data.domain.Page;

public interface PostsService extends BaseService<Posts>{

   /**
    * 翻页条件查询帖子
    * @param posts
    * @param pageNo
    * @param length
    * @return
    */
   Page<Posts> findByPage(Posts posts,int pageNo,int length);

   /**
    * 批量修改帖子的top
    * @param ids
    */
   void changeTop(Integer[] ids);

   /**
    * 批量修改帖子的good
    * @param ids
    */
   void changeGood(Integer[] ids);


}
