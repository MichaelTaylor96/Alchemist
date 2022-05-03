package com.dovetail.alchemist.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.dovetail.alchemist.AlchemistGame
import ktx.app.KtxScreen

abstract class GameScreen(
    val game: AlchemistGame,
    val viewport: Viewport = game.gameViewport,
    val engine: Engine = game.engine
) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }
}