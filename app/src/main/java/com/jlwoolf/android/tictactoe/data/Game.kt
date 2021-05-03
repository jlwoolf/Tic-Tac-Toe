package com.jlwoolf.android.tictactoe.data

import android.util.Log
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

//game class that takes a bunch of setting inputs
//and handles the technical side of the tik tak toe
//game
class Game(var computerPlayer: Boolean,
           var computerFirst: Boolean,
           var computerDifficulty: Int,
           var firstPiece: Char) {

    val board = Board(null)
    var playerMap: MutableMap<Char, String> = mutableMapOf()
    var currentPiece: Char = firstPiece

    companion object {
        private const val LOG_TAG = "TTT.Game"
    }

    //this initializes the player map which makes it easier
    //to save the data of a game. It associates each piece with a player
    init {
        when {
            computerPlayer && computerFirst -> {
                playerMap[firstPiece] = "computer"
                playerMap[oppositePiece(firstPiece)] = "player"
                computerMove(firstPiece)
                currentPiece = oppositePiece(firstPiece)
            }
            computerPlayer && !computerFirst ->  {
                playerMap[firstPiece] = "player"
                playerMap[oppositePiece(firstPiece)] = "computer"
            }
            !computerPlayer -> {
                playerMap[firstPiece] = "player_one"
                playerMap[oppositePiece(firstPiece)] = "player_two"
            }
        }
    }

    //this function is the main function used by the fragment
    //it takes a move input, and if it is a valid move, updates the board
    //it also will do a computer move if it is a one player game
    fun play(pos: Int){
        if(board.endOfGame()) {
            Log.d(LOG_TAG, "end of game")
            return
        }
        if(board.makeMove(pos, currentPiece)) {
            currentPiece = oppositePiece(currentPiece)
            if(computerPlayer && !board.endOfGame()) {
                computerMove(currentPiece)
                currentPiece = oppositePiece(currentPiece)
            }
        }
    }

    fun getCharArray() : CharArray {
        val charArray = CharArray(9)
        for(i in 0..8) {
            charArray[i] = board.grid[i]
        }
        return charArray
    }
    fun loadCharArray(charArray: CharArray) {
        for(i in 0..8) {
            board.grid[i] = charArray[i]
        }
    }

    //some of these functions could probably be removed but
    //the ones visible from here to the next comment serve to make
    //creating the history data entries easier along with disabling
    //the board upon the end of a game
    fun endOfGame(): Boolean {
        return board.endOfGame()
    }
    fun winningPlayer(): String {
        return when {
            board.detectWin('x') -> playerMap['x']!!
            board.detectWin('o') -> playerMap['o']!!
            else -> playerMap[firstPiece]!!
        }
    }
    fun draw(): Boolean {
        return !board.detectWin('x') && !board.detectWin('o')
    }
    fun winningPiece(): Char {
        return when {
            board.detectWin('x') -> 'x'
            board.detectWin('o') -> 'o'
            else -> firstPiece
        }
    }
    fun loosingPlayer(): String {
        return when {
            board.detectWin('x') -> playerMap['o']!!
            board.detectWin('o') -> playerMap['x']!!
            else -> playerMap[oppositePiece(firstPiece)]!!
        }
    }
    fun loosingPiece(): Char {
        return when {
            board.detectWin('x') -> 'o'
            board.detectWin('o') -> 'x'
            else -> oppositePiece(firstPiece)
        }
    }
    fun currentPlayer(): String {
        return playerMap[currentPiece]!!
    }
    fun outcome(): Outcome {
        if(draw())
            return Outcome.DRAW
        return when(winningPlayer()) {
            "computer" -> Outcome.LOSS
            "player" -> Outcome.WIN
            "player_one" -> Outcome.WIN
            "player-two"-> Outcome.LOSS
            else -> Outcome.DRAW
        }
    }

    //this function was used to interchange between x and o
    //char/pieces. It made more sense for me when programming to do this
    //rather than have a boolean called xpiece or something similar
    private fun oppositePiece(piece: Char): Char {
        return when(piece) {
            'x' -> 'o'
            'o' -> 'x'
            else -> ' '
        }
    }

    //the code below is a minimax algorithm for the computer AI
    //since even a single depth algorithm was too smart, the easiest difficulty
    //is completely random, and medium is difficult but beatable. Hard is
    //impossible to win, the best you can do is a draw
    //I figure since it was not required in the project I don't need to go into
    //depth about how it works
    private fun computerMove(piece: Char) {
        if(computerDifficulty == 1) {
            board.makeMove(board.getPossibleMoves()[Random.nextInt(0, board.getPossibleMoves().size)], piece)
        } else {
            board.makeMove(minimaxDecision(getDepth(), piece), piece)
        }
    }

    private fun getDepth(): Int {
        return when(computerDifficulty) {
            1 -> 1
            2 -> 2
            else -> 8
        }
    }

    private fun minimaxDecision(depth: Int, piece: Char): Int {
        var utility: Int = Int.MIN_VALUE
        val moves: MutableList<Int> = mutableListOf()
        for(i in board.getPossibleMoves()) {
            val child: Board = Board(board)
            child.makeMove(i, piece)
            val tempUtility = minValue(child, piece,depth - 1, Int.MIN_VALUE, Int.MAX_VALUE)
            if(tempUtility > utility) {
                utility = tempUtility
                moves.clear()
                moves.add(i)
            } else if(tempUtility == utility) {
                moves.add(i)
            }
        }
        return moves[Random.nextInt(0, moves.size)]
    }
    private fun minValue(board: Board, piece: Char, depth: Int, alpha: Int, beta: Int): Int {
        if(board.endOfGame() || depth < 0) {
            return utility(board, piece)
        }

        var utility: Int = Int.MAX_VALUE
        var _beta: Int = beta
        for(i in board.getPossibleMoves()) {
            val child: Board = Board(board)
            child.makeMove(i, oppositePiece(piece))
            utility = min(utility, maxValue(child, piece, depth - 1, alpha, _beta))
            if(utility <= alpha)
                return utility
            _beta = min(_beta, utility)
        }
        return utility
    }

    private fun maxValue(board: Board, piece: Char, depth: Int, alpha: Int, beta: Int): Int {
        if(board.endOfGame() || depth < 0) {
            return utility(board, piece)
        }

        var utility: Int = Int.MIN_VALUE
        var _alpha: Int = alpha
        for(i in board.getPossibleMoves()) {
            val child: Board = Board(board)
            child.makeMove(i, piece)
            utility = max(utility, minValue(child, piece, depth - 1, _alpha, beta))
            if(utility >= beta)
                return utility
            _alpha = max(_alpha, utility)
        }
        return utility
    }

    private fun utility(board: Board, piece: Char): Int {
        return when {
            board.detectWin(piece) -> 1
            board.detectWin(oppositePiece(piece)) -> -1
            else -> 0
        }
    }
}