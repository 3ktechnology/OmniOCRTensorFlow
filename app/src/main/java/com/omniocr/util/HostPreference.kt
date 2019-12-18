package com.omniocr.util

import android.content.Context
import com.omni.omnisdk.OmniApplication

class HostPreference {
    var a = "abc"
    var b = a + "" + a

    companion object {
        fun setPreferences(key: String?, value: String?) {
            val editor = OmniApplication.instance.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE
            ).edit()
            editor.putString(key, value)
            editor.commit()
        }

        fun getPreferences(key: String?): String? {
            val prefs = OmniApplication.instance.getSharedPreferences(
                "WED_APP",
                Context.MODE_PRIVATE
            )
            return prefs.getString(key, "")
        }

        fun setBoolean(key: String?, value: Boolean?) {
            val editor = OmniApplication.instance.getSharedPreferences(
                "WED_APP", Context.MODE_PRIVATE
            ).edit()
            editor.putBoolean(key, value!!)
            editor.commit()
        }

        fun getBoolean(key: String?): Boolean {
            val prefs = OmniApplication.instance.getSharedPreferences(
                "WED_APP",
                Context.MODE_PRIVATE
            )
            return prefs.getBoolean(key, false)
        }
    }
}