package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.dovetail.alchemist.WORLD_HEIGHT
import com.dovetail.alchemist.WORLD_WIDTH
import com.dovetail.alchemist.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.max
import kotlin.math.min

private const val UPDATE_RATE = 1/25f

class MoveSystem : IteratingSystem(allOf(
        MoveComponent::class,
        TransformComponent::class
    ).exclude(
        RemoveComponent::class.java
    ).get()
) {
    private var accumulator = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while(accumulator >= UPDATE_RATE) {
            accumulator -= UPDATE_RATE

            for (entity in entities) {
                entity[TransformComponent.mapper]?.let {transform ->
                    transform.prevPosition.set(transform.position)
                }
            }

            super.update(UPDATE_RATE)
        }

        val alpha = accumulator / UPDATE_RATE
        for (entity in entities) {
            entity[TransformComponent.mapper]?.let {transform ->
                transform.interpolatedPosition.set(
                    MathUtils.lerp(transform.prevPosition.x, transform.position.x, alpha),
                    MathUtils.lerp(transform.prevPosition.y, transform.position.y, alpha),
                    transform.position.z
                )
            }
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        val move = entity[MoveComponent.mapper]
        require(transform!=null){"Entity |entity| must have a TransformComponent. entity=$entity"}
        require(move!=null){"Entity |entity| must have a MoveComponent. entity=$entity"}

        val player = entity[PlayerComponent.mapper]
        if(player != null) {
            movePlayer(transform, move, player, deltaTime)
        } else {
            moveEntity(transform, move, deltaTime)
        }
    }

    private fun movePlayer(
            transform: TransformComponent,
            move: MoveComponent,
            player: PlayerComponent,
            deltaTime: Float
    ) {
        move.speed.x = when (player.movingX) {
            Movement.POS -> MathUtils.clamp(move.speed.x + (PLAYER_ACCELERATION * deltaTime), 0f, PLAYER_MAX_SPEED)
            Movement.NEG -> MathUtils.clamp(move.speed.x - (PLAYER_ACCELERATION * deltaTime), -PLAYER_MAX_SPEED, 0f)
            else -> 0f
        }
        move.speed.y = when (player.movingY) {
            Movement.POS -> MathUtils.clamp(move.speed.y + (PLAYER_ACCELERATION * deltaTime), 0f, PLAYER_MAX_SPEED)
            Movement.NEG -> MathUtils.clamp(move.speed.y - (PLAYER_ACCELERATION * deltaTime), -PLAYER_MAX_SPEED, 0f)
            else -> 0f
        }

        moveEntity(transform, move, deltaTime)
    }

    private fun moveEntity(transform: TransformComponent, move: MoveComponent, deltaTime: Float) {
        transform.position.x = MathUtils.clamp(
                transform.position.x + (move.speed.x*deltaTime),
                0f,
                WORLD_WIDTH-transform.size.x
        )
        transform.position.y = MathUtils.clamp(
                transform.position.y + (move.speed.y*deltaTime),
                0f,
                WORLD_HEIGHT-transform.size.y
        )
    }
}