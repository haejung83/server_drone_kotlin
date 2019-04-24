package com.haejung.droneserver.drones

import com.haejung.droneserver.files.FileStorage
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
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
@RequestMapping(value = ["/drones"])
class DroneController(
        private val droneRepository: DroneRepository,
        private val fileStorage: FileStorage
) {

    @PostConstruct
    fun setup() {
        if (droneRepository.count() == 0L) {
            val saveLoopCount = 5
            with(droneRepository) {
                for (i in 0 until saveLoopCount) {
                    save(Drone("SmoothOperator_$i", "Quad", "HelioSpring", 5, "smooth.jpg"))
                    save(Drone("Chameleon_$i", "Quad", "Kakute F4", 5, "chameleon.jpg"))
                    save(Drone("CG_$i", "Quad", "Helio Spring", 5, "cg.jpg"))
                    save(Drone("Stingy_$i", "Quad", "Helio Spring", 5, "stingy.jpg"))
                    save(Drone("Japalura_$i", "Quad", "Kakute F4", 3, "japalura.jpg"))
                }
            }
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

    @GetMapping("/image/{quadName}")
    fun getDroneImage(@PathVariable quadName: String): ResponseEntity<Resource> {
        val drone: Drone? = droneRepository.findByName(quadName)
        val file: Resource? = drone?.image?.let { fileStorage.loadFile(it) }
        val resource: ResponseEntity<Resource>? = file?.run {
            ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${file.filename}")
                    .body(this)
        }
        return resource ?: ResponseEntity.notFound().build()
    }

}

