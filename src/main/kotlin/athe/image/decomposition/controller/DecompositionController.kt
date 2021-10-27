package athe.image.decomposition.controller

import athe.image.decomposition.service.DecompositionService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.imageio.ImageIO

@RestController
@RequestMapping("/api/decompose")
class DecompositionController(private val decompositionService: DecompositionService) {

    @PutMapping
    fun decomposeImage(@RequestBody content: ByteArray) {
        decompositionService.execute(ImageIO.read(content.inputStream()))
    }
}