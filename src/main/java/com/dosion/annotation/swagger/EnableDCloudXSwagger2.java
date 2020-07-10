

package com.dosion.annotation.swagger;

import com.dosion.configuration.SwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author cdw
 * @date 2018/7/21
 * 开启dcloudx swagger
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableDCloudXSwagger2 {
}
