package com.mygdx.game.gameScreen;

import com.uwsoft.editor.renderer.SceneLoader;

/**
 * Created by David on 02.06.2017.
 *
 * class setting the graphical environment type for a level.
 */
public class Nature {

    private static final String[] natureTypes = {"forest","jungle","swamp","enchanted","industry","woodwork"};
    private String nature;
    private String background;
    public String ground;

    protected Nature() {
        nature = checkNature();
        background = nature+"_background";
        ground = nature+"_ground";
    }

    private String checkNature() {
        String n = "default";
        for (int i=0; i<natureTypes.length; i++) {
            if (GameScreen.sceneLoader.getSceneVO().sceneName.contains(natureTypes[i])) {
                nature = natureTypes[i];
                break;
            }
        }
        return n;
    }
}
