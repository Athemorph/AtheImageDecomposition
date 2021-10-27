package athe.image.decomposition.output.local

import athe.image.decomposition.output.OutputService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import javax.imageio.ImageIO

@Service
@Profile("local")
class LocalOutputService(
    @Value("\${service.output.local.path}")
    private val storagePath: String,

    @Value("\${service.output.image-type}")
    private val imageType: String
) : OutputService {

    init {
        File(storagePath).apply {
            if (!this.exists()) {
                this.mkdir()
            }
        }
    }

    override fun send(image: BufferedImage) {
        ImageIO.write(
            image,
            imageType,
            File(storagePath + "\\" + UUID.randomUUID().toString() + ".$imageType").apply { createNewFile() }
        )
    }
}