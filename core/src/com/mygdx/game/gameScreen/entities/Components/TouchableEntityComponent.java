package com.mygdx.game.gameScreen.entities.Components;

import com.badlogic.ashley.core.Entity;

/**
 * mother class for processed entities that can be user-activated.
 *
 */

public abstract class TouchableEntityComponent extends ActiveEntityComponent {

    public boolean touchDown(int screenX, int screenY, int pointer, Entity entity) { return false; }

    public boolean touchDragged(int screenX, int screenY, int pointer, Entity entity) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, Entity entity) {
        return false;
    }
}
