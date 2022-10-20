package com.ismaeldivita.chipnavigation.sample

class Data(s: String, s1: String, s2: String, s3: String) {

    private var s: String = ""
    private var s1: String = ""
    private var s2: String = ""
    private var s3: String = ""

    fun Data(){}

    fun Data(s: String, s1: String, s2: String?, s3: String?) {
        this.s = s!!
        this.s1 = s1!!
        this.s2 = s2!!
        this.s3 = s3!!
    }

    fun getS(): String? {
        return s
    }

    fun setS(s: String?) {
        this.s = s!!
    }

    fun getS1(): String? {
        return s1
    }

    fun setS1(s1: String?) {
        this.s1 = s1!!
    }

    fun getS2(): String? {
        return s2
    }

    fun setS2(s2: String?) {
        this.s2 = s2!!
    }

    fun getS3(): String? {
        return s3
    }

    fun setS3(s3: String?) {
        this.s3 = s3!!
    }
}