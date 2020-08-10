/**
 *
 */
package com.good.framework.entity

import android.graphics.Color
import com.god.uikit.utils.asteriskMobile
import com.god.uikit.utils.changeTextColor
import java.io.Serializable

class Staff : Serializable {
    var id: Int? = null
    var name: String? = null
    var code: String? = null
    var password: String? = null
    var email: String? = null
    var mobile: String? = null
    var entryTime: String? = null
    var type: Int? = null
    var status: Int? = null
    var aesKey: String? = null
    var icon : String? = null;

    val mobileStar : String
        get() = mobile?.asteriskMobile()?:"";

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 5166581995305876730L
    }
}