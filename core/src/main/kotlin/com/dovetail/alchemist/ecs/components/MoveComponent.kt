package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class MoveComponent : Component, Poolable {
    val speed = Vector2()

    override fun reset() {
        speed.setZero()
    }

    companion object {
        val mapper = mapperFor<MoveComponent>()
    }
}