package hu.frontrider.platformer.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Frontrider on 2015.09.08..
 */
public class LevelMenu implements Screen
{
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table,container;
    private TextButton buttonPlay,buttonBack;
    private List list;
    private ScrollPane scrollPane;

    @Override
    public void show()
    {
         stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("gui/atlas.pack");

        skin = new Skin(Gdx.files.internal("gui/menuskin.json"),atlas);

        skin = new Skin(Gdx.files.internal("gui/menuSkin.json"),atlas);

        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table.debug();


        list = new List(skin);

        list.setItems(new String[]{"one","two","three","four","five","six"});

        scrollPane  = new ScrollPane(list,skin,"transparent");

        container = new Table();
        container.add(scrollPane).width(500f).height(50f);
        container.row();


        buttonPlay = new TextButton("Play",skin);
        buttonPlay.pad(15);
        buttonPlay.addListener(new ClickListener()
                               {
                                   @Override
                                   public void clicked(InputEvent event,float x,float y)
                                   {

                                       GameScreen levelscreen = new GameScreen();
                                       levelscreen.put(list.getSelected().toString());

                                       ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(levelscreen);
                                       Gdx.app.log("level select","Start");
                                   }
                               }
        );



        buttonBack = new TextButton("Back",skin);
        buttonBack.pad(10);
        buttonBack.addListener(new ClickListener()
                               {
                                   @Override
                                   public void clicked(InputEvent event,float x,float y)
                                   {
                                       ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                                       Gdx.app.log("level select","back");
                                   }
                               }
        );

        table.add("Level Select");

        table.row();

        table.add(buttonPlay);

        table.getCell(buttonPlay).spaceRight(300);

        table.getCell(buttonPlay).spaceRight(500);

        table.add(scrollPane);
        table.row();
        table.add(buttonBack);

        stage.addActor(table);

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {

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
