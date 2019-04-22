package com.haejung.droneserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DroneserverApplication

fun main(args: Array<String>) {
    runApplication<DroneserverApplication>(*args)
}
