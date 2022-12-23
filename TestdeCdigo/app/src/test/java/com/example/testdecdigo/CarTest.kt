package com.example.testdecdigo

import junit.framework.Assert.assertEquals
import org.junit.Test

internal class CarTest {

    @Test
    fun carStart(){
        val sut = Car()
        assertEquals("ON",sut.start())
    }
}