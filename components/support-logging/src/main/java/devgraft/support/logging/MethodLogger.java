package devgraft.support.logging;

import org.springframework.boot.logging.LogLevel;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodLogger {
    @AliasFor("value")
    LogLevel logLevel() default LogLevel.TRACE;
    @AliasFor("logLevel")
    LogLevel value() default LogLevel.TRACE;

    String name() default "";
    boolean isShowRequestMsg() default true;
    boolean isShowResultMsg() default true;
}
