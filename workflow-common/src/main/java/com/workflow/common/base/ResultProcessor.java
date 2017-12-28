package com.workflow.common.base;


import com.workflow.common.dto.Result;

@FunctionalInterface
public interface ResultProcessor {
    Result process();
}
