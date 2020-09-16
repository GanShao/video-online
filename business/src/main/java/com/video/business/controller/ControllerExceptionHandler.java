package com.video.business.controller;

import com.video.server.dto.ResponseDto;
import com.video.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice 是Controller增强器，可以对Controller做统一处理，如异常处理，数据处理
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    //@ExceptionHandler注解中可以添加参数，参数是某个异常类的class，代表这个方法专门处理该类异常.
    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.warn(e.getMessage());
        //有些接口不对外，为了内部对安全，不应该把参数对规则暴露出去，所以需要模糊返回信息
//        responseDto.setMessage(e.getMessage());
        responseDto.setMessage("请求参数异常！");
        return responseDto;
    }
}
