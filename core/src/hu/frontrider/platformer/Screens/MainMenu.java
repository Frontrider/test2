package hu.frontrider.platformer.Screens;


import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import hu.frontrider.platformer.TiledPlatformer;
import hu.frontrider.platformer.Tween.ActorAccessor;


/**
 * Created by Frontrider on 2015.09.08..
 */
public class MainMenu implements Screen
{
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay,buttonExit;
    private Label heading;
    private TweenManager tweenManager;

    @Override
    public void show()
    {
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(Gdx.files.internal("gui/atlas.pack"));
        skin = new Skin(Gdx.files.internal("gui/menuskin.json"),atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        buttonPlay = new TextButton("PLAY", skin);
        buttonPlay.pad(15);
        buttonPlay.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event,float x,float y)
            {
               ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
               Gdx.app.log("main menu","Start");
            }
        }
        );
        buttonExit = new TextButton("EXIT", skin);
        buttonExit.pad(15);
        buttonExit.addListener(new ClickListener()
                               {
                                   @Override
                                   public void clicked(InputEvent event,float x,float y)
                                   {
                                       Gdx.app.exit();
                                   }
                               }
        );



        heading = new Label(TiledPlatformer.Name,skin);
        heading.setFontScale(3);

        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(15);
        table.row();
        table.add(buttonExit);

        table.debug();
        stage.addActor(table);

        tweenManager = new TweenManager();

        Tween.registerAccessor(Actor.class, new ActorAccessor());

                Timeline.createSequence().beginSequence()
                        .push(Tween.to(heading, ActorAccessor.RGB, 0.5f).target(0, 0, 1))
                        .push(Tween.to(heading, ActorAccessor.RGB,0.5f).target(0,1,0))
                        .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,0,0))
                        .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,1,0))
                        .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(0,1,1))
                        .push(Tween.to(heading,ActorAccessor.RGB,.5f).target(1,0,1))
                        .push(Tween.to(heading, ActorAccessor.RGB,.5f).target(0,0,0))
                        .end().repeat(Tween.INFINITY,0).start(tweenManager);

                Timeline.createSequence().beginSequence()
                        .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                        .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
                        .push(Tween.from(heading, ActorAccessor.ALPHA, 0.5f).target(0))
                        .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, 0.5f).target(1))
                        .push(Tween.to(buttonExit, ActorAccessor.ALPHA, 0.5f).target(1))
                        .end().start(tweenManager);

                 Tween.from(table,ActorAccessor.ALPHA,.5f).target(0);
                 Tween.from(table, ActorAccessor.ALPHA,.5f).target(Gdx.graphics.getHeight() /8).start(tweenManager);


                         Gdx.app.log("main menu", "inicializálva");
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        tweenManager.update(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
       // stage.setViewport(new StretchViewport(width, height));
        table.setClip(true);
        table.setSize(width,height);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }
}
