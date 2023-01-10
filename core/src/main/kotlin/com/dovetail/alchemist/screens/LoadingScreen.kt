package com.dovetail.alchemist.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.dovetail.alchemist.AlchemistGame
import com.dovetail.alchemist.asset.AtlasAsset
import com.dovetail.alchemist.asset.TextureAsset
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.plusAssign
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.scene2d.*

class LoadingScreen(game : AlchemistGame) : GameScreen(game) {
    private lateinit var progressBar: Image
    private lateinit var clickToStartLabel: Label

    override fun show() {
        val assetRefs = gdxArrayOf(
            TextureAsset.values().map { assets.loadAsync(it.descriptor) },
            AtlasAsset.values().map { assets.loadAsync(it.descriptor) }
        ).flatten()

        KtxAsync.launch {
            assetRefs.joinAll()
            assetsLoaded()
        }

        setupUI()
    }

    override fun hide() {
        stage.clear()
    }

    fun setupUI() {
        stage.actors {
            table {
                defaults().fillX().expandX()

                label("Loading Screen", "default") {
                    wrap = true
                    setAlignment(Align.center)
                }
                row()

                clickToStartLabel = label("click to start", "default") {
                    wrap = true
                    setAlignment(Align.center)
                    color.a = 0f
                }
                row()

                stack { cell ->
                    progressBar = image("blue_bar").apply {
                        scaleX = 0f
                    }
                    label("Loading...", "default") {
                        setAlignment(Align.center)
                    }
                    cell.padLeft(5f).padRight(5f)
                    cell.maxWidth(200f)
                }

                setFillParent(true)
                pack()
            }
        }
    }

    override fun render(delta: Float) {
        if (assets.progress.isFinished &&
            Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) &&
            game.containsScreen<PlayScreen>()
        ) {
            game.setScreen<PlayScreen>()
            game.removeScreen<LoadingScreen>()
        }

        progressBar.scaleX = assets.progress.percent
        stage.run {
            viewport.apply()
            act()
            draw()
        }
    }

    fun assetsLoaded() {
        game.addScreen(PlayScreen(game))
        game.addScreen(MixingScreen(game))
        clickToStartLabel += Actions.forever(Actions.sequence(
            Actions.fadeIn(0.5f),
            Actions.fadeOut(0.25f)
        ))
    }
}