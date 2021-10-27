package athe.image.decomposition.service

import athe.image.decomposition.output.OutputService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.image.BufferedImage
import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*
import kotlin.math.sqrt

@Service
class DecompositionService(
    private val outputService: OutputService,

    @Value("\${service.decomposition.sensitivity}")
    private val searchSensitivity: Int,

    @Value("\${service.decomposition.min-density}")
    private val minDensity: Int
) {

    fun execute(image: BufferedImage) {
        decompose(image).map { image.getSubimage(it) }.filter { !it.isSolid() }.forEach { outputService.send(it) }
    }

    private fun decompose(image: BufferedImage): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        val markedPixels = Array(image.width) { Array<String?>(image.height) { null } }
        val borders = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                if (markedPixels[x][y] != null) {
                    continue
                }

                search(x, y, image, markedPixels).apply {
                    if (this.isEnoughDensity()) {
                        borders.add(this)
                    }
                }
            }
        }

        return borders
    }

    private fun BufferedImage.isSolid(): Boolean {
        val colors = mutableSetOf<Int>()
        for (x in 0 until this.width) {
            for (y in 0 until this.height) {
                colors.add(this.getRGB(x, y))
            }
        }

        return colors.size == 1
    }

    private fun search(
        startX: Int,
        startY: Int,
        image: BufferedImage,
        markedPixels: Array<Array<String?>>
    ): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val initialColor = Color(image.getRGB(startX, startY), true)
        val pixelId = UUID.randomUUID().toString()

        var minX = startX
        var maxX = startX
        var minY = startY
        var maxY = startY

        var adjacency = setOf(Pair(startX, startY))

        while (adjacency.isNotEmpty()) {
            adjacency.forEach { markedPixels[it.first][it.second] = pixelId }
            minX = min(adjacency.minByOrNull { it.first }?.first ?: minX, minX)
            minY = min(adjacency.minByOrNull { it.second }?.second ?: minY, minY)
            maxX = max(adjacency.maxByOrNull { it.first }?.first ?: maxX, maxX)
            maxY = max(adjacency.maxByOrNull { it.second }?.second ?: maxY, maxY)

            adjacency = adjacency.flatMap {
                setOf(
                    Pair(it.first - 1, it.second),
                    Pair(it.first, it.second - 1),
                    Pair(it.first + 1, it.second),
                    Pair(it.first, it.second + 1)
                )
            }
                .filter {
                    it.first in 0 until image.width && it.second in 0 until image.height
                            && initialColor.isInSenseRange(Color(image.getRGB(it.first, it.second)))
                            && markedPixels[it.first][it.second] == null
                }.toSet()
        }

        return Pair(Pair(minX, minY), Pair(maxX, maxY))
    }

    private fun Color.isInSenseRange(color: Color) =
        sqrt(((this.red - color.red) * (this.red - color.red) + (this.green - color.green) * (this.green - color.green) + (this.blue - color.blue) * (this.blue - color.blue)).toDouble()) <= searchSensitivity

    private fun Pair<Pair<Int, Int>, Pair<Int, Int>>.isEnoughDensity() =
        (this.second.first - this.first.first >= minDensity) && (this.second.second - this.first.second >= minDensity)

    private fun BufferedImage.getSubimage(borders: Pair<Pair<Int, Int>, Pair<Int, Int>>) = this.getSubimage(
        borders.first.first,
        borders.first.second,
        borders.second.first - borders.first.first,
        borders.second.second - borders.first.second
    )
}