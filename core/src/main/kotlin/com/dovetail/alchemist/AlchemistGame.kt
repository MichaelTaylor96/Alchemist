package com.dovetail.alchemist

import com.badlogic.gdx.Game
import ktx.app.KtxGame
import ktx.app.KtxScreen

class AlchemistGame : KtxGame<KtxScreen>() {
    override fun create() {
        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}