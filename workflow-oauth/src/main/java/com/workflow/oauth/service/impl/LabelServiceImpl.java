package com.workflow.oauth.service.impl;

import com.workflow.oauth.service.LabelService;
import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.LabelDao;
import com.workflow.common.entity.Label;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl extends BaseServiceImpl<LabelDao,Label> implements LabelService {


    @Override
    public Page<Label> findByPage(int pageNo, int length) {
        PageRequest pageRequest = new PageRequest(pageNo, length);
        Page<Label> page = repository.findAll(pageRequest);
        return page;
    }
}
