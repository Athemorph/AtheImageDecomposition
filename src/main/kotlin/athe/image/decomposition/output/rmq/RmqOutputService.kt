package athe.image.decomposition.output.rmq

import athe.image.decomposition.output.OutputService
import athe.image.decomposition.output.rmq.stream.channel.DecompositionOutputChannels
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.context.annotation.Profile
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
@Profile("rmq")
@EnableBinding(DecompositionOutputChannels::class)
class RmqOutputService(
    private val decompositionOutputChannels: DecompositionOutputChannels,

    @Value("\${service.output.image-type}")
    private val imageType: String
) : OutputService {

    override fun send(image: BufferedImage) {
        decompositionOutputChannels.decompositionOutput().send(GenericMessage(ByteArrayOutputStream().apply {
            ImageIO.write(image, imageType, this)
        }.toByteArray()))
    }
}