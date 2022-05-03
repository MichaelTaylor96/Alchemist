package com.dovetail.alchemist

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dovetail.alchemist.ecs.systems.PlayerAnimationSystem
import com.dovetail.alchemist.ecs.systems.PlayerInputSystem
import com.dovetail.alchemist.ecs.systems.RenderSystem
import com.dovetail.alchemist.screens.GameScreen
import com.dovetail.alchemist.screens.MixingScreen
import com.dovetail.alchemist.screens.PlayScreen
import ktx.app.KtxGame
import ktx.log.logger

const val PPM = 50f
private val Log = logger<AlchemistGame>()
class AlchemistGame : KtxGame<GameScreen>() {
    val batch : Batch by lazy { SpriteBatch() }
    val gameViewport = FitViewport(16f * PPM, 9f * PPM)

    val engine:Engine by lazy { PooledEngine().apply {
        addSystem(PlayerInputSystem(gameViewport))
        addSystem(PlayerAnimationSystem(
            TextureRegion(Texture("graphics/player_right.png")),
            TextureRegion(Texture("graphics/player_left.png")),
            TextureRegion(Texture("graphics/player_up.png")),
            TextureRegion(Texture("graphics/player_down.png"))
        ))
        addSystem(RenderSystem(batch, gameViewport))
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