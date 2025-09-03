package com.gk.happytteokordersystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing

@SpringBootApplication
class HappytteokOrderSystemApplication


fun main(args: Array<String>) {
    runApplication<HappytteokOrderSystemApplication>(*args)
}
