package com.haejung.droneserver.files

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream

/**
 *
 *
 * @author haejung
 * @since 19. 4. 24
 */

@Service
class FileStorage {
    private val storagePath = Paths.get("files")

    init {
        if (Files.notExists(storagePath))
            Files.createDirectory(storagePath)
    }

    fun store(file: MultipartFile) {
        Files.copy(file.inputStream, storagePath.resolve(file.originalFilename))
    }

    fun loadFile(filename: String): Resource {
        val file = storagePath.resolve(filename)
        val resource = UrlResource(file.toUri())

        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw FileNotFoundException("$filename not exists")
        }
    }

    fun delete(filename: String) {
        if (Files.exists(storagePath.resolve(filename)))
            Files.delete(storagePath.resolve(filename))
    }

    fun deleteAll() {
        FileSystemUtils.deleteRecursively(storagePath.toFile())
    }

    fun loadFiles(): Stream<Path> {
        return Files.walk(storagePath, 1)
                .filter { path -> path != storagePath }
                .map(storagePath::relativize)
    }
}