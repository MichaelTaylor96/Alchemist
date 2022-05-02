package com.dovetail.alchemist.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.dovetail.alchemist.AlchemistGame
import ktx.log.logger

private val LOG = logger<MixingScreen>()

class MixingScreen(game: AlchemistGame) : GameScreen(game) {
    override fun show() {
        LOG.debug { "Showing mixing screen" }
    }

    override fun render(delta: Float) {
        batch.begin()
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen<PlayScreen>()
        }

        batch.end()
    }
}