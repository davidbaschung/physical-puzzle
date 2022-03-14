package com.mygdx.game.gameScreen;

import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.gameScreen.entities.Tags;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.Map;

import static com.mygdx.game.gameScreen.GameScreen.sceneLoader;

/**
 * Created by David on 01.06.2017.
 *
 * classe for general scene staging.
 */
public class SceneManager {
    private final Nature nature;
    private final Tags tags;

    public SceneManager(Tags tags) {
        this.tags = tags;
        nature = new Nature();

        SceneGraphics.applyGlobalRegionByTagName("ground", "default_background"/*nature.ground*/);
        SceneGraphics.applyRegion("primitive1", "small8", true);
        SceneGraphics.applyRegion("square", "small5", false);
        SceneGraphics.applyAnimation("square", "Anim");
        SceneGraphics.applyAnimation("square2", "Anim2");
        SceneGraphics.applyAnimation("square2", "Anim");
//            new $.PrintPerSecond(Boolean.toString(new ItemWrapper(sceneLoader.getRoot()).getChild("ball").getEntity().getComponent(PhysicsBodyComponent.class).allowSleep));
            new ItemWrapper(sceneLoader.getRoot()).getChild("ball").getEntity().getComponent(PhysicsBodyComponent.class).allowSleep = false;
            //TODO - add Iterating Systems + dispose content
        addComponents();
        addIteratingSystems();

        Gdx.input.setInputProcessor(new GameInputProcessor(tags));

    }

    private void addComponents() { // method to add components to engine's entities. Has
        for (Map.Entry<String, Class> entry : tags.touchables.entrySet()) {
            sceneLoader.addComponentsByTagName(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Class> entry : tags.untouchables.entrySet()) {
            sceneLoader.addComponentsByTagName(entry.getKey(), entry.getValue());
        }
    }

    private void addIteratingSystems() {
        for (int i = 0; i<tags.systemsClasses.size(); i++) {
            try {sceneLoader.getEngine().addSystem(((IteratingSystem) tags.systemsClasses.get(i).newInstance()));} catch (Exception e) {};
        }
    }

}
