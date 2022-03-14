package com.mygdx.game.gameScreen.entities.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.gameScreen.SceneGraphics;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.mygdx.game.gameScreen.entities.Components.ActiveEntityComponent;

import static utils.$.print;

/**
 * mother class for all processing systemsClasses
 */

public abstract class AbstractSystem extends IteratingSystem implements ContactSystem {

    public AbstractSystem(Family family) {
        super(family);
    }

    public void processEntity(Entity entity, Class componentClass, float deltaTime) {
        if (((ActiveEntityComponent)entity.getComponent(componentClass)).parent == null) {
            initEntity(entity, componentClass);
        }
    }

    public void initEntity(Entity entity, Class componentClass) { // méthode référençant l'entité parente dans chaque touchable.
        ActiveEntityComponent activeEntityComponent = ((ActiveEntityComponent) entity.getComponent(componentClass));
        print("ROTATION CENTER : "+((com.mygdx.game.gameScreen.entities.Components.Pivotable) activeEntityComponent).rotationCenter);
        TransformComponent transformComponent = entity.getComponent(TransformComponent.class);
        DimensionsComponent dimensionsComponent = entity.getComponent(DimensionsComponent.class);
        activeEntityComponent.parent = entity;
        Vector2 position = new Vector2(transformComponent.x, transformComponent.y)
                .add(new Vector2(dimensionsComponent.width, dimensionsComponent.height).rotate(transformComponent.rotation)
                        .scl(0.5f));
        SceneGraphics.setPositionByCenter(entity, position);
    }
    public abstract void beginContact(Contact contact, Entity entityA, Entity entityB);
    public abstract void endContact(Contact contact, Entity entityA, Entity entityB);

}
