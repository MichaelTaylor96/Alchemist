package com.dovetail.alchemist.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas

enum class TextureAsset(
    fileName: String,
    directory: String = "graphics",
    val descriptor: AssetDescriptor<Texture> = AssetDescriptor("$directory/$fileName", Texture::class.java)
) {
    BACKGROUND("grass.png")
}

enum class AtlasAsset(
    fileName: String,
    val isSkinAtlas: Boolean,
    directory: String = "graphics",
    val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor(
        "$directory/$fileName",
        TextureAtlas::class.java
    )
) {
    GAME_GRAPHICS("graphics.atlas", false),
    UI("ui.atlas", true, "ui")
}

enum class BitmapFontAsset(
    fileName: String,
    directory: String = "ui",
    val descriptor: AssetDescriptor<BitmapFont> = AssetDescriptor(
        "$directory/$fileName",
        BitmapFont::class.java
    )
) { }