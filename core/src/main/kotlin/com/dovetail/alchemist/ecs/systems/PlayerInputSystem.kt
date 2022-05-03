package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.MathUtils.PI
import com.badlogic.gdx.math.MathUtils.asin
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.Viewport
import com.dovetail.alchemist.ecs.components.FacingComponent
import com.dovetail.alchemist.ecs.components.FacingDirection
import com.dovetail.alchemist.ecs.components.PlayerComponent
import com.dovetail.alchemist.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

private val LOG = logger<PlayerInputSystem>()

class PlayerInputSystem(
    private val viewport: Viewport
) : IteratingSystem(allOf(PlayerComponent:: class, TransformComponent::class, FacingComponent::class).get()) {
    private val mouseVec = Vector2()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        val facing = entity[FacingComponent.mapper]
        require(transform!=null){"Entity |entity| must have a PlayerComponent. entity=$entity"}
        require(facing!=null){"Entity |entity| must have a FacingComponent. entity=$entity"}

        mouseVec.x = Gdx.input.x.toFloat()
        mouseVec.y = Gdx.input.y.toFloat()
        viewport.unproject(mouseVec)
        val xDiff = mouseVec.x - (transform.position.x + (transform.size.x/2))
        val yDiff = mouseVec.y - (transform.position.y + (transform.size.y/2))
        val distance = sqrt(xDiff.pow(2) + yDiff.pow(2))
        val angle = asin(yDiff/distance) * (180/ PI)
        facing.direction = when {
            yDiff > 0 && angle > 45 -> FacingDirection.UP
            yDiff < 0 && angle < -45 -> FacingDirection.DOWN
            xDiff > 0 && abs(angle) < 45 -> FacingDirection.RIGHT
            xDiff < 0 && abs(angle) < 45 -> FacingDirection.LEFT
            else -> FacingDirection.RIGHT
        }
    }
}