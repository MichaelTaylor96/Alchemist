package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dovetail.alchemist.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerAnimationSystem(
        private val rightRegion : TextureRegion,
        private val leftRegion : TextureRegion,
        private val upRegion : TextureRegion,
        private val downRegion : TextureRegion,
) : IteratingSystem(allOf(PlayerComponent::class, FacingComponent::class, GraphicComponent::class).get()),
    EntityListener {
    private var lastDirection = FacingDirection.RIGHT

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) {
        entity[GraphicComponent.mapper]?.setSpriteRegion(rightRegion)
    }

    override fun entityRemoved(entity: Entity?) = Unit

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val graphic = entity[GraphicComponent.mapper]
        val facing = entity[FacingComponent.mapper]
        require(graphic!=null){"Entity |entity| must have a PlayerComponent. entity=$entity"}
        require(facing!=null){"Entity |entity| must have a FacingComponent. entity=$entity"}

        if(facing.direction == lastDirection && graphic.sprite.texture != null) {
            return
        }

        lastDirection = facing.direction
        val region = when(facing.direction) {
            FacingDirection.RIGHT -> rightRegion
            FacingDirection.LEFT -> leftRegion
            FacingDirection.DOWN -> downRegion
            FacingDirection.UP -> upRegion
        }
        graphic.setSpriteRegion(region)
    }
}