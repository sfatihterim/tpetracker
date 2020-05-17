package com.nsfl.tpetracker.html

import com.nsfl.tpetracker.model.player.Player
import com.nsfl.tpetracker.model.position.Position

fun List<Player>.getTotalTPE() = sumBy { it.currentTPE }

fun List<Player>.getAverageTPE() = getTotalTPE() / countOrOne()

fun List<Player>.getTotalEffectiveTPE() = sumBy { if (it.currentTPE > 250) 250 else it.currentTPE }

fun List<Player>.getAverageEffectiveTPE() = getTotalEffectiveTPE() / countOrOne()

fun List<Player>.getOffensiveTPE(dsfl: Boolean): Int {

    val qbList = filter { it.position == Position.QB }
            .sortedByDescending { it.currentTPE }

    val offenseList = filter {
        it.position == Position.RB
                || it.position == Position.WR
                || it.position == Position.TE
    }.sortedByDescending { it.currentTPE }

    return getPlayerTPESafe(qbList, 0, dsfl) +
            getPlayerTPESafe(offenseList, 0, dsfl) +
            getPlayerTPESafe(offenseList, 1, dsfl) +
            getPlayerTPESafe(offenseList, 2, dsfl) +
            getPlayerTPESafe(offenseList, 3, dsfl) +
            getPlayerTPESafe(offenseList, 4, dsfl)
}

fun List<Player>.getDefensiveTPE(dsfl: Boolean): Int {

    val defenseList = filter {
        it.position == Position.DE
                || it.position == Position.DT
                || it.position == Position.LB
                || it.position == Position.CB
                || it.position == Position.S
    }.sortedByDescending { it.currentTPE }

    return getPlayerTPESafe(defenseList, 0, dsfl) +
            getPlayerTPESafe(defenseList, 1, dsfl) +
            getPlayerTPESafe(defenseList, 2, dsfl) +
            getPlayerTPESafe(defenseList, 3, dsfl) +
            getPlayerTPESafe(defenseList, 4, dsfl) +
            getPlayerTPESafe(defenseList, 5, dsfl) +
            getPlayerTPESafe(defenseList, 6, dsfl) +
            getPlayerTPESafe(defenseList, 7, dsfl) +
            getPlayerTPESafe(defenseList, 8, dsfl) +
            getPlayerTPESafe(defenseList, 9, dsfl) +
            getPlayerTPESafe(defenseList, 10, dsfl)
}

private fun getPlayerTPESafe(playerList: List<Player>, index: Int, dsfl: Boolean) = try {
    if (dsfl && playerList[index].currentTPE > 250) 250 else playerList[index].currentTPE
} catch (exception: Exception) {
    50
}

fun <T> Iterable<T>.countOrOne() = count().let { if (it == 0) 1 else it }

fun <T, R : Comparable<R>> Iterable<T>.minBy(default: R, selector: (T) -> R): R {
    val min = minBy { selector.invoke(it) }
    return if (min == null) {
        default
    } else {
        selector(min)
    }
}

fun <T, R : Comparable<R>> Iterable<T>.maxBy(default: R, selector: (T) -> R): R {
    val max = maxBy { selector.invoke(it) }
    return if (max == null) {
        default
    } else {
        selector(max)
    }
}
