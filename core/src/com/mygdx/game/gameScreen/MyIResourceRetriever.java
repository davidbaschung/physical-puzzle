package com.mygdx.game.gameScreen;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uwsoft.editor.renderer.resources.IResourceLoader;
import com.uwsoft.editor.renderer.resources.IResourceRetriever;
import com.uwsoft.editor.renderer.resources.ResourceManager;

import java.io.File;
import java.util.HashMap;

import static utils.$.print;

/**
 * Created by David on 18.05.2017.
 *
 * class for initial resources loading through AssetManager
 */
public class MyIResourceRetriever extends ResourceManager implements IResourceLoader, IResourceRetriever {

    MyAssetManager assetManager;
    private String atlasName;
    private String backgroundsName;
    protected HashMap<String, TextureRegion> createdTextureRegions;
    protected String[] textureList;
    private HashMap<String, TextureAtlas> createdAnimAtlasNames;
    protected String[] animList;
    protected String assetPrefix;
    protected String atlasSuffix;


    public MyIResourceRetriever() {
        super();
//        packResolutionName = "1350";
        textureList = new String[] {"Backgrounds","Small","Grounds"};
        animList = new String[] {"Anim","Anim2"};
        //TODO : chargement auto des assets selon pack de niveaux
        assetManager = new MyAssetManager(this);
        createdTextureRegions = new HashMap<String, TextureRegion>();
    }

    public void setWorkingResolution(String resolution) {
        super.setWorkingResolution(resolution);
        assetPrefix = packResolutionName + File.separator;
        atlasSuffix = ".atlas";
    }

    // IAssetLoader - - -

    @Override
    public void initAllResources() {
        super.initAllResources();
    }
    @Override // ~idem
    public void loadAssets() {
        loadAtlasPack();
        loadParticleEffects();
        loadSpineAnimations();
        loadSpriteAnimations();
        loadSpriterAnimations();
        loadFonts();
        loadShaders();
    }

    @Override // charge seulement les textureregions (pas anims)
    public void loadAtlasPack() {
        for (int i = 0; i< textureList.length; i++) {
            assetManager.load(packResolutionName + File.separator + textureList[i]+".atlas", TextureAtlas.class);
        }
    }
    @Override
    public void loadParticleEffects() {
        super.loadParticleEffects();
    }

    @Override
    public void loadSpineAnimation(String name) {
        super.loadSpineAnimation(name);
    }

    @Override // charge les animations
    public void loadSpriteAnimations() {
//        super.loadSpriteAnimations();
        for (int i=0; i<animList.length; i++) {
            assetManager.load(packResolutionName + File.separator + animList[i]+".atlas", TextureAtlas.class);
            print(packResolutionName);
        }
    }

    @Override
    public void loadSpriterAnimations() {
        super.loadSpriterAnimations();
    }

    @Override
    public void loadFonts() {
        super.loadFonts();
    }

    @Override
    public void loadShaders() {
        super.loadShaders();
    }

    // IResourceRetriever - - d -

    @Override
    public TextureRegion getTextureRegion(String name) {
        TextureRegion region;
//        try { // chercher une ??ventuelle r??gion customis??e
//            region = createdTextureRegions.get(name);
//        } catch (NullPointerException e) {
//            try { // si pas de custom, chercher existante

        region = createdTextureRegions.get(name);
        if (region==null) {
            String mapFilename = assetManager.texturesFilenameList.get(name);
            region = ((TextureAtlas) assetManager.get(mapFilename)).findRegion(name);
        }
//            } catch (NullPointerException f) { // exception lanc??e par loadscene(), cherchant une custom encore inexistante
//                String mapFilename = assetManager.texturesFilenameList.get(name);
//                region = ((TextureAtlas) assetManager.get(mapFilename)).findRegion("Default");
//            }
//        }
        return region;
    }

    @Override
    public TextureAtlas getSpriteAnimation(String name) {
        return super.getSpriteAnimation(name);

        // m??thode pas n??cessaire. Toutes les animations sont charg??es par loadAtlasPack. --> ??? n??cessaire, m??thode employ??e secr??tement par O2D au runTime! De plus, on acc??de liste des anims.
        // m??thode quand m??me pas n??cessaire, car il s'agit ici des animations pr??sentes dans la sc??ne (donc aucune). L'anim est inject??e dans les components de l'entity apr??s coup.

//        TextureAtlas animAtlas;
//        animAtlas = createdAnimAtlasNames.get(name);
//        if (animAtlas==null) {
//            String mapFilename = assetManager.animsFilenameList.get(name);
//            animAtlas = (TextureAtlas) assetManager.get(mapFilename);
//        }
//        return animAtlas;

    }

    // Ancien (& non fonctionnel) - Chargement de body externes cr????s par physics-body-editor et impl??mentation dans la sc??ne

//    @Override
//    public SceneVO loadSceneVO(String sceneName) {
//        FileHandle file = Gdx.files.internal(scenesPath + File.separator + sceneName + ".dt");
//        Json json = new Json();
//        SceneVO sceneVO = json.fromJson(SceneVO.class, file.readString());
//
//        loadedSceneVOs.put(sceneName, sceneVO);
//
//        System.out.println("sColorPrimitives : "+ sceneVO.composite.sColorPrimitives);
//        return sceneVO;
//    }

//    @Override
//    public TextureAtlas getSkeletonAtlas(String name) {
//        return super.getSkeletonAtlas(name);
//    }
//
//    @Override
//    public TextureAtlas getSpriteAnimation(String name) {
//        TextureAtlas spriteAnimation = assetManager.get(name);
//        return spriteAnimation;
//    }

}
