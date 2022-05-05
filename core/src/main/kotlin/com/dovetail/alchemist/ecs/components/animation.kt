package com.dovetail.alchemist.ecs.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor
import ktx.collections.GdxArray

private const val DEFAULT_FRAME_DURATION = 1/20f

enum class AnimationType(
        val atlasKey : String,
        val playMode : Animation.PlayMode = Animation.PlayMode.LOOP,
        val rate : Float = 1f
) {
    NONE(""),
    PLAYER_IDLE_RIGHT("player_idle_right", rate=0.5f),
    PLAYER_IDLE_LEFT("player_idle_left", rate=0.5f),
    PLAYER_IDLE_UP("player_idle_up", rate=0.5f),
    PLAYER_IDLE_DOWN("player_idle_down", rate=0.5f),
    PLAYER_WALK_RIGHT("walk_right", rate=0.5f),
    PLAYER_WALK_LEFT("walk_left", rate=0.5f),
    PLAYER_WALK_UP("walk_up", rate=0.5f),
    PLAYER_WALK_DOWN("walk_down", rate=0.5f),
}

class Animation2d(
        val type : AnimationType,
        keyFrames : GdxArray<TextureRegion>,
        playMode : PlayMode = PlayMode.LOOP,
        rate : Float = 1f
) : Animation<TextureRegion>(DEFAULT_FRAME_DURATION/rate, keyFrames, playMode) {

}

class AnimationComponent : Component, Poolable {
    var type = AnimationType.NONE
    var stateTime = 0f
    lateinit var animation: Animation2d

    override fun reset() {
        type = AnimationType.NONE
    }

    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}