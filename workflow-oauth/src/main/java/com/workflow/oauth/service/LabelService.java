package com.workflow.oauth.service;

import com.workflow.common.base.BaseService;
import com.workflow.common.entity.Label;
import org.springframework.data.domain.Page;

public interface LabelService extends BaseService<Label> {

    /**
     * 翻页查询
     * @param pageNo
     * @param length
     * @return
     */
    Page<Label> findByPage(int pageNo, int length);
}
