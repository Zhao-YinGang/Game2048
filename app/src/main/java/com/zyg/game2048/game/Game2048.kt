package com.zyg.game2048.game

import kotlin.random.Random

private const val TAG = "Game2048"

object Game2048 {
    fun interface OnGameOverListener {
        operator fun invoke()
    }

    fun interface OnUpdateDataListener {
        operator fun invoke(squares: Array<Array<Int>>)
    }

    const val DEFAULT_RANKS = 4 // 默认行列数
    private const val NEXT_NUM_2 = 2 // 下一个数字2
    private const val NEXT_NUM_4 = 4 // 下一个数字4
    private const val DIFFICULTY = 0.2 // 难度(下一个数字生成4而不是2的几率)

    private var squares = Array(DEFAULT_RANKS) { Array(DEFAULT_RANKS) { 0 } } // 保持数据
    private var ranks: Int // 行列数
        set(value) {
            squares = Array(value) { Array(value) { 0 } }
        }
        get() = squares.size


    private var onGameOverListener: OnGameOverListener? = null
    private var onUpdateDataListener: OnUpdateDataListener? = null

    /**
     * 开始游戏
     */
    fun start(ranks: Int) {
        this.ranks = ranks
        fillNextEmptyPosition()
    }

    /**
     * 游戏结束通知回调
     */
    fun setOnGameOverListener(onGameOverListener: OnGameOverListener?) {
        this.onGameOverListener = onGameOverListener
    }

    /**
     * 游戏数据刷新通知回调
     */
    fun setOnUpdateDataListener(onUpdateDataListener: OnUpdateDataListener?) {
        this.onUpdateDataListener = onUpdateDataListener
    }

    /**
     * 上滑
     */
    fun flingUp() {
        margeUp()

        var row = 0
        var column = 0
        while (column < ranks) {
            while (row + 1 < ranks) {
                if (squares[row][column] == squares[row + 1][column]) {
                    squares[row][column] *= 2
                    squares[row + 1][column] = 0
                }
                ++row
            }
            row = 0
            ++column
        }

        margeUp()
        fillNextEmptyPosition()
    }

    /**
     * 下滑
     */
    fun flingDown() {
        margeDown()

        var row = ranks - 1
        var column = 0
        while (column < ranks) {
            while (row > 0) {
                if (squares[row][column] == squares[row - 1][column]) {
                    squares[row][column] *= 2
                    squares[row - 1][column] = 0
                }
                --row
            }
            row = ranks - 1
            ++column
        }

        margeDown()
        fillNextEmptyPosition()
    }

    /**
     * 左滑
     */
    fun flingLeft() {
        margeLeft()

        var column = 0
        var row = 0
        while (row < ranks) {
            while (column + 1 < ranks) {
                if (squares[row][column] == squares[row][column + 1]) {
                    squares[row][column] *= 2
                    squares[row][column + 1] = 0
                }
                ++column
            }
            column = 0
            ++row
        }

        margeLeft()
        fillNextEmptyPosition()
    }

    /**
     * 右滑
     */
    fun flingRight() {
        margeRight()

        var column = ranks - 1
        var row = 0
        while (row < ranks) {
            while (column > 0) {
                if (squares[row][column] == squares[row][column - 1]) {
                    squares[row][column] *= 2
                    squares[row][column - 1] = 0
                }
                --column
            }
            column = ranks - 1
            ++row
        }

        margeRight()
        fillNextEmptyPosition()
    }

    /**
     * 向上合并
     */
    private fun margeUp() {
        var row = 0
        var column = 0

        val columnsTmp = mutableListOf<Int>()
        while (column < ranks) {
            while (row < ranks) {
                if (squares[row][column] != 0) {
                    columnsTmp.add(squares[row][column])
                }
                squares[row][column] = 0
                ++row
            }

            row = 0
            while (row < columnsTmp.size) {
                squares[row][column] = columnsTmp[row]
                ++row
            }

            row = 0
            columnsTmp.clear()
            ++column
        }
    }

    /**
     * 向下合并
     */
    private fun margeDown() {
        var row = ranks - 1
        var column = 0

        val columnsTmp = mutableListOf<Int>()
        while (column < ranks) {
            while (row >= 0) {
                if (squares[row][column] != 0) {
                    columnsTmp.add(squares[row][column])
                }
                squares[row][column] = 0
                --row
            }

            row = 0
            while (row < columnsTmp.size) {
                squares[ranks - 1 - row][column] = columnsTmp[row]
                ++row
            }

            row = ranks - 1
            columnsTmp.clear()
            ++column
        }
    }

    /**
     * 向左合并
     */
    private fun margeLeft() {
        var column = 0
        var row = 0

        val rowsTmp = mutableListOf<Int>()
        while (row < ranks) {
            while (column < ranks) {
                if (squares[row][column] != 0) {
                    rowsTmp.add(squares[row][column])
                }
                squares[row][column] = 0
                ++column
            }

            column = 0
            while (column < rowsTmp.size) {
                squares[row][column] = rowsTmp[column]
                ++column
            }

            column = 0
            rowsTmp.clear()
            ++row
        }
    }

    /**
     * 向右合并
     */
    private fun margeRight() {
        var column = ranks - 1
        var row = 0

        val columnsTmp = mutableListOf<Int>()
        while (row < ranks) {
            while (column >= 0) {
                if (squares[row][column] != 0) {
                    columnsTmp.add(squares[row][column])
                }
                squares[row][column] = 0
                --column
            }

            column = 0
            while (column < columnsTmp.size) {
                squares[row][ranks - 1 - column] = columnsTmp[column]
                ++column
            }

            column = ranks - 1
            columnsTmp.clear()
            ++row
        }
    }

    /**
     * 生成下一个出现的数字
     */
    private fun generateNextNum() =
        if (Random.nextDouble(1.0) < DIFFICULTY) NEXT_NUM_4 else NEXT_NUM_2


    /**
     * 寻找剩余的空位
     */
    private fun findEmptyPositions(): List<Pair<Int, Int>> {
        val positions = mutableSetOf<Pair<Int, Int>>()

        squares.forEachIndexed { row, rows ->
            rows.forEachIndexed { column, value ->
                if (value == 0) {
                    positions.add(row to column)
                }
            }
        }

        return positions.toList()
    }

    /**
     * 寻找剩余的空位，如果没有找到则游戏结束，如果找到则随机挑选一个填充
     */
    private fun fillNextEmptyPosition() {
        val positions = findEmptyPositions()

        if (positions.isEmpty()) {
            onGameOverListener?.invoke()
        } else {
            val index = Random.nextInt(positions.size)
            val position = positions[index]
            squares[position.first][position.second] = generateNextNum()
            onUpdateDataListener?.invoke(squares)
        }
    }

    @JvmStatic
    fun main(arg: Array<String>?) {

        squares.forEach { println(it.toList()) }
        for (i in 0..80) {
            println("----------------")
            fillNextEmptyPosition()
            squares.forEach { println(it.toList().toString()) }
            flingRight()
            println("+++++++++++++++++")
            squares.forEach { println(it.toList().toString()) }
        }
    }

}