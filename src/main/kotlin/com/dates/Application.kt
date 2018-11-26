package com.dates

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("com.dates")
                .mainClass(Application.javaClass)
                .start()
    }
}