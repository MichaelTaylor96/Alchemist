package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Rectangle
import com.dovetail.alchemist.ecs.components.IngredientComponent
import com.dovetail.alchemist.ecs.components.PlayerComponent
import com.dovetail.alchemist.ecs.components.RemoveComponent
import com.dovetail.alchemist.ecs.components.TransformComponent
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger

private val LOG = logger<CollectIngredientSystem>()

class CollectIngredientSystem : IteratingSystem(allOf(IngredientComponent::class, TransformComponent::class).get()) {
    private val playerRect = Rectangle()
    private val ingredientRect = Rectangle()
    private val playerEntities by lazy {
        engine.getEntitiesFor(allOf(PlayerComponent::class).get())
    }

    private fun collectIngredient(playerEntity: Entity, ingredientEntity: Entity) {
        val player = playerEntity[PlayerComponent.mapper]
        val ingredient = ingredientEntity[IngredientComponent.mapper]
        require(player!=null) { "Entity |entity| must have a PlayerComponent. entity=$playerEntity" }
        require(ingredient!=null) { "Entity |entity| must have a IngredientComponent. entity=$ingredientEntity" }

        player.inventory.ingredients.add(ingredient.type)
        LOG.debug { "Acquired: ${ingredient.type}. Inventory: ${player.inventory.ingredients}" }
        ingredientEntity.addComponent<RemoveComponent>(engine)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform!=null) { "Entity |entity| must have a TransformComponent. entity=$entity" }

        ingredientRect.set(
            transform.position.x,
            transform.position.y,
            transform.size.x,
            transform.size.y
        )
        playerEntities.forEach { player ->
            player[TransformComponent.mapper]?.let { playerTransform ->
                playerRect.set(
                    playerTransform.position.x,
                    playerTransform.position.y,
                    playerTransform.size.x,
                    playerTransform.size.y
                )
                if(playerRect.overlaps(ingredientRect) && Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    collectIngredient(player, entity)
                }
            }
        }
    }
}