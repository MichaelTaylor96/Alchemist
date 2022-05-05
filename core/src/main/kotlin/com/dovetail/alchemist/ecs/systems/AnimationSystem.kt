package com.dovetail.alchemist.ecs.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dovetail.alchemist.ecs.components.*
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.collections.GdxArray
import ktx.log.logger
import java.util.EnumMap

private val LOG = logger<AnimationSystem>()

class AnimationSystem(
        private val atlas : TextureAtlas
) : IteratingSystem(allOf(AnimationComponent::class, GraphicComponent::class).get()), EntityListener {
    private val animationCache = EnumMap<AnimationType, Animation2d>(AnimationType::class.java)

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val aniCmp = entity[AnimationComponent.mapper]
        val graphic = entity[GraphicComponent.mapper]
        require(aniCmp!=null){"Entity |entity| must have a AnimationComponent. entity=$entity"}
        require(graphic!=null){"Entity |entity| must have a GraphicComponent. entity=$entity"}

        if(aniCmp.type==AnimationType.NONE) {
            LOG.error { "Animation loaded with none type" }
            return
        }

        if(aniCmp.type == aniCmp.animation.type) {
            aniCmp.stateTime += deltaTime
        } else {
            aniCmp.stateTime = 0f
            aniCmp.animation = getAnimation(aniCmp.type)
        }

        val frame = aniCmp.animation.getKeyFrame(aniCmp.stateTime)
        graphic.setSpriteRegion(frame)
    }

    override fun entityAdded(entity: Entity) {
        entity[AnimationComponent.mapper]?.let { aniCmp ->
            aniCmp.animation = getAnimation(aniCmp.type)
            val frame = aniCmp.animation.getKeyFrame(aniCmp.stateTime)
            entity[GraphicComponent.mapper]?.setSpriteRegion(frame)
        }
    }

    private fun getAnimation(type: AnimationType): Animation2d {
        var animation = animationCache[type]
        if(animation==null) {
            var regions = GdxArray<TextureRegion>(atlas.findRegions(type.atlasKey))
            if(regions.isEmpty) {
                LOG.error { "No regions found for ${type.atlasKey}" }
            }
            animation = Animation2d(type, regions, type.playMode, type.rate)
            animationCache[type] = animation
        }
        return(animation)
    }

    override fun entityRemoved(entity: Entity) = Unit
}