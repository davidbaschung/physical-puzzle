package com.mygdx.game.gameScreen.entities.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * mother class for entities that are processed continuously
 */

public abstract class ActiveEntityComponent implements Component{
    public Class iteratingSystemClass;
    public Entity parent;
    
    public ActiveEntityComponent() {
        setIteratingSystemClass();
    }

    protected abstract void setIteratingSystemClass();
}
