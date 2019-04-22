package com.haejung.droneserver.drones

import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 *
 * @author haejung
 * @since 19. 4. 22
 */
interface DroneRepository : JpaRepository<Drone, Long> {
    fun findByName(name: String): Drone
}
