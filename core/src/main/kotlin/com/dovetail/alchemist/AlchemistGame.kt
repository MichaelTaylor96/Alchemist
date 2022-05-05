package com.dovetail.alchemist

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dovetail.alchemist.ecs.systems.*
import com.dovetail.alchemist.screens.GameScreen
import com.dovetail.alchemist.screens.MixingScreen
import com.dovetail.alchemist.screens.PlayScreen
import ktx.app.KtxGame
import ktx.log.logger

const val PPM = 50f
const val WORLD_WIDTH = 16 * PPM
const val WORLD_HEIGHT = 9 * PPM
private val Log = logger<AlchemistGame>()
class AlchemistGame : KtxGame<GameScreen>() {
    val batch : Batch by lazy { SpriteBatch() }
    val graphicsAtlas by lazy { TextureAtlas("graphics/graphics.atlas") }
    val background by lazy { Texture("graphics/grass.png") }
    val gameViewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)

    val engine:Engine by lazy { PooledEngine().apply {
        addSystem(PlayerInputSystem(gameViewport))
        addSystem(CollectIngredientSystem())
        addSystem(MoveSystem())
        addSystem(PlayerAnimationSystem())
        addSystem(AnimationSystem(graphicsAtlas))
        addSystem(RenderSystem(batch, gameViewport, background))
        addSystem(RemoveSystem())
    } }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        Log.debug { "Created game instance" }
        addScreen(PlayScreen(this))
        addScreen(MixingScreen(this))
        setScreen<PlayScreen>()
    }

    override fun dispose() {
        batch.dispose()
    }
}