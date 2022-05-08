package com.dovetail.alchemist.screens

import com.dovetail.alchemist.AlchemistGame
import com.dovetail.alchemist.asset.AtlasAsset
import com.dovetail.alchemist.asset.TextureAsset
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf

class LoadingScreen(game : AlchemistGame) : GameScreen(game) {
    override fun show() {
        val assetRefs = gdxArrayOf(
            TextureAsset.values().map { assets.loadAsync(it.descriptor) },
            AtlasAsset.values().map { assets.loadAsync(it.descriptor) }
        ).flatten()

        KtxAsync.launch {
            assetRefs.joinAll()
            assetsLoaded()
        }

        // ...
        // setup UI
    }

    fun assetsLoaded() {
        game.addScreen(PlayScreen(game))
        game.addScreen(MixingScreen(game))
        game.setScreen<PlayScreen>()
        game.removeScreen<LoadingScreen>()
    }
}