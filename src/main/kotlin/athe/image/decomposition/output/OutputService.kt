package athe.image.decomposition.output

import java.awt.image.BufferedImage

interface OutputService {

    fun send(image: BufferedImage)
}