package devgraft.support.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class CallLoggerAspect {
    private final ObjectMapper om;
    @Pointcut("execution(* *(..)) && "
            + "@annotation(devgraft.support.logging.MethodLogger)")
    public void callLoggerPointcut() {

    }

    @Around("callLoggerPointcut()")
    public Object methodLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object[] requestArgs = joinPoint.getArgs();
        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        final MethodLogger methodAnnotation = method.getAnnotation(MethodLogger.class);
        final LogLevel logLevel = LogLevel.TRACE == methodAnnotation.value() ? methodAnnotation.logLevel() : methodAnnotation.value();
        final String methodName = StringUtils.hasText(methodAnnotation.name()) ? methodAnnotation.name() : method.getName();
        final Annotation[][] methodMatrix = method.getParameterAnnotations();

        final Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < requestArgs.length; i++) {
            for (final Annotation annotation : methodMatrix[i]) {
                if (LogParam.class == annotation.annotationType()) {
                    final LogParam logParam = (LogParam)annotation;
                    final String parameterName = StringUtils.hasText(logParam.name()) ? logParam.name() : method.getParameters()[i].getName();
                    final Object parameterValue = requestArgs[i];
                    map.put(parameterName, parameterValue);
                }
            }
        }

        if (methodAnnotation.isShowRequestMsg()) log(logLevel, String.format("[%s] request", methodName), map);

        final Object result = joinPoint.proceed();

        if (methodAnnotation.isShowResultMsg()) log(logLevel, String.format("[%s] result", methodName), result);

        return result;
    }

    private void log(final LogLevel logLevel, final String msg, final Object obj) throws JsonProcessingException {
        switch (logLevel) {
            case INFO:
                if (log.isInfoEnabled()) log.info("{} : {}", msg, om.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
                break;
            case TRACE:
                if (log.isTraceEnabled()) log.trace("{} : {}", msg, om.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
                break;
            case DEBUG:
                if (log.isDebugEnabled()) log.debug("{} : {}", msg, om.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
                break;
            case WARN:
                if (log.isWarnEnabled()) log.warn("{} : {}", msg, om.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
                break;
            case ERROR:
                if (log.isErrorEnabled()) log.error("{} : {}", msg, om.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
                break;
        }
    }
}
