package com.mygdx.game.gameScreen.entities.Components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.gameScreen.SceneGraphics;
import com.mygdx.game.gameScreen.entities.Systems.AbstractSystem;
import com.mygdx.game.gameScreen.entities.Systems.PivotableSystem;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import utils.Mat;

import static utils.$.print;

/**
 * class for entities hat can be rotated around a particular point.
 */

public class Pivotable extends TouchableEntityComponent implements ContactListener{

    public Vector2 rotationCenter = null;
    private SceneGraphics.PositionByVelocity positionByVelocity;

    public Pivotable() {
    }

    @Override
    protected void setIteratingSystemClass() {
        iteratingSystemClass = PivotableSystem.class;
    }

    /**
 * note : contructor through : {@link AbstractSystem#initEntity(Entity, Class)}
 */

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, Entity entity) {
//        positionByVelocity = new SceneGraphics.PositionByVelocity(Family.all(Pivotable.class).get()); // note : any class with at least 1 element would be fine.
//        positionByVelocity.start(entity);
//        Vector2 coords = SceneGraphics.unproject(screenX, screenY);
//        positionByVelocity.setCoords(coords);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer, Entity entity) {
        Vector2 coords = SceneGraphics.unproject(screenX, screenY);
        PhysicsBodyComponent physicsBodyComponent = entity.getComponent(PhysicsBodyComponent.class);
        Vector2 toBody = new Vector2(physicsBodyComponent.body.getPosition()).scl(1/PhysicsBodyLoader.getScale()).sub(rotationCenter);
        Vector2 toCoords = new Vector2(coords).sub(rotationCenter);
        float rotationAngle = toBody.angle(toCoords);
        Vector2 newPosition = new Vector2(rotationCenter).add(new Vector2(toBody).rotate(rotationAngle));
//        if ( Math.abs(Mat.angle(physicsBodyComponent.body.getAngle(), (float) Math.toRadians(toCoords.angle()+90))) < 60f) { // saut évités par angle maximal
        if ( toCoords.len() > 1f && Math.abs(Mat.angle(physicsBodyComponent.body.getAngle(), (float) Math.toRadians(toCoords.angle()+90))) < 60f) {
            //if () // TODO TODO TODO
                SceneGraphics.setPositionByCenter(entity, newPosition, toCoords.angle() + 90);
        }
//        positionByVelocity.setCoords(coords);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, Entity entity) {
//        positionByVelocity.stop();
        return true;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
