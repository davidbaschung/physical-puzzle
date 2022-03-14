package com.mygdx.game.gameScreen.entities.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;

public interface ContactSystem {
    void beginContact(Contact contact, Entity entityA, Entity entityB);
    void endContact(Contact contact, Entity entityA, Entity entityB);
}
