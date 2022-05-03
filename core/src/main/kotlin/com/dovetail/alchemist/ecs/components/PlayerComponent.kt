package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class PlayerComponent : Component, Poolable {
    override fun reset() {
        TODO("Not yet implemented")
    }

    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }
}