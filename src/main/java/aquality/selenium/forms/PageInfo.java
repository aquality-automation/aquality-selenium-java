package aquality.selenium.forms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for page declaration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PageInfo {

    /**
     * Page game getter
     *
     * @return Page name
     */
    String pageName() default "";

    /**
     * Page xpath anchor getter
     *
     * @return Xpath anchor
     */
    String xpath() default "";

    /**
     * Page id anchor getter
     *
     * @return id anchor
     */
    String id() default "";

    /**
     * Page css anchor getter
     *
     * @return css anchor
     */
    String css() default "";
}


