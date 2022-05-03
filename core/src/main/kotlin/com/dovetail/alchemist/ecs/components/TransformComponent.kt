package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class TransformComponent : Component, Poolable, Comparable<TransformComponent> {
    val position = Vector3()
    val size = Vector2(1f, 1f)
    var rotation = 0f

    override fun reset() {
        position.set(Vector3.Zero)
        size.set(1f, 1f)
        rotation = 0f
    }

    override fun compareTo(other: TransformComponent): Int {
        val zDiff = position.z - other.position.z
        return(if(zDiff==0f) position.y - other.position.y else zDiff).toInt()
    }

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}