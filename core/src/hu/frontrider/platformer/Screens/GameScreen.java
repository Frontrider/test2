package hu.frontrider.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import hu.frontrider.platformer.Controller.MyContactFilter;
import hu.frontrider.platformer.Classes.Disposer;
import hu.frontrider.platformer.Entity.Enemy.EnemyHandler;
import hu.frontrider.platformer.Interfaces.Drawable;
import hu.frontrider.platformer.Interfaces.PickupObjects;
import hu.frontrider.platformer.Entity.Player.Player;
import hu.frontrider.platformer.Classes.BodyRemover;
import hu.frontrider.platformer.Classes.Drawer;
import hu.frontrider.platformer.Helper.StaticVariables;
import hu.frontrider.platformer.Controller.InputController;
import hu.frontrider.platformer.Controller.MyContactListener;
import hu.frontrider.platformer.Map.*;
import hu.frontrider.platformer.Classes.Updater;

import javax.xml.transform.Result;

/**
 * Created by Frontrider on 2015.09.10..
 */

public class GameScreen implements Screen
{
    private static final String LOG_TAG = "Game screen";

    private static final int POSITIONITERATIONS = 5;
    private final float TIMESTEP = 1/60f;
    private final int VELOCITYITERATIONS = 8;

    private static final int TILE_SIZE = StaticVariables.TILE_SIZE;
    private static final float WORLD_UNIT = (float) 1/TILE_SIZE;

    private World world;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tileRenderer;
    private SpriteBatch batch;
    private SpriteBatch UIBatch;
    private BodyRemover bodyRemover;

    private EnemyHandler enemyHandler;
    private Updater updater;
    private Disposer disposer;
    private Drawer drawer;

    Array<Body> bodies;

    private Player player;

    private Stage gui;
    private Table table;
    private TextureAtlas atlas;
    private Skin GuiSkin;
    private ProgressBar shieldbar;
    boolean running = true;
    Array results;

    private InputController inputController;

    public GameScreen(String mapname)
    {
        map = new TmxMapLoader().load("maps/"+mapname+".tmx");
    }

    @Override
    public void show()
    {
        results = new Array();
        if(running){
        world = new World(new Vector2(0,-15f),true);
        bodyRemover = new BodyRemover();
        bodies = new Array();

        batch = new SpriteBatch();
        UIBatch = new SpriteBatch();

        camera = new OrthographicCamera(Gdx.graphics.getWidth()/25 ,Gdx.graphics.getHeight()/25);
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 25 / 3, Gdx.graphics.getHeight() / 25 / 3);



        GroundLoader.buildShapes(map, TILE_SIZE, world);
        OneWayPlatform.buildShapes(map, TILE_SIZE, world);
        ArcLoader.LoadArcs(map, world);

        tileRenderer = new OrthogonalTiledMapRenderer(map,WORLD_UNIT);
        tileRenderer.setView(camera);
            player = new Player(world, PlayerLoader.getPos(map));
            camera.zoom = 2f;


            camera.position.set(player.getPosition().x, player.getPosition().y, 0);

            enemyHandler = new EnemyHandler( EnemyLoader.getPos(map, world,player),player);

            ZoneLoader.getPos(map, world);

            MapEnd.buildShapes(map,TILE_SIZE,world);

            Array<PickupObjects> Pickups = new Array();
            Pickups.addAll(EnergyCellLoader.getPos(map, world));
            Pickups.addAll(PowerupLoader.getPos(map, world, TILE_SIZE));
            updater = new Updater();
            updater.add(enemyHandler.getEnemies());
            updater.add(player);

            player.setUpdater(updater);

            disposer = new Disposer();
            disposer.add(enemyHandler.getEnemies());
            disposer.add(player);
            disposer.add(Pickups);

            drawer = new Drawer();
            drawer.add(Pickups);


            world.setContactFilter(new MyContactFilter());
            world.setContactListener(new MyContactListener());

            gui = new Stage();
            table = new Table();

            atlas = new TextureAtlas(Gdx.files.internal("gui/ui.pack"));
            GuiSkin = new Skin(Gdx.files.internal("gui/ui.json"),atlas);

            shieldbar = new ProgressBar(0f,100f,1f,false,GuiSkin);
            shieldbar.setRange(0,100);

            table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            table.add(shieldbar);
            table.top();
            table.left();
            gui.addActor(table);

        inputController = new InputController(player, player);
                    Gdx.input.setInputProcessor(inputController);

        inputController.enableTouch(camera);
        switch (Gdx.app.getType()) {
            case Android:
                // android specific code

                break;
            case Desktop:
                // desktop specific code
                break;
            default:
                // Other platforms specific code
        }

    }
      //  updater.start();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glGenBuffer();

        world.getBodies(bodies);
        bodyRemover.removebodies(bodies, world);
        bodies.clear();
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        enemyHandler.update(player, world);
        updater.Update(delta);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();

        tileRenderer.setView(camera);
        tileRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //drawer.Draw(batch, world);
        drawer.Draw(batch,world,updater.Get());
        batch.end();

        shieldbar.setValue(player.getShield());
        gui.act();

        UIBatch.begin();
        gui.draw();
        UIBatch.end();
       // debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height)
    {
              //camera.viewportWidth = width/60;
              //camera.viewportWidth = height/60;
             // camera.update();
    }

    @Override
    public void pause()
    {
          running = false;
    }

    @Override
    public void resume()
    {
          running = true;
    }

    @Override
    public void hide()
    {
        dispose();
    }

    @Override
    public void dispose()
    {
       // world.dispose();
        //debugRenderer.dispose();
        map.dispose();
        gui.dispose();
        atlas.dispose();
        GuiSkin.dispose();
        UIBatch.dispose();

        disposer.dispose();
    }
}
