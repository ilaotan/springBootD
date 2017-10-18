package com.springBootD.application.demo.model.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2017/10/12 19:30
 */
public class AbstractResult implements Serializable {

    private static final long serialVersionUID = 1547388657941513953L;

    protected String code;

    protected String msg;

    protected Map<String, String> content;

    public AbstractResult() {
    }

    public AbstractResult(final String code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final String getCode() {
        return this.code;
    }

    public final void setCode(final String code) {
        this.code = code;
    }

    public final String getMsg() {
        return this.msg;
    }

    public final void setMsg(final String msg) {
        this.msg = msg;
    }

    public final Map<String, String> getContent() {
        return this.content;
    }

    public final void setContent(final Map<String, String> content) {
        this.content = content;
    }
}
