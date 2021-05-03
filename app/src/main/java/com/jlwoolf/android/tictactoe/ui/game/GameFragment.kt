package com.jlwoolf.android.tictactoe.ui.game

import android.content.SharedPreferences
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.jlwoolf.android.tictactoe.R
import com.jlwoolf.android.tictactoe.data.Game
import com.jlwoolf.android.tictactoe.data.HistoryItem
import com.jlwoolf.android.tictactoe.databinding.FragmentGameBinding
import java.util.*


class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var boardMap: MutableMap<Int, ImageButton> = mutableMapOf()
    private var gameOver: Boolean = false

    private lateinit var gameViewModelFactory: GameViewModelFactory
    private lateinit var gameViewModel: GameViewModel
    private lateinit var sharedPreferences: SharedPreferences

    private var computerPlayer: Boolean = true
    private var computerFirst: Boolean = true
    private var computerDifficulty: Int = 1
    private var firstPiece: Char = 'x'

    companion object {
        private const val LOG_TAG = "TTT.GameFragment"
        private const val BOARD_INDEX = "BOARD"
    }

    //initializes the variable boardmap. Since fragment id's can be a pain
    //to type out repeatably, I create a board map that maps to each item
    //in the game fragments board
    private fun initializeBoardMap() {
        boardMap[0] = binding.topLeftGridButton
        boardMap[1] = binding.topCenterGridButton
        boardMap[2] = binding.topRightGridButton
        boardMap[3] = binding.leftGridButton
        boardMap[4] = binding.centerGridButton
        boardMap[5] = binding.rightGridButton
        boardMap[6] = binding.bottomLeftGridButton
        boardMap[7] = binding.bottomCenterGridButton
        boardMap[8] = binding.bottomRightGridButton
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(LOG_TAG, "onSaveInstanceState")
        savedInstanceState.putCharArray(BOARD_INDEX, gameViewModel.game.getCharArray())
    }

    //draws the board. Function reads the game board from the gameViewModel
    //and then, depending on the character stored, gets a resoure (x or) to set
    //the background of the image button to
    //it will also handle the displaying of the buttons to play again or go back
    //if the game ends
    private fun drawBoard() {
        Log.d(LOG_TAG, "drawBoard() called")
        for((k,v) in boardMap) {
            v.setBackgroundResource(getPieceResource(gameViewModel.game.board.grid[k]))
        }

        if(gameViewModel.game.endOfGame()) {
            when (gameViewModel.game.winningPlayer()) {
                "player_one" -> binding.gameStatusTextView.text = resources.getString(R.string.player_one_wins)
                "player_two" -> binding.gameStatusTextView.text = resources.getString(R.string.player_two_wins)
                "player" -> binding.gameStatusTextView.text = resources.getString(R.string.player_wins)
                "computer" -> binding.gameStatusTextView.text = resources.getString(R.string.computer_wins)
            }
            if(gameViewModel.game.draw())
                binding.gameStatusTextView.text = resources.getString(R.string.game_draws)
            binding.buttonLinearLayout.isVisible = true
        } else {
            if(!computerPlayer) {
                when(gameViewModel.game.currentPlayer()) {
                    "player_one" -> binding.gameStatusTextView.text = resources.getString(R.string.player_one_turn)
                    "player_two" -> binding.gameStatusTextView.text = resources.getString(R.string.player_two_turn)
                }
            } else {
                binding.gameStatusTextView.text = resources.getString(R.string.player_one_turn)
            }
        }
    }

    //takes a character and returns a theme dependent resoure
    //the color of the x's and o's change with respect to the theme
    private fun getPieceResource(piece: Char): Int {
        return when(piece) {
            'x' -> {
                val a: TypedArray = requireContext().obtainStyledAttributes(R.style.Theme_MaterialComponents_DayNight_DarkActionBar, intArrayOf(R.attr.img_x))
                return a.getResourceId(0, 0)
            }
            'o' ->  {
                val a: TypedArray = requireContext().obtainStyledAttributes(R.style.Theme_MaterialComponents_DayNight_DarkActionBar, intArrayOf(R.attr.img_o))
                return a.getResourceId(0, 0)
            }
            else -> R.drawable.blank
        }
    }

    //the function that is passed to the imagebutton clicklistener
    //will make a move on the board and then draw the board. Whether the move is
    //valid is handled by the game class
    //it will also save the game data after a game ends. I used a somewhat weird
    //technique here to make sure pressing a grid button even after the game ended
    //wouldn't save a new history item. Uses a boolean that "locks" the game in a state
    //of completion after the data is saved and doesn't change until play again is clicked
    fun makeMove(pos: Int) {
        gameViewModel.game.play(pos)
        drawBoard()

        if(gameViewModel.game.endOfGame() && !gameOver) {
            Log.d(LOG_TAG, "game over")
            val historyItem = HistoryItem(
                    winner = gameViewModel.game.winningPlayer(),
                    loser = gameViewModel.game.loosingPlayer(),
                    date = Calendar.getInstance().time,
                    loserPiece = gameViewModel.game.loosingPiece(),
                    winnerPiece = gameViewModel.game.winningPiece(),
                    outcome = gameViewModel.game.outcome())
            gameViewModel.addHistoryItem(historyItem)
            gameOver = true
        }
    }

    //reads in the game preferences from the settings
    //and also initializes the viewModel so that rotation does not
    //reset the board and so data of finished games can be saved
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        computerPlayer=
                when(sharedPreferences.getString("num_players", "one_player")) {
                    "one_player" -> true
                    else -> false
                }
        computerFirst =
                when(sharedPreferences.getBoolean("computer_first", false)) {
                    true -> true
                    false -> false
                }
        computerDifficulty =
                when(sharedPreferences.getString("computer_difficulty", "easy")) {
                    "easy" -> 1
                    "medium" -> 2
                    else -> 3
                }
        firstPiece =
                when(sharedPreferences.getString("who_plays_first", "x_first")) {
                    "x_first" -> 'x'
                    else -> 'o'
                }

        gameViewModelFactory = GameViewModelFactory(computerPlayer, computerFirst, computerDifficulty, firstPiece, requireContext())
        gameViewModel = ViewModelProvider(this, gameViewModelFactory).get(GameViewModel::class.java)
        gameViewModel.game = Game(computerPlayer, computerFirst, computerDifficulty, firstPiece)

        val charArray = savedInstanceState?.getCharArray(BOARD_INDEX)
        if(charArray != null) {
            gameViewModel.game.loadCharArray(charArray)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG_TAG, "onCreateView() called")
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        initializeBoardMap()
        return binding.root
    }

    //sets on click listeners for each image button in the grid
    //and also sets the click listeners for the play again button
    //and the go back button
    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")
        drawBoard()

        for((k,v) in boardMap) {
            v.setOnClickListener {
                makeMove(k)
            }
        }
        binding.playAgainButton.setOnClickListener {
            gameViewModel.game = Game(computerPlayer, computerFirst, computerDifficulty, firstPiece)
            drawBoard()
            binding.buttonLinearLayout.isVisible = false
            gameOver = false
        }
        binding.goBackButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestory() called")
    }
}