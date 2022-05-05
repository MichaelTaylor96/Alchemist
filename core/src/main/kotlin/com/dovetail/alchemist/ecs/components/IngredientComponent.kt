package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

enum class IngredientType(
    val atlasKey: String
) {
    NONE(""),
    BLUE_FLOWER("blue_flower"),
    WHITE_FLOWER("white_flower"),
    YELLOW_FLOWER("yellow_flower"),
    RED_FLOWER("red_flower")
}

class IngredientComponent : Component, Poolable {
    var type = IngredientType.NONE

    override fun reset() {
        type = IngredientType.NONE
    }

    companion object {
        val mapper = mapperFor<IngredientComponent>()
    }
}