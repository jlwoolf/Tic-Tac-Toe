package com.jlwoolf.android.tictactoe.data

class Board(parent: Board?) {
    val grid: MutableList<Char> = mutableListOf()

    //initializes the board to either be that of a parent
    //(useful for the minimax) or a blank board
    init {
        if (parent != null) {
            grid.addAll(parent.grid)
        } else {
            grid.addAll(listOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '))
        }
    }

    fun clearBoard() {
        grid.clear()
        grid.addAll(listOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '))
    }

    //handles the making of a move on the board
    fun makeMove(pos: Int, piece: Char): Boolean {
        return when(grid[pos]) {
            ' ' -> {
                grid[pos] = piece
                true
            } else -> {
                false
            }
        }
    }

    //detects if a given piece is in a winning
    //state. I manually programmed all options as
    //it wasn't really that hard and there were only 8
    //win combinations
    fun detectWin(piece: Char): Boolean {
        if(grid[0] == grid[1]
                && grid[1] == grid[2]
                && grid[0] == piece)
            return true
        if(grid[3] == grid[4]
                && grid[4] == grid[5]
                && grid[3] == piece)
            return true
        if(grid[6] == grid[7]
                && grid[7] == grid[8]
                && grid[6] == piece)
            return true

        if(grid[0] == grid[3]
                && grid[3] == grid[6]
                && grid[0] == piece)
            return true
        if(grid[1] == grid[4]
                && grid[4] == grid[7]
                && grid[1] == piece)
            return true
        if(grid[2] == grid[5]
                && grid[5] == grid[8]
                && grid[2] == piece)
            return true

        if(grid[0] == grid[4]
                && grid[4] == grid[8]
                && grid[0] == piece)
            return true
        if(grid[2] == grid[4]
                && grid[4] == grid[6]
                && grid[2] == piece)
            return true

        return false
    }

    //detects if the game is over. Either the board
    //is full or there is three in a row of a piece
    fun endOfGame(): Boolean {
        if(detectWin('o'))
            return true
        if(detectWin('x'))
            return true

        for(i in 0..8) {
            if(grid[i] == ' ')
                return false
        }
        return true
    }

    //returns a list of valid moves. Used for the mininmax
    //algorithm
    fun getPossibleMoves(): List<Int> {
        val moves: MutableList<Int> = mutableListOf()
        for(i in 0..8) {
            if(grid[i] == ' ')
                moves.add(i)
        }
        return moves
    }
}