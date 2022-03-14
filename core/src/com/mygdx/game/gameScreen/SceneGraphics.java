package com.mygdx.game.gameScreen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.uwsoft.editor.renderer.components.*;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.components.sprite.AnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationComponent;
import com.uwsoft.editor.renderer.components.sprite.SpriteAnimationStateComponent;
import com.uwsoft.editor.renderer.physics.PhysicsBodyLoader;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.ItemWrapper;
import javafx.geometry.Pos;
import utils.$;

import static utils.$.print;

/**
 * Created by David on 18.05.2017.
 *
 * class to simplify graphical modifications on the screen, like entity's position, texture etc.
 * methods are static and easy to use.
 */

public class SceneGraphics {
    private static ItemWrapper root = null;
    private static Viewport viewport;
    public static int pixelToWorld;

    protected SceneGraphics() {
        root = new ItemWrapper(GameScreen.sceneLoader.getRoot());
    }

    protected static void applyRegion(String itemIdentifier, String regionName, boolean polygon) {
        Entity entity = new ItemWrapper(GameScreen.sceneLoader.getRoot()).getChild(itemIdentifier).getEntity();
        TextureRegionComponent textureRegionComponent = ComponentRetriever.get(entity, TextureRegionComponent.class);     //sans Entity : ComponentRetriever.get( new ItemWrapper(GameScreen.sceneLoader.getRoot()).getChild(""), ...
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        PolygonComponent polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);
        MyIResourceRetriever rm = ((MyIResourceRetriever) GameScreen.sceneLoader.getRm());
        float pixelToWorld = rm.getProjectVO().pixelToWorld;

        removeAnimation(itemIdentifier);

//        print("primitive atlasRegion:"+ ((TextureAtlas.AtlasRegion) textureRegionComponent.region).name);

        TextureAtlas.AtlasRegion atlasRegion = ((TextureAtlas) rm.assetManager.get(rm.assetManager.texturesFilenameList.get(regionName))).findRegion(regionName);
        TextureRegion textureRegion = (TextureRegion) atlasRegion;
//        atlasRegion.name = rm.assetManager.texturesFilenameList.get(regionName);

        textureRegionComponent.region = atlasRegion;
        if (polygon) textureRegionComponent.setPolygonSprite(polygonComponent, pixelToWorld);
        dimensionsComponent.width = textureRegion.getRegionWidth()/pixelToWorld;
        dimensionsComponent.height = textureRegion.getRegionHeight()/pixelToWorld;
    }

    private static void applyRegionToRelScreenLoc(Entity entity, String regionName) { // att. region.x calculé depuis le haut. Dans O2D, depuis le bas.
        MainItemComponent mainItemComponent = ComponentRetriever.get(entity, MainItemComponent.class);
        TextureRegionComponent textureRegionComponent = ComponentRetriever.get(entity, TextureRegionComponent.class);     //sans Entity : ComponentRetriever.get( new ItemWrapper(GameScreen.sceneLoader.getRoot()).getChild(""), ...
        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        PolygonComponent polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);
        MyIResourceRetriever rm = ((MyIResourceRetriever) GameScreen.sceneLoader.getRm());
        int pixelToWorld = rm.getProjectVO().pixelToWorld;
        Viewport viewport = ComponentRetriever.get(GameScreen.sceneLoader.getRoot(), ViewPortComponent.class).viewPort;


        TextureAtlas.AtlasRegion atlasRegion = ((TextureAtlas) rm.assetManager.get(rm.assetManager.texturesFilenameList.get(regionName))).findRegion(regionName);
        setOrigin(entity, 0,0);

        /// extrait de la région, à revoir
//        atlasRegion.setRegion(atlasRegion,
//                (int)(transformComponent.x*1), (int)(transformComponent.y*1),
//                (int)(atlasRegion.getRegionWidth()-transformComponent.x*1), (int)((atlasRegion.getRegionHeight()-transformComponent.y*1))
//        );

        textureRegionComponent.region = atlasRegion;
        textureRegionComponent.setPolygonSprite(polygonComponent, pixelToWorld);
        dimensionsComponent.width = textureRegionComponent.region.getRegionWidth()/pixelToWorld; //peut-être mettre au début? (bizarre)
        dimensionsComponent.height = textureRegionComponent.region.getRegionHeight()/pixelToWorld;
    }

    protected static void applyGlobalRegionByTagName(String tagName, String regionName) {
        ImmutableArray<Entity> entities = GameScreen.sceneLoader.engine.getEntities();
        for(Entity entity: entities) {
            MainItemComponent mainItemComponent = ComponentRetriever.get(entity, MainItemComponent.class);
            if(mainItemComponent.tags.contains(tagName)) {
                applyRegionToRelScreenLoc(entity, regionName);
            }

        }

    }

    // -- applique à l'entity les components nécessaires pour afficher l'animation.
    public static void applyAnimation(String itemIdentifier, String animationName) {
        //OLDTODO : attribuer components seulement si inexistants. (probablement pas nécessaire)

        Entity entity = root.getChild(itemIdentifier).getEntity();

//        entity.remove(AnimationComponent.class);
//        entity.remove(SpriteAnimationComponent.class);
//        entity.remove(SpriteAnimationStateComponent.class);
        entity.add(((MyIResourceRetriever) GameScreen.sceneLoader.getRm()).assetManager.spriteAnimationComponentHashMap.get(animationName));
        entity.add(((MyIResourceRetriever) GameScreen.sceneLoader.getRm()).assetManager.animationComponentHashMap.get(animationName));
        entity.add(((MyIResourceRetriever) GameScreen.sceneLoader.getRm()).assetManager.animFactory.createSpriteAnimationStateComponent(animationName)); /// créer le stateComponent individuel de l'entité

        print(entity.getComponents());
    }

    public static void setPositionByCenter(Entity entity, Vector2 position) {
        setPositionByCenter(entity, position, entity.getComponent(TransformComponent.class).rotation);
    }
    public static void setPositionByCenter(Entity entity, Vector2 position, float angle) { // method to set
        PhysicsBodyComponent physicsBodyComponent = entity.getComponent(PhysicsBodyComponent.class);
        if (physicsBodyComponent != null) {
            physicsBodyComponent.body.setTransform(position.x*PhysicsBodyLoader.getScale(), position.y*PhysicsBodyLoader.getScale(), (float) Math.toRadians(angle));
            // rem : pour res 1350x... le scaling vaudra 1/20. Très probablement, pixelToWorld affecte ce scale lors de la création du world.
        } else {
            //TODO
        }
    }

    public static class PositionByVelocity extends IteratingSystem{

        private Entity MyEntity;
        public Vector2 position;
        public float angle;

        public PositionByVelocity(Family family) {
            super(family);
            GameScreen.sceneLoader.engine.addSystem(this);
        }

        public void start(Entity entity) { this.MyEntity = entity; }

        public void setCoords(Vector2 pos) { position = pos; }

        @Override
        protected void processEntity(Entity e, float deltaTime) {
//            PhysicsBodyComponent physicsBodyComponent = this.MyEntity.getComponent(PhysicsBodyComponent.class);
//            Vector2 linearVelocityVector = new Vector2(position).scl(PhysicsBodyLoader.getScale()).sub(physicsBodyComponent.body.getPosition());
//            physicsBodyComponent.body.setLinearVelocity(linearVelocityVector);
//            float deltaAngle = new Vector2(physicsBodyComponent.body.getPosition()).angle(new Vector2((float) Math.cos(Math.toRadians(angle)), (float) Math.sin(Math.toRadians(angle))));
//            physicsBodyComponent.body.setAngularVelocity(deltaAngle);
        }

        public void stop() { GameScreen.sceneLoader.engine.removeSystem(this); }
    }

    private static void removeAnimation(String itemIdentifier) { // vraiment private?
        Entity entity = root.getChild(itemIdentifier).getEntity();

        entity.remove(SpriteAnimationComponent.class);
        entity.remove(AnimationComponent.class);
        entity.remove(SpriteAnimationStateComponent.class);
    }

    public static void setViewport(Viewport v) {
        viewport = v;
    }

    public static Viewport getViewport() {
        return viewport;
    }

    private static void setOrigin(Entity entity, float x, float y) {
        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        PolygonComponent polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);
        float oldX = transformComponent.x, oldY = transformComponent.y;
        Vector2[][] vertices = polygonComponent.vertices;

        transformComponent.x = x; transformComponent.y = y;
        for (int i=0; i<vertices.length; i++) {
            for (int j=0; j <vertices[i].length; j++) {
                vertices[i][j].x += oldX-x;
                vertices[i][j].y += oldY-y;
            }
        }
        polygonComponent.vertices = vertices;
    }

    public static Vector2 unproject(int screenX, int screenY) {
        Vector3 unprojectedV3 = SceneGraphics.getViewport().getCamera().unproject(new Vector3(screenX,screenY,0));
        return new Vector2(unprojectedV3.x, unprojectedV3.y);
    }
}