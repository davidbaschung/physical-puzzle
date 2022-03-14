package com.mygdx.game.gameScreen.entities;

import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.renderer.components.TextureRegionComponent;
import com.uwsoft.editor.renderer.scripts.IScript;

import static utils.$.print;

/**
 * Created by David on 01.06.2017.
 */
public class Ground extends Entity { // utilité à revoir

    public Ground() {
        print("NEW GROUND !");
    }
}
