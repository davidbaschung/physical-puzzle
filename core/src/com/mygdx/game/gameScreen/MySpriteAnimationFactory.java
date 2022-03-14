package com.mygdx.game.gameScreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Settings;
import com.uwsoft.editor.renderer.components.TextureRegionComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationStateComponent;
import com.uwsoft.editor.renderer.data.FrameRange;

import java.util.HashMap;

/**
 * Created by David on 13.06.2017.
 *
 * class to create all necessary animation components.
 * Note : does NOT override overlap2D's SpriteAnimationFactory
 * belongs to AssetManager
 *
 */
public class MySpriteAnimationFactory {

    private final MyAssetManager assetManager;

    protected MySpriteAnimationFactory(MyAssetManager am) { this.assetManager = am; }

    protected void createSpriteAnimationData(String animationName) { // REMARQUE : utilisation prévue pour une animation par fichier.

        Array<TextureAtlas.AtlasRegion> regions = ((TextureAtlas) assetManager.get(assetManager.myRM.assetPrefix + animationName + assetManager.myRM.atlasSuffix)).getRegions();

        SpriteAnimationComponent spriteAnimationComponent = new SpriteAnimationComponent();
        spriteAnimationComponent.animationName = animationName;

        spriteAnimationComponent.frameRangeMap = new HashMap<String, FrameRange>();

        spriteAnimationComponent.fps = Settings.fps;
        spriteAnimationComponent.currentAnimation = Settings.DefaultAnimationName;
        spriteAnimationComponent.playMode = Animation.PlayMode.LOOP;//NORMAL;
        FrameRange frameRange = new FrameRange(spriteAnimationComponent.currentAnimation, 0, regions.size - 1);
        spriteAnimationComponent.frameRangeMap.put(frameRange.name, frameRange);

        AnimationComponent animationComponent = new AnimationComponent(); // vraiment nécessaire? spriteAnimationComponent nécessaire aussi??

        assetManager.spriteAnimationComponentHashMap.put(animationName, spriteAnimationComponent);
        assetManager.animationComponentHashMap.put(animationName, animationComponent);

    }

    protected SpriteAnimationStateComponent createSpriteAnimationStateComponent(String animationName) {

        Array<TextureAtlas.AtlasRegion> regions = ((TextureAtlas) assetManager.get(assetManager.myRM.assetPrefix + animationName + assetManager.myRM.atlasSuffix)).getRegions();
        SpriteAnimationStateComponent stateComponent = new SpriteAnimationStateComponent(regions);

        //TODO playmode etc.
//        if(spriteAnimationComponent.frameRangeMap.isEmpty()) {
//            spriteAnimationComponent.frameRangeMap.put("Default", new FrameRange("Default", 0, regions.size-1));
//        }
//        if(spriteAnimationComponent.currentAnimation == null) {
//            spriteAnimationComponent.currentAnimation = (String) spriteAnimationComponent.frameRangeMap.keySet().toArray()[0];
//        }
//        if(spriteAnimationComponent.playMode == null) {
//            spriteAnimationComponent.playMode = Animation.PlayMode.LOOP;
//        }
//
        stateComponent.set(assetManager.spriteAnimationComponentHashMap.get(animationName));
//
        TextureRegionComponent textureRegionComponent = new TextureRegionComponent();
        textureRegionComponent.region = regions.get(0); /// vraiment?

        //TODO séparer les components -- (pas nécessaire? spriteAninmationComponent et SpriteAnimationStateComponent contiennent tous deux des elmts propres à l'entity)
//        entity.add(textureRegionComponent);
//        entity.add(stateComponent);
//        entity.add(animationComponent);
//        entity.add(spriteAnimationComponent);
//

        return stateComponent;
//        return spriteAnimationComponent;
    }
}
