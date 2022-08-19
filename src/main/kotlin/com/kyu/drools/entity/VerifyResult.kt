package com.kyu.drools.entity

data class VerifyResult(val result: Boolean, val code: Int, val close: Boolean) {

    companion object {

        fun accept(): VerifyResult {
            return VerifyResult(true, 0, false)
        }

        fun deny(code: Int): VerifyResult {
            return VerifyResult(false, code, true)
        }

        fun deny(code: Int, close: Boolean): VerifyResult {
            return VerifyResult(false, code, close)
        }
    }
}