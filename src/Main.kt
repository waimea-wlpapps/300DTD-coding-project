/**
 * ------------------------------------------------------------------------
 * Explore New Zealand and Escape to Australia
 * Level 3 programming project
 *
 * by William Papps
 *
 * The project is a small map based game where you have to move from location to location its themed
 * around new zealand and you have to visit cities throughout new zealand and find specific items
 * once you have finished the task list you are able to go and fly to australia. There is a session leaderboard
 * that tracks your best scores on that instance on the game is run the leaderboard doesn't save
 * if you exit and relaunch the game.

 * ------------------------------------------------------------------------
 */


import com.formdev.flatlaf.FlatDarkLaf
import com.formdev.flatlaf.FlatLightLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


//=============================================================================================

/**
 * Location class
 * Defines the location and where they can move
 */

class Location(val name: String, val x: Int, val y: Int) {
    var north: Location? = null
    var east: Location? = null
    var south: Location? = null
    var west: Location? = null
}

/**
 * GUI class
 * Defines the UI and responds to events
 */

class GUI : JFrame(), KeyListener {
    //Array of all locations
    private val locations = arrayOf(
        Location("Auckland", 40, 40),
        Location("Wellington", 170, 40),
        Location("Christchurch", 300, 40),
        Location("Hamilton", 430, 40),
        Location("Dunedin", 40, 120),
        Location("Rotorua", 170, 120),
        Location("Napier", 300, 120),
        Location("New Plymouth", 430, 120),
        Location("Queenstown", 40, 200),
        Location("Nelson", 170, 200),
        Location("Invercargill", 300, 200),
        Location("Gisborne", 430, 200),
        Location("Tauranga", 40, 280),
        Location("Whangarei", 170, 280),
        Location("Palmerston North", 300, 280),
        Location("Australia", 430, 280) // Finish location
    )

    private var currentLocation: Location = locations[0] // Makes it so they start at location 0 fromt he array which is the starting location
    private var previousLocation: Location? = null

    private lateinit var characterLabel: JLabel
    private lateinit var moveCountLabel: JLabel
    private lateinit var taskList: JList<String>
    private lateinit var taskListModel: DefaultListModel<String>
    private lateinit var leaderboardList: JList<String>
    private lateinit var leaderboardModel: DefaultListModel<String>

    private var moveCount: Int = 0
    private var hasPassport: Boolean = false
    private var visitedQueenstown: Boolean = false
    private var hasMap: Boolean = false
    private var hasHint: Boolean = false
    private var hasSupplies: Boolean = false

    private val leaderboard = mutableListOf<Int>()
    private val allLeaderboards = mutableListOf<Int>()

    /**
     * Create, build and run the UI
     */

    init {
        setupWindow()
        buildUI()
        setupConnections()

        setLocationRelativeTo(null)
        isVisible = true
        updateCharacterPosition()

        addKeyListener(this)
        isFocusable = true
    }

    /**
     * Configure the connections between locations
     */

    private fun setupConnections() {
        // Sets up the connections between the locations so that they move to the correct position on key press
        val gridSize = 4

        for (i in locations.indices) {
            val row = i / gridSize
            val col = i % gridSize

            if (row > 0) {
                locations[i].north = locations[i - gridSize]
            }

            if (row < gridSize - 1) {
                locations[i].south = locations[i + gridSize]
            }

            if (col > 0) {
                locations[i].west = locations[i - 1]
            }

            if (col < gridSize - 1) {
                locations[i].east = locations[i + 1]
            }
        }
    }

    /**
     * Configure the main window
     */

    private fun setupWindow() {
        title = "Explore NewZealand and Escape to Australia"
        contentPane.preferredSize = Dimension(1000, 600)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null
        pack()
    }

    /**
     * Populate the UI
     */

    private fun buildUI() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 14)

        characterLabel = JLabel("\uD83D\uDFE2", SwingConstants.CENTER)
        characterLabel.font = baseFont
        characterLabel.bounds = Rectangle(0, 0, 70, 40)
        add(characterLabel)

        for (location in locations) {
            val label = JLabel(location.name, SwingConstants.CENTER)
            label.bounds = Rectangle(location.x, location.y, 100, 50)
            label.font = baseFont
            add(label)
        }

        val helpButton = JButton("Help")
        helpButton.bounds = Rectangle(20, 520, 120, 40)
        helpButton.addActionListener { showHelp() }
        add(helpButton)

        moveCountLabel = JLabel("Moves: $moveCount", SwingConstants.LEFT)
        moveCountLabel.bounds = Rectangle(150, 520, 300, 40)
        add(moveCountLabel)

        taskListModel = DefaultListModel<String>().apply {
            addElement("Collect the Map from Christchurch")
            addElement("Find a Hint in Rotorua")
            addElement("Check for Supplies in Wellington")
            addElement("Obtain the Passport from Invercargill")
            addElement("Visit Queenstown")
            addElement("Fly to Australia") // Final task
        }
        taskList = JList(taskListModel)
        taskList.bounds = Rectangle(600, 40, 320, 300)
        taskList.font = baseFont
        taskList.isEnabled = false
        add(taskList)

        leaderboardModel = DefaultListModel<String>()
        leaderboardList = JList(leaderboardModel)
        leaderboardList.bounds = Rectangle(600, 350, 320, 200)
        leaderboardList.font = baseFont
        leaderboardList.isEnabled = false
        add(leaderboardList)
    }

    /**
     * Handle any UI events
     */

    private fun showHelp() {
        JOptionPane.showMessageDialog(this, "Use arrow keys to move through New Zealand. Reach Australia to win!", "Help", JOptionPane.INFORMATION_MESSAGE)
    }


    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_LEFT -> moveLeft()
            KeyEvent.VK_RIGHT -> moveRight()
            KeyEvent.VK_UP -> moveUp()
            KeyEvent.VK_DOWN -> moveDown()
        }
    }

    private fun moveLeft() {
        previousLocation = currentLocation
        currentLocation.west?.let {
            currentLocation = it
            moveCount++
            updateCharacterPosition()
        }
    }

    private fun moveRight() {
        previousLocation = currentLocation
        currentLocation.east?.let {
            currentLocation = it
            moveCount++
            updateCharacterPosition()
        }
    }

    private fun moveUp() {
        previousLocation = currentLocation
        currentLocation.north?.let {
            currentLocation = it
            moveCount++
            updateCharacterPosition()
        }
    }

    private fun moveDown() {
        previousLocation = currentLocation
        currentLocation.south?.let {
            currentLocation = it
            moveCount++
            updateCharacterPosition()
        }
    }


    private fun updateCharacterPosition() {
        characterLabel.bounds = Rectangle(currentLocation.x, currentLocation.y, 70, 40)

        moveCountLabel.text = "Moves: $moveCount"

        /**
         * Checks the locations for items
         */

        // Checks to see if they're at the location and have the item there or not and if not it gives them the item there.
        if (currentLocation.name == "Invercargill" && !hasPassport) {
            hasPassport = true
            JOptionPane.showMessageDialog(this, "You found a passport!", "Item Collected", JOptionPane.INFORMATION_MESSAGE)
            taskListModel.set(3, "✓ Obtain the Passport from Invercargill")
        }

        if (currentLocation.name == "Christchurch" && !hasMap) {
            hasMap = true
            JOptionPane.showMessageDialog(this, "You found a map!", "Item Collected", JOptionPane.INFORMATION_MESSAGE)
            taskListModel.set(0, "✓ Collect the Map from Christchurch")
        }

        if (currentLocation.name == "Rotorua" && !hasHint) {
            hasHint = true
            JOptionPane.showMessageDialog(this, "You found a hint!", "Item Collected", JOptionPane.INFORMATION_MESSAGE)
            taskListModel.set(1, "✓ Find a Hint in Rotorua")
        }

        if (currentLocation.name == "Wellington" && !hasSupplies) {
            hasSupplies = true
            JOptionPane.showMessageDialog(this, "You found supplies!", "Item Collected", JOptionPane.INFORMATION_MESSAGE)
            taskListModel.set(2, "✓ Check for Supplies in Wellington")
        }

        if (currentLocation.name == "Queenstown" && !visitedQueenstown) {
            visitedQueenstown = true
            JOptionPane.showMessageDialog(this, "You visited Queenstown!", "Location Visited", JOptionPane.INFORMATION_MESSAGE)
            taskListModel.set(4, "✓ Visit Queenstown")
        }

        if (hasPassport && hasMap && hasHint && hasSupplies && visitedQueenstown) {
            if (currentLocation.name == "Australia") {
                JOptionPane.showMessageDialog(this, "Congratulations! You flew to Australia and finished.", "Game Finished", JOptionPane.INFORMATION_MESSAGE)
                taskListModel.set(5, "✓ Fly to Australia") // Mark final task as completed
                updateLeaderboard(moveCount)
                resetGame()
            }
        } else if (currentLocation.name == "Australia") {
            // Teleport back to the last location if the player tries to move to Australia without completing the tasks
            currentLocation = previousLocation ?: currentLocation
            JOptionPane.showMessageDialog(this, "You need to gather all necessary items before flying to Australia. Returning to your last location.", "Cannot Fly", JOptionPane.WARNING_MESSAGE)
            updateCharacterPosition()
        }
    }

    private fun updateLeaderboard(moves: Int) {
        leaderboard.add(moves)
        allLeaderboards.add(moves)
        leaderboard.sort()
        allLeaderboards.sort()

        leaderboardModel.clear()
        leaderboard.forEachIndexed { index, moves ->
            leaderboardModel.addElement("Score: $moves moves")
        }
    }

    /**
     * Restart the application
     */

    // Resets everything in the game back to defaults so that they can play again
    private fun resetGame() {
        currentLocation = locations[0]
        moveCount = 0
        hasPassport = false
        hasMap = false
        hasHint = false
        hasSupplies = false
        visitedQueenstown = false

        // sets all the tasks back to incomplete

        taskListModel.set(0, "Collect the Map from Christchurch")
        taskListModel.set(1, "Find a Hint in Rotorua")
        taskListModel.set(2, "Check for Supplies in Wellington")
        taskListModel.set(3, "Obtain the Passport from Invercargill")
        taskListModel.set(4, "Visit Queenstown")
        taskListModel.set(5, "Fly to Australia")
        updateCharacterPosition()
    }

    override fun keyReleased(e: KeyEvent) {}
    override fun keyTyped(e: KeyEvent) {}
}

/**
 * Launch the application
 */

fun main() {
    FlatDarkLaf.setup()
    EventQueue.invokeLater { GUI() }
}
