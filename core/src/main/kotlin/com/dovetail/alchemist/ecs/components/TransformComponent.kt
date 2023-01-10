package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class TransformComponent : Component, Poolable, Comparable<TransformComponent> {
    val position = Vector3()
    val prevPosition = Vector3()
    val interpolatedPosition = Vector3()
    val size = Vector2(1f, 1f)
    var rotation = 0f

    override fun reset() {
        position.setZero()
        prevPosition.setZero()
        interpolatedPosition.setZero()
        size.set(1f, 1f)
        rotation = 0f
    }

    override fun compareTo(other: TransformComponent): Int {
        val zDiff = other.position.z.compareTo(position.z)
        return(if(zDiff==0) other.position.y - position.y else zDiff).toInt()
    }

    fun setInitialPosition(x: Float, y: Float, z: Float) {
        position.set(x, y, z)
        prevPosition.set(x, y, z)
        interpolatedPosition.set(x, y, z)
    }

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}