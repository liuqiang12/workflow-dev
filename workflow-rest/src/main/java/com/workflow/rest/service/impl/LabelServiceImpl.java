package com.workflow.rest.service.impl;

import com.workflow.common.base.BaseServiceImpl;
import com.workflow.common.dao.LabelDao;
import com.workflow.common.entity.Label;
import com.workflow.rest.service.LabelService;
import org.springframework.stereotype.Service;
@Service
public class LabelServiceImpl extends BaseServiceImpl<LabelDao,Label> implements LabelService{
}
