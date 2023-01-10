package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dovetail.alchemist.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger

private val LOG = logger<PlayerAnimationSystem>()

class PlayerAnimationSystem(
        private val initialAnimation : AnimationType = AnimationType.PLAYER_IDLE_RIGHT
) : IteratingSystem(allOf(
        PlayerComponent::class,
        FacingComponent::class,
        GraphicComponent::class,
        AnimationComponent::class).get()
), EntityListener {
    private var lastDirection = FacingDirection.RIGHT
    private var lastMoving = false

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) = Unit

    override fun entityRemoved(entity: Entity?) = Unit

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val graphic = entity[GraphicComponent.mapper]
        val facing = entity[FacingComponent.mapper]
        val aniCmp = entity[AnimationComponent.mapper]
        val player = entity[PlayerComponent.mapper]
        require(graphic!=null){"Entity |entity| must have a PlayerComponent. entity=$entity"}
        require(facing!=null){"Entity |entity| must have a FacingComponent. entity=$entity"}
        require(aniCmp!=null){"Entity |entity| must have a AnimationComponent. entity=$entity"}
        require(player!=null){"Entity |entity| must have a PLayerComponent. entity=$entity"}

        if(
            facing.direction == lastDirection &&
            player.moving == lastMoving &&
            graphic.sprite.texture != null
        ) {
            return
        }

        lastMoving = player.moving
        lastDirection = facing.direction
        var animationType : AnimationType = if (player.movingX == Movement.NONE && player.movingY == Movement.NONE) {
            when(facing.direction) {
                FacingDirection.RIGHT -> AnimationType.PLAYER_IDLE_RIGHT
                FacingDirection.LEFT -> AnimationType.PLAYER_IDLE_LEFT
                FacingDirection.DOWN -> AnimationType.PLAYER_IDLE_DOWN
                FacingDirection.UP -> AnimationType.PLAYER_IDLE_UP
            }
        } else {
            when(facing.direction) {
                FacingDirection.RIGHT -> AnimationType.PLAYER_WALK_RIGHT
                FacingDirection.LEFT -> AnimationType.PLAYER_WALK_LEFT
                FacingDirection.DOWN -> AnimationType.PLAYER_WALK_DOWN
                FacingDirection.UP -> AnimationType.PLAYER_WALK_UP
            }
        }

        aniCmp.type = animationType
    }
}