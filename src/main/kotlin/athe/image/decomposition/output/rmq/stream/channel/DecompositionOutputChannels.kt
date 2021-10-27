package athe.image.decomposition.output.rmq.stream.channel

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface DecompositionOutputChannels {

    companion object {

        const val DECOMPOSITION_OUTPUT = "decomposition-output"
    }

    @Output(DECOMPOSITION_OUTPUT)
    fun decompositionOutput(): MessageChannel
}