package com.weiran.common.annotations;

import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.exception.BaseCustomizeException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private static final String INTERNAL_ACCESS_ERROR_MESSAGE = "This API is only allowed invoked by an internal source";
    private static final String FROM_PUBLIC = "public";

    @Pointcut("@within(com.weiran.common.annotations.InternalAccess)")
    public void internalAccessOnClass() {
    }

    @Pointcut("@annotation(com.weiran.common.annotations.InternalAccess)")
    public void internalAccessOnMethod() {
    }

    @Before(value = "internalAccessOnMethod() || internalAccessOnClass()")
    public void before() {
        HttpServletRequest request = getRequestAttributes().getRequest();
        validateInternalSource(request);
    }

    private void validateInternalSource(HttpServletRequest request) {
        String from = request.getHeader("from");
        if (isPublicAccess(from)) {
            log.error(INTERNAL_ACCESS_ERROR_MESSAGE);
            throw new BaseCustomizeException(ResponseEnum.INTERNAL_ACCESS_ERROR);
        }
    }

    private boolean isPublicAccess(String from) {
        return StringUtils.isNotBlank(from) && FROM_PUBLIC.equals(from);
    }

    @NonNull
    private ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
    }
}
