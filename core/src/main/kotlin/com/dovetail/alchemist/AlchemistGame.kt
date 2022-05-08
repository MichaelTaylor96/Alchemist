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
import com.dovetail.alchemist.asset.AtlasAsset
import com.dovetail.alchemist.asset.TextureAsset
import com.dovetail.alchemist.ecs.systems.*
import com.dovetail.alchemist.screens.GameScreen
import com.dovetail.alchemist.screens.LoadingScreen
import com.dovetail.alchemist.screens.MixingScreen
import com.dovetail.alchemist.screens.PlayScreen
import ktx.app.KtxGame
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.logger

const val PPM = 50f
const val WORLD_WIDTH = 16 * PPM
const val WORLD_HEIGHT = 9 * PPM
private val Log = logger<AlchemistGame>()
class AlchemistGame : KtxGame<GameScreen>() {
    val batch : Batch by lazy { SpriteBatch() }
    val gameViewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    val uiViewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    val stage : Stage by lazy {
        val result = Stage(uiViewport, batch)
        Gdx.input.inputProcessor = result
        result
    }
    val assets : AssetStorage by lazy {
        KtxAsync.initiate()
        AssetStorage()
    }

    val engine:Engine by lazy { PooledEngine().apply {
        val graphicsAtlas = assets[AtlasAsset.GAME_GRAPHICS.descriptor]
        val background = assets[TextureAsset.BACKGROUND.descriptor]

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

        val assetRefs = gdxArrayOf(
            AtlasAsset.values().filter { it.isSkinAtlas }.map { assets.loadAsync(it.descriptor) }
            BitmapFontAsset.values().map { assets.loadAsync(it.descriptor) }
        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()
            createSkin(assets)
            addScreen(LoadingScreen(this))
            setScreen<LoadingScreen>()
        }
    }

    override fun dispose() {
        batch.dispose()
    }
}