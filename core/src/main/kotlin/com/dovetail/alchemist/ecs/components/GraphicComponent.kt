package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor


class GraphicComponent : Component, Poolable {
    val sprite = Sprite()

    override fun reset() {
        sprite.texture = null
        sprite.setColor(1f,1f,1f,1f)
    }

    companion object {
        val mapper = mapperFor<GraphicComponent>()
    }
}