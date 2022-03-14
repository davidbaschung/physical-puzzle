package com.mygdx.game.gameScreen.entities.Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import static com.mygdx.game.gameScreen.GameScreen.sceneLoader;
import static utils.$.print;


/**
 * class for pivotables processing.
 */

public class PivotableSystem extends AbstractSystem {

    private ComponentMapper pivotableComponentMapper;

    public PivotableSystem() { // méthode commune initialisant les pivotables, sans référence d'entité.
        super(Family.all(com.mygdx.game.gameScreen.entities.Components.Pivotable.class).get());
        pivotableComponentMapper = pivotableComponentMapper.getFor(com.mygdx.game.gameScreen.entities.Components.Pivotable.class);
        for (Entity entity : sceneLoader.engine.getEntities()) { // code to create rotation center
            MainItemComponent mainItemComponent = entity.getComponent(MainItemComponent.class);
            if (mainItemComponent.tags.contains("pivotableCenter")) {
                mainItemComponent.visible = false;
                TransformComponent transformComponent = entity.getComponent(TransformComponent.class);
                DimensionsComponent dimensionsComponent = entity.getComponent(DimensionsComponent.class);
                new ItemWrapper(sceneLoader.getRoot()).getChild("pivotable" + new String(mainItemComponent.itemIdentifier).replace("pivotableCenter", "")).getEntity().getComponent(com.mygdx.game.gameScreen.entities.Components.Pivotable.class).rotationCenter =
                        new Vector2(transformComponent.x+dimensionsComponent.width/2, transformComponent.y+dimensionsComponent.height/2);
            }
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        super.processEntity(entity, com.mygdx.game.gameScreen.entities.Components.Pivotable.class, deltaTime);
    }

    @Override
    public void initEntity(Entity entity, Class componentClass) {
        super.initEntity(entity, componentClass);
        PhysicsBodyComponent physicsBodyComponent = entity.getComponent(PhysicsBodyComponent.class);
        // inutile si touchDragged marche (avec setTransform);
//        Pivotable pivotable = entity.getComponent(Pivotable.class);

    }

    @Override
    public void beginContact(Contact contact, Entity entityA, Entity entityB) {
        print("CONTACT HAS BEGUN : "+contact.toString());
        // TODO flag contact pour pivotable.touchDragged
    }

    @Override
    public void endContact(Contact contact, Entity entityA, Entity entityB) {
        print("CONTACT ENDED : "+contact.toString());
    }
}
