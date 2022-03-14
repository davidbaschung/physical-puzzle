package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameScreen.entities.Tags;
import com.mygdx.game.gameScreen.GameScreen;

/**
 * main class for the game
 */

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		setScreen(new GameScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
