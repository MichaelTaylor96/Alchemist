package com.dovetail.alchemist.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.dovetail.alchemist.AlchemistGame
import ktx.app.KtxScreen

abstract class GameScreen(
        val game: AlchemistGame,
        val batch: Batch = game.batch,
        val engine: Engine = game.engine
    ) : KtxScreen