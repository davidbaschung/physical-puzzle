package com.mygdx.game.gameScreen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameScreen.entities.Tags;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.data.ResolutionEntryVO;

import static utils.$.print;

/**
 * Created by David on 18.05.2017.
 *
 * Main screen for a any level.
 */
public class GameScreen implements Screen {
    public static SceneLoader sceneLoader = null;
    private MyIResourceRetriever myIResourceRetriever;
    private SceneGraphics sceneGraphics;
    private SceneManager sceneManager;
    private Vector2 viewportMinDimension;
    private Viewport viewport;
    private OrthographicCamera camera;
    private boolean assetsLoaded;
    private boolean firstRender;
    private boolean worldFirstUpdating;
    private String workingResolutionName;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private FPSLogger logger = new FPSLogger();

    public GameScreen() {
    }

    @Override
    public void show() {
        System.out.println("GameScreen.show");
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewportMinDimension = new Vector2(56,100);
        viewport = new ExtendViewport(viewportMinDimension.x, viewportMinDimension.y, camera);
        SceneGraphics.setViewport(viewport);
        myIResourceRetriever = new MyIResourceRetriever();
        myIResourceRetriever.loadProjectVO();
        workingResolutionName = "1350"; //nom de résolution O2D.  ATT changer dans pixelToWorld;
        myIResourceRetriever.setWorkingResolution(workingResolutionName);
        myIResourceRetriever.initAllResources();
        myIResourceRetriever.getProjectVO().pixelToWorld = (int) ((float) getResolution().height / viewportMinDimension.y);
//        myIResourceRetriever.getProjectVO().pixelToWorld = (int) ((float) getResolution().width / viewportMinDimension.x);
//        myIResourceRetriever.getProjectVO().pixelToWorld = 19;
        SceneGraphics.pixelToWorld = myIResourceRetriever.getProjectVO().pixelToWorld;
        print("res : "+getResolution().width+" "+getResolution().height);
        print("pxToW : "+myIResourceRetriever.getProjectVO().pixelToWorld);
        sceneLoader = new SceneLoader(myIResourceRetriever);
//        sceneLoader.setResolution("1350");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (firstRender==false) {System.out.println("Gamescreen first rendering"); firstRender=true;}

        if ( ! ((MyIResourceRetriever) sceneLoader.getRm()).assetManager.update()) {
            System.out.println("assets loading " + ((MyIResourceRetriever) sceneLoader.getRm()).assetManager.getProgress() *100 +" %");
        } else if (assetsLoaded==false) {
            System.out.println("assets loading : done");
            initWhenAssetsLoaded();
        }

        if (assetsLoaded==true) {
            sceneLoader.getEngine().update(Gdx.graphics.getDeltaTime());
            worldFirstUpdating = true;
            debugRenderer.render(sceneLoader.world, camera.combined);
            logger.log();
        }

        if (worldFirstUpdating==true) {
            initWhenWorldUpdated();
            worldFirstUpdating = false;
        }
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen.resize");
        viewport.getCamera().position.set(viewportMinDimension.x/2, viewportMinDimension.y/2, 0f); //ATT. doit rester en mode portrait.
    }

    @Override
    public void pause() {
        System.out.println("GameScreen.pause");
    }

    @Override
    public void resume() {
        System.out.println("GameScreen.resume");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen.hide");
    }

    @Override
    public void dispose() {
        System.out.println("GameScreen.dispose");

        camera = null;
        viewportMinDimension = null;
        viewport = null;
        myIResourceRetriever = null;
        workingResolutionName = null;
        myIResourceRetriever.initAllResources();
//        myIResourceRetriever.getProjectVO().pixelToWorld = getResolution().height / (int)viewportMinDimension.y;
//        sceneLoader = new SceneLoader(myIResourceRetriever);
    }

    // - - - - - - - - - - my methods - - - - - - - - - -


    private void initWhenAssetsLoaded() {
        sceneLoader.loadScene("MainScene", viewport);
        sceneGraphics = new SceneGraphics();
        sceneManager = new SceneManager(new Tags());
        assetsLoaded = true;
        resize(0,0);
    }

    private void initWhenWorldUpdated() {
        setBodiesUserData();
        sceneLoader.world.setContactListener(new GameContactListener());
    }

    private void setBodiesUserData() {
        for (Entity entity : sceneLoader.engine.getEntities()) {
            PhysicsBodyComponent physicsBodyComponent = entity.getComponent(PhysicsBodyComponent.class);
            if (physicsBodyComponent != null) {
                MainItemComponent mainItemComponent = entity.getComponent(MainItemComponent.class);
                physicsBodyComponent.body.setUserData(mainItemComponent.itemIdentifier);
            }
        }
    }

    private ResolutionEntryVO getResolution() {
        ResolutionEntryVO resolution = null;
        for(int i = 0; i< myIResourceRetriever.getProjectVO().resolutions.size; i++) {
            if (workingResolutionName.equals(myIResourceRetriever.getProjectVO().resolutions.get(i).name)
                    || workingResolutionName.equals(myIResourceRetriever.getProjectVO().originalResolution.name)) {
                resolution = myIResourceRetriever.getProjectVO().resolutions.get(i);
                return resolution;
            }
        }
        return null;
    }

}
