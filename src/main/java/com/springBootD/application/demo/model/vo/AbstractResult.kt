package com.springBootD.application.demo.model.vo

import java.io.Serializable

/**
 * 类的描述.
 *
 * @author tan liansheng on 2017/10/12 19:30
 */
class AbstractResult : Serializable {

    var code: String? = null

    var msg: String? = null

    var content: Map<String, String>? = null

    constructor() {}

    constructor(code: String, msg: String) {
        this.code = code
        this.msg = msg
    }

    companion object {

        private const val serialVersionUID = 1547388657941513953L
    }
}
