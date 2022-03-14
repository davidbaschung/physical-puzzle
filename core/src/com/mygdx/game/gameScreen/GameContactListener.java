package com.mygdx.game.gameScreen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.gameScreen.entities.Components.ActiveEntityComponent;
import com.mygdx.game.gameScreen.entities.Systems.AbstractSystem;
import com.mygdx.game.gameScreen.entities.Tags;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.HashMap;

import static com.mygdx.game.gameScreen.GameScreen.sceneLoader;
import static utils.$.print;

public class GameContactListener implements ContactListener{

    private static HashMap<Contact, Entity[]> contactsList = new HashMap<Contact, Entity[]>();

    @Override
    public void beginContact(Contact contact) {
        ItemWrapper root = new ItemWrapper(sceneLoader.getRoot());
        String ID1 = (String) contact.getFixtureA().getBody().getUserData(),
            ID2 = (String) contact.getFixtureB().getBody().getUserData();
        ActiveEntityComponent activeEntityComponent1 = root.getChild(ID1).getEntity().getComponent(ActiveEntityComponent.class),
            activeEntityComponent2 = root.getChild(ID1).getEntity().getComponent(ActiveEntityComponent.class);
        // TODO parcourir les components pour déterminer s'il contiennent une extension d'activeEntityComponent
        print(ID1+" , "+ID2);
        print(activeEntityComponent1+ " , "+ activeEntityComponent2);
        Entity[] value = new Entity[2];
        if (activeEntityComponent1 != null) {
            value[0] = root.getChild(ID1).getEntity();
            ((AbstractSystem) sceneLoader.getEngine().getSystem(activeEntityComponent1.iteratingSystemClass)). beginContact(contact, root.getChild(ID1).getEntity(), root.getChild(ID2).getEntity());
        }
        if (activeEntityComponent2 != null) {
            value[1] = root.getChild(ID2).getEntity();
            ((AbstractSystem) sceneLoader.getEngine().getSystem(activeEntityComponent2.iteratingSystemClass)).beginContact(contact, root.getChild(ID2).getEntity(), root.getChild(ID1).getEntity());
        }
        contactsList.put(contact, value);
        print("GAMECL");
//        try { Method method = activeEntityComponent1.iteratingSystemClass.getMethod("beginContact", Contact.class, Entity.class, Entity.class);
//            method.invoke( null, contact, value[0], value[1]);
//            print("REFLECTION");
//        } catch (Exception e) {e.printStackTrace();}
        //TODO exception with proguard here?
    }

    @Override
    public void endContact(Contact contact) {
        contactsList.put(contact, null);
    } // TODO

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
