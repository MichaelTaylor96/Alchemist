package com.dovetail.alchemist.event

import com.badlogic.ashley.core.Entity
import ktx.collections.GdxSet
import java.util.EnumMap

enum class GameEventType {
    COLLECT_INGREDIENT
}

interface GameEvent

object GameEventCollectIngredient : GameEvent {
    lateinit var player: Entity
    lateinit var ingredient: Entity
}

interface GameEventListener {
    fun onEvent(type:GameEventType, data:GameEvent? = null)
}

class GameEventManager {
    private val listeners = EnumMap<GameEventType, GdxSet<GameEventListener>>(GameEventType::class.java)

    fun addListener(type: GameEventType, listener: GameEventListener) {
        var eventListeners = listeners[type]
        if (eventListeners == null) {
            eventListeners = GdxSet()
            listeners[type] = eventListeners
        }
        eventListeners.add(listener)
    }

    fun removeListener(type: GameEventType, listener: GameEventListener) {
        var eventListeners = listeners[type]
        eventListeners?.remove(listener)
    }

    fun removeListener(listener: GameEventListener) {
        listeners.values.forEach { it.remove(listener) }
    }

    fun dispatch(type: GameEventType, data: GameEvent? = null) {
        listeners[type]?.forEach { it.onEvent(type, data) }
    }
}