package com.dovetail.alchemist.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.dovetail.alchemist.AlchemistGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage

abstract class GameScreen(
    val game : AlchemistGame,
    val gameViewport : Viewport = game.gameViewport,
    val uiViewport: Viewport = game.uiViewport,
    val assets : AssetStorage = game.assets,
    val stage : Stage = game.stage
) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }
}