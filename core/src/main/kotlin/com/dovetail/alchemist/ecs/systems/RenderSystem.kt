package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.dovetail.alchemist.ecs.components.GraphicComponent
import com.dovetail.alchemist.ecs.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class RenderSystem(
        private val batch : Batch,
        private val viewport: Viewport
) : SortedIteratingSystem(
        allOf(TransformComponent::class, GraphicComponent::class).get(),
        compareBy { entity -> entity[TransformComponent.mapper] }
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        val graphic = entity[GraphicComponent.mapper]
        require(transform!=null){"Entity |entity| must have a TransformComponent. entity=$entity"}
        require(graphic!=null){"Entity |entity| must have a GraphicComponent. entity=$entity"}

        graphic.sprite.run {
            rotation = transform.rotation
            setBounds(
                    transform.position.x,
                    transform.position.y,
                    transform.size.x,
                    transform.size.y
            )
            draw(batch)
        }
    }

    override fun update(deltaTime: Float) {
        forceSort()
        viewport.apply()
        batch.use(viewport.camera.combined) {
            super.update(deltaTime)
        }
    }
}