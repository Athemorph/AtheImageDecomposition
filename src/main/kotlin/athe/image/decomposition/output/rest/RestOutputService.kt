package athe.image.decomposition.output.rest

import athe.image.decomposition.output.OutputService
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage

@Service
@Profile("rest")
class RestOutputService : OutputService {

    override fun send(image: BufferedImage) {
        TODO("Not yet implemented")
    }
}