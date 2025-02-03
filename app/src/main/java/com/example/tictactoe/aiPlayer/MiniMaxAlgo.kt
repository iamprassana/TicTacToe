// aiPlayer.kt (updated)
package com.example.tictactoe.aiPlayer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlin.math.max
import kotlin.math.min

// Corrected player constants
const val AI = 'O'
const val HUMAN = 'X'

// State variables
var winner by mutableStateOf<String?>(null)
private var currentPlayer by mutableStateOf(HUMAN)

// Initialize board with individual cell states
val board = List(3) { row ->
    List(3) { col ->
        mutableStateOf<Char>(' ')
    }
}

fun checkWin(): String? {
    // Check rows
    for (i in 0..2) {
        if (board[i][0].value == board[i][1].value &&
            board[i][1].value == board[i][2].value &&
            board[i][0].value != ' ') {
            return board[i][0].value.toString()
        }
    }

    // Check columns
    for (i in 0..2) {
        if (board[0][i].value == board[1][i].value &&
            board[1][i].value == board[2][i].value &&
            board[0][i].value != ' ') {
            return board[0][i].value.toString()
        }
    }

    // Check diagonals
    if (board[0][0].value == board[1][1].value &&
        board[1][1].value == board[2][2].value &&
        board[0][0].value != ' ') {
        return board[0][0].value.toString()
    }

    if (board[0][2].value == board[1][1].value &&
        board[1][1].value == board[2][0].value &&
        board[0][2].value != ' ') {
        return board[0][2].value.toString()
    }

    // Check for tie
    return if (board.all { row -> row.all { it.value != ' ' } }) "tie" else null
}

fun bestTurn() {
    var bestScore = Int.MIN_VALUE
    var bestMove = Pair(-1, -1)

    for (i in 0..2) {
        for (j in 0..2) {
            if (board[i][j].value == ' ') {
                board[i][j].value = AI
                val score = miniMax(0, false)
                board[i][j].value = ' '

                if (score > bestScore) {
                    bestScore = score
                    bestMove = Pair(i, j)
                }
            }
        }
    }

    board[bestMove.first][bestMove.second].value = AI
    winner = checkWin()
    currentPlayer = HUMAN
}

fun miniMax(depth: Int, isMaximizing: Boolean): Int {
    val result = checkWin()
    return when {
        result == AI.toString() -> 1
        result == HUMAN.toString() -> -1
        result == "tie" -> 0
        else -> {
            if (isMaximizing) {
                var maxScore = Int.MIN_VALUE
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (board[i][j].value == ' ') {
                            board[i][j].value = AI
                            val score = miniMax(depth + 1, false)
                            board[i][j].value = ' '
                            maxScore = max(score, maxScore)
                        }
                    }
                }
                maxScore
            } else {
                var minScore = Int.MAX_VALUE
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (board[i][j].value == ' ') {
                            board[i][j].value = HUMAN
                            val score = miniMax(depth + 1, true)
                            board[i][j].value = ' '
                            minScore = min(score, minScore)
                        }
                    }
                }
                minScore
            }
        }
    }
}

fun resetGame() {
    board.forEach { row ->
        row.forEach { cell ->
            cell.value = ' '
        }
    }
    winner = null
    currentPlayer = HUMAN
}