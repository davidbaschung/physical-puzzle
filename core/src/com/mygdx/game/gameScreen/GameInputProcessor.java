package com.mygdx.game.gameScreen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Settings;
import com.mygdx.game.gameScreen.entities.Tags;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.mygdx.game.gameScreen.entities.Components.TouchableEntityComponent;

import java.util.HashMap;

import static utils.$.print;

/**
 * Created byb David on 18.05.2017.
 *
 * Game main class!
 */

//TODO remplacer par un Gesture Detector
//TODO déterminer l'objet touché dans touchDown, puis placer un listener de polling (sur l'événement en question) dans l'IteratingSystem de l'objet touché (="custom"Component). A REVOIR.
//TODO créer un IteratingSystem entreprenant des actions selon l'événement stocké dans "custom"Component.
//TODO --> event - processor : interaction with elmt - add elmt to listener list (gérer liste selon existence event) - subprocess touching with methods of the elmt

public class GameInputProcessor extends InputAdapter {

    public static HashMap<String, Class> touchables;
    private static final Entity[] listeners = new Entity[Settings.maxFingers];
    private static final Class[] listenersTouchableEntityClass = new Class[Settings.maxFingers];

    public GameInputProcessor(Tags tags) {
        touchables = tags.touchables;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { // method to check interactions with touchable entities
//        print("entities : "+GameScreen.sceneLoader.engine.getEntities().toString()); // test entities
        for (Entity entity : GameScreen.sceneLoader.engine.getEntities()) {
            //TODO
            MainItemComponent mainItemComponent = entity.getComponent(MainItemComponent.class);
            for (String str : ((String[]) touchables.keySet().toArray(new String[touchables.keySet().size()]))) {
                if (mainItemComponent.tags.contains(str)) {
                    TransformComponent transformComponent = entity.getComponent(TransformComponent.class);
                    DimensionsComponent dimensionsComponent = entity.getComponent(DimensionsComponent.class);
                    Vector3 p = SceneGraphics.getViewport().getCamera().unproject(new Vector3(screenX, screenY,0));
                    Vector2 e = new Vector2(transformComponent.x+dimensionsComponent.width/2, transformComponent.y+dimensionsComponent.height/2);
                    Vector2 e_to_p = new Vector2(p.x-e.x, p.y-e.y);
                    Vector2 e_to_p_ortho = new Vector2(e_to_p).setAngle(e_to_p.angle()-transformComponent.rotation);
                    if (e.x+e_to_p_ortho.x>transformComponent.x && e.x+e_to_p_ortho.x<transformComponent.x+dimensionsComponent.width &&
                            e.y+e_to_p_ortho.y>transformComponent.y && e.y+e_to_p_ortho.y<transformComponent.y+dimensionsComponent.height) {

                        //TODO z-index
                        // code to activate certain behaviour
                        try {
                            listeners[pointer] = entity;
                            listenersTouchableEntityClass[pointer] = (touchables.get(str));
                            ((TouchableEntityComponent) entity.getComponent(touchables.get(str))).touchDown(screenX, screenY, pointer, entity);
                        } catch (ArrayIndexOutOfBoundsException exception) {
                            print("TOO MUCH FINGERS DOWN");
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        try {
            Entity entity = listeners[pointer];
            ((TouchableEntityComponent) entity.getComponent(listenersTouchableEntityClass[pointer])).touchDragged(screenX, screenY, pointer, entity);
        } catch (ArrayIndexOutOfBoundsException exception) {
        } catch (NullPointerException exception) {}
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        try {
            Entity entity = listeners[pointer];
            ((TouchableEntityComponent) entity.getComponent((Class) listenersTouchableEntityClass[pointer])).touchUp(screenX, screenY, pointer, entity);
            listeners[pointer] = null;
        } catch (ArrayIndexOutOfBoundsException exception) {
        } catch (NullPointerException exception) {}
        return true;
    }

}
