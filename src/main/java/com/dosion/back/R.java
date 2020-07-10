

package com.dosion.back;

import com.dosion.utils.ReflectUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author cdw
 */
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 运行成功
     */
    final String RUN_SUCCESS = "0";

    /**
     * 运行失败
     */
    final String RUN_ERROR = "1";

    /**
     * 业务失败
     */
    final Boolean BACK_ERROR = false;

    /**
     * 业务成功
     */
    final Boolean BACK_SUCCESS = true;

    /**
     * 未登录
     */
    final String RUN_NOT_LOGIN = "1001";


    @Getter
    @Setter
    @ApiModelProperty(value = "执行code")
    private String code = RUN_SUCCESS;

    @Getter
    @Setter
    @ApiModelProperty(value = "执行信息")
    private String msg = "success";


    @Getter
    @Setter
    @ApiModelProperty(value = "业务处理状态")
    private Boolean state = BACK_SUCCESS;

    @Getter
    @Setter
    @ApiModelProperty(value = "分页总行数")
    private Long count = null;


    @Getter
    @Setter
    @ApiModelProperty(value = "返回数据")
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(T data, Long count) {
        super();
        this.data = data;
        this.count = count;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = RUN_ERROR;
    }

    public R<T> put(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return (R<T>) ReflectUtils.getTarget(this, map);
    }

    public R<T> error(String msg) {
        this.state = BACK_ERROR;
        this.msg = msg;
        return this;
    }

    public R<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public R<T> noLogin() {
        this.code = RUN_NOT_LOGIN;
        return this;
    }

    public R<T> runError(String msg) {
        this.code = RUN_ERROR;
        this.msg = msg;
        return this;
    }

}
