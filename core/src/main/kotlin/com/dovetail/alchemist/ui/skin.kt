package com.dovetail.alchemist.ui

import com.dovetail.alchemist.asset.AtlasAsset
import com.dovetail.alchemist.asset.BitmapFontAsset
import ktx.assets.async.AssetStorage
import ktx.scene2d.Scene2DSkin
import ktx.style.label
import ktx.style.skin

fun createSkin(assets: AssetStorage) {
    val atlas = assets[AtlasAsset.UI.descriptor]
    val whiteFont = assets[BitmapFontAsset.WHITE_FONT.descriptor]
    val blackFont = assets[BitmapFontAsset.BLACK_FONT.descriptor]
    Scene2DSkin.defaultSkin = skin(atlas) {skin ->
        label("default") {
            font = whiteFont
        }
        label("blackText") {
            font = blackFont
        }
    }
}