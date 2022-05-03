package com.dovetail.alchemist.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dovetail.alchemist.AlchemistGame
import com.dovetail.alchemist.PPM
import com.dovetail.alchemist.ecs.components.FacingComponent
import com.dovetail.alchemist.ecs.components.GraphicComponent
import com.dovetail.alchemist.ecs.components.PlayerComponent
import com.dovetail.alchemist.ecs.components.TransformComponent
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.scene2d.*
import ktx.graphics.use
import ktx.log.logger

private val LOG = logger<PlayScreen>()

class PlayScreen(game: AlchemistGame) : GameScreen(game) {
    private val player = game.engine.entity {
        with<TransformComponent> {
            position.set(100f,100f,0f)
            size.set(32f, 56f)
        }
        with<GraphicComponent>()
        with<PlayerComponent>()
        with<FacingComponent>()
    }

    override fun show() {
        LOG.debug { "Showing play screen" }
    }

    override fun render(delta: Float) {
        engine.update(delta)

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen<MixingScreen>()
        }
    }

    override fun dispose() {
    }
}