package com.weiran.common.annotations;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.BaseCustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class InternalAccessAspect {
    @Pointcut("@within(com.weiran.common.annotations.InternalAccess)")
    public void internalAccessOnClass() {
    }

    @Pointcut("@annotation(com.weiran.common.annotations.InternalAccess)")
    public void internalAccessOnMethod() {
    }

    @Before(value = "internalAccessOnMethod() || internalAccessOnClass()")
    public void before() {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
        ).getRequest();
        String from = httpServletRequest.getHeader("from");
        if (!StringUtils.isEmpty(from) && "public".equals(from)) {
            log.error("This api is only allowed invoked by internal source");
            throw new BaseCustomizeException(ResponseEnum.INTERNAL_ACCESS_ERROR);
        }
    }
}
