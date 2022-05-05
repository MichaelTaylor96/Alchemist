package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import com.dovetail.alchemist.PPM
import ktx.ashley.mapperFor
import ktx.collections.GdxArray

const val PLAYER_ACCELERATION = 11f * PPM
const val PLAYER_MAX_SPEED = 2.5f * PPM

class PlayerComponent : Component, Poolable {
    var movingX = Movement.NONE
    var movingY = Movement.NONE
    val moving : Boolean
        get() {
            return(movingX!=Movement.NONE || movingY!=Movement.NONE)
        }
    val inventory = Inventory

    override fun reset() {
        movingX = Movement.NONE
        movingY = Movement.NONE
        inventory.ingredients.clear()
        inventory.vials.clear()
        inventory.equipment.clear()
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}

object Inventory {
    val ingredients = GdxArray<IngredientType>()
    val equipment = GdxArray<String>()
    val vials = GdxArray<String>()
}

enum class Movement {
    POS, NEG, NONE
}