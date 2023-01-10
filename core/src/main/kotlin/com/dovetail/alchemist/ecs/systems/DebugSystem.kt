package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import com.dovetail.alchemist.ecs.components.PlayerComponent
import ktx.ashley.allOf

private const val WINDOW_INFO_UPDATE_RATE = 0.25f

class DebugSystem : IntervalIteratingSystem(allOf(PlayerComponent::class).get(), WINDOW_INFO_UPDATE_RATE) {
    init {
        setProcessing(false)
    }

    override fun processEntity(entity: Entity?) {
        TODO("Not yet implemented")
    }
}