package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class FacingComponent : Component, Poolable {
    var direction = FacingDirection.RIGHT

    override fun reset() {
        direction = FacingDirection.RIGHT
    }

    companion object {
        val mapper = mapperFor<FacingComponent>()
    }
}

enum class FacingDirection {
    LEFT, RIGHT, UP, DOWN
}