package com.haejung.droneserver.drones

import javax.persistence.*


/**
 *
 *
 * @author haejung
 * @since 19. 4. 22
 */

@Entity(name = "Drone")
@Table(name = "tbl_drone")
data class Drone constructor(
        var name: String,
        var type: String,
        var fc: String,
        var size: Int,
        var image: String,
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        var id: Long? = null
)

