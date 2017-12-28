package com.workflow.common.base;


import com.workflow.common.dto.Result;
import com.workflow.common.exception.ServiceProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author DELL
 */
public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Result restProcessor(ResultProcessor processor){
        Result result = null;
        try{
            result = processor.process();
        }
        catch (ServiceProcessException e1){
            logger.error("ServiceProcess Error Log :"+e1.getLocalizedMessage(),e1);
            result = Result.error(e1.getMessage());
        }
        catch (Exception e){
            logger.error("Error Log :"+e.getLocalizedMessage(),e);
            result = Result.error("服务器出现异常");
        }

        return result;
    }
}
