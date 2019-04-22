package com.haejung.droneserver.drones

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

/**
 *
 *
 * @author haejung
 * @since 19. 4. 22
 */

@RestController
@RequestMapping(value = "/drones")
class DroneController constructor(private val droneRepository: DroneRepository) {

    @PostConstruct
    fun setup() {
        if (droneRepository.findAll().isEmpty()) {
            droneRepository.save(Drone("SmoothOperator", "quad", "helio spring", 5, 1300))
            droneRepository.save(Drone("Chameleon", "quad", "kakute f4", 5, 1300))
            droneRepository.save(Drone("CG", "quad", "helio spring", 5, 1300))
            droneRepository.save(Drone("Stingy", "quad", "helio spring", 5, 1300))
        }
    }

    @GetMapping("")
    fun listDrone(): List<Drone> {
        return droneRepository.findAll()
    }

    @GetMapping("/{quadName}")
    fun getDrone(@PathVariable quadName: String): Drone {
        return droneRepository.findByName(quadName)
    }

}

