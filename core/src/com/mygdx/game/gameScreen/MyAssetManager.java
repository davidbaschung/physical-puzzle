package com.mygdx.game.gameScreen;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationComponent;

import java.util.HashMap;

import static utils.$.print;

/**
 * Created by David on 02.06.2017.
 *
 * class to list and organize textures, atlases and anims. Acts as soon as the loading is finished, allowing the IResourceRetriever to get existing assets.
 * belongs to MyIResourceRetriever
 */
public class MyAssetManager extends AssetManager{

    protected MyIResourceRetriever myRM;
    protected HashMap<String, String> texturesFilenameList;
    protected MySpriteAnimationFactory animFactory;
    protected HashMap<String, SpriteAnimationComponent> spriteAnimationComponentHashMap;
    protected HashMap<String, AnimationComponent> animationComponentHashMap;
    protected HashMap<String, String> animsFilenameList;
    private boolean assetsOrganized;

    protected MyAssetManager(MyIResourceRetriever rm) {
        super();
        myRM = rm;
        animFactory = new MySpriteAnimationFactory(this);
        spriteAnimationComponentHashMap = new HashMap<String, SpriteAnimationComponent>();
        animationComponentHashMap = new HashMap<String, AnimationComponent>();

    }

    @Override
    public synchronized boolean update() {
        boolean loaded = super.update();
        if (loaded && !assetsOrganized) {
            print("assetNames loaded : "+getAssetNames().toString());
            createTexturesFilenameList();
            createAnimsFilenameList();
            assetsOrganized = true;
        }
        return loaded;
    }

    // - - - my methods

    private void createTexturesFilenameList() {   /// utilisé pour création : get (String fileName, Class<T> type)
        HashMap<String, String> texturesFL = new HashMap<String, String>();
        for (int i=0; i<myRM.textureList.length/*atlasArray.size*/; i++) {
            TextureAtlas textureAtlas = (TextureAtlas) get(myRM.assetPrefix + myRM.textureList[i] + myRM.atlasSuffix);
            Array regions = textureAtlas.getRegions();
            for (int j=0; j<regions.size; j++) {
                texturesFL.put(((TextureAtlas.AtlasRegion)regions.get(j)).name, getAssetFileName(textureAtlas));
            }
            print("one loop "+i);
        }
        texturesFilenameList = texturesFL;
    }

    // -- créer la liste des animations et components
    private void createAnimsFilenameList() { //TODO renommer, création frameRanges? -- Factory ici
//        HashMap<String, String>
        for (int i=0; i<myRM.animList.length; i++) {
            animFactory.createSpriteAnimationData(myRM.animList[i]); //TODO (création provisoire), gérer components existants (pas besoin?)
        }

    }

//    private void addToTexturesFilenameList(String fileName) { // OLD
//        Array regions = ((TextureAtlas) atlasArray).getRegions(); //getAll pas possible! :-( car assets pas chargés
//    }
}
