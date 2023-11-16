package com.daeseong.businfo

object String_util {

    fun isNumeric(sInput: String): Boolean {
        val regex = "[\\d]*$"
        return sInput.matches(regex.toRegex())
    }
}