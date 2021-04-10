package com.github.poluka.kControlLibrary.enity.position

import com.github.poluka.kControlLibrary.enity.Axes

fun Point.positionArrayFromString(text: String): Point {
    // Очищаем ненужный текст
    val text2 = text.substringAfter(";").substringBeforeLast(";")

    // Переводим текст в массив double
    val position = getDoubleArrayFromString(text2)

    // Загружаем все значения в массив
    for (coordinate in Axes.values()) {
        this[coordinate] = position[coordinate.ordinal]
    }

    return this
}

private fun getDoubleArrayFromString(position: String): List<Double> = position.split(";")
    .map { value ->
        String.format("%.2f", value.trim().toDouble())
            .replace(",", ".")
            .toDouble()
    }
