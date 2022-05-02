package com.dovetail.alchemist.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dovetail.alchemist.AlchemistGame
import com.dovetail.alchemist.PPM
import com.dovetail.alchemist.ecs.components.GraphicComponent
import com.dovetail.alchemist.ecs.components.TransformComponent
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.scene2d.*
import ktx.graphics.use
import ktx.log.logger

private val LOG = logger<PlayScreen>()

class PlayScreen(game: AlchemistGame) : GameScreen(game) {
    private val background = Texture("graphics/grass_with_flowers.png")
    private val bgRegion = TextureRegion()
    private val viewport = FitViewport(16f * PPM, 9f * PPM)
    private val player = game.engine.entity {
        with<TransformComponent> {
            position.set(1f,1f,0f)
        }
        with<GraphicComponent> {
            sprite.texture = Texture("graphics/character.png")
            sprite.setRegion(sprite.texture)
            sprite.setSize(sprite.texture.width.toFloat(), sprite.texture.height.toFloat())
            sprite.setOriginCenter()
        }
    }

    init {
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
        bgRegion.texture = background
        bgRegion.setRegion(0f, 0f, 258*PPM, 258*PPM)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun show() {
        LOG.debug { "Showing play screen" }
    }

    override fun render(delta: Float) {
        engine.update(delta)

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen<MixingScreen>()
        }

        viewport.apply()
        batch.use(viewport.camera.combined) {
            batch.draw(bgRegion, 0f,0f)
            player[GraphicComponent.mapper]?.sprite?.draw(it)
        }
    }

    override fun dispose() {
    }
}