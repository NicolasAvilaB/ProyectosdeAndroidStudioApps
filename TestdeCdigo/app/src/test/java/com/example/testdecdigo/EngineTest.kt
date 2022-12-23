package com.example.testdecdigo

import org.junit.Test

internal class EngineTest {
    private var state = "OFF"

    @Test
    fun start(): String{

        this.state = "ON"

        return this.state

    }
}