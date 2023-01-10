package com.dovetail.alchemist.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dovetail.alchemist.AlchemistGame
import com.dovetail.alchemist.PPM
import com.dovetail.alchemist.WORLD_HEIGHT
import com.dovetail.alchemist.WORLD_WIDTH
import com.dovetail.alchemist.asset.AtlasAsset
import com.dovetail.alchemist.ecs.components.*
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.scene2d.*
import ktx.graphics.use
import ktx.log.logger
import kotlin.math.min
import kotlin.random.Random

private val LOG = logger<PlayScreen>()
private const val MAX_DELTA_TIME = 1/20f
class PlayScreen(game: AlchemistGame) : GameScreen(game) {
    val engine = game.engine

    init {
        val player = game.engine.entity {
            with<TransformComponent> {
                setInitialPosition(100f,100f,0f)
                size.set(16f, 28f)
            }
            with<MoveComponent>()
            with<GraphicComponent>()
            with<PlayerComponent>()
            with<FacingComponent>()
            with<AnimationComponent>{type = AnimationType.PLAYER_IDLE_RIGHT}
        }

        val widthInt = WORLD_WIDTH.toInt()
        val heightInt = WORLD_HEIGHT.toInt()
        repeat(20) {
            val atlas = game.assets[AtlasAsset.GAME_GRAPHICS.descriptor]
            val ingredientType = IngredientType.values()[Random.nextInt(1,5)]
            val region = atlas.findRegion(ingredientType.atlasKey)
            val x = Random.nextInt(widthInt).toFloat()
            val y = Random.nextInt(heightInt).toFloat()
            val ingredient = game.engine.entity {
                with<TransformComponent> {
                    setInitialPosition(x, y, 0f)
                    size.set(10f, 10f)
                }
                with<IngredientComponent> {
                    type = ingredientType
                }
                with<GraphicComponent> {
                    setSpriteRegion(region)
                }
            }
        }
    }

    override fun show() {
        LOG.debug { "Showing play screen" }
    }

    override fun render(delta: Float) {
        engine.update(min(MAX_DELTA_TIME, delta))

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen<MixingScreen>()
        }
    }

    override fun dispose() {
    }
}