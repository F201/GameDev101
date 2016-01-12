package com.mygdx.game;

/**
 * Created by Fathurrohman on 2/9/2015.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {

    MyGdxGame main;

    BitmapFont scoreFont; //object untuk menggambar text ke layar
    SpriteBatch batch;    //spritebatch adalah class yang digunakan untuk melakukan draw ke layar
    Texture pad_1, pad_2, ball;//object untuk menggambar image ke layar
    Rectangle rectanglePad_1, rectanglePad_2, rectangleBall;//object rectangle untuk melakukang pengecekan collusion antar object
    float padSpeed = 3f;// variabel kecepatan paddle
    float ballSpeedX = 4f;//variabel kecepatan bola pada sumbu X
    float ballSpeedY = 4f;//variabel kecepatan bola pada sumbu Y

    int scorePad1, scorePad2;//variabel untuk menampung jumlah score
    String scoreText;//variabel untuk menampung tulisan score

    public GameScreen() {
        //this.main = main;
        inisialisasi();//mengeksekusi procedure insisialisasi
    }

    public void inisialisasi() {
        //membuat object spritebatch
        batch = new SpriteBatch();

        //membuat object texture dengan inputan image paddle dan ball
        pad_1 = new Texture(Gdx.files.internal("Pad.png"));
        pad_2 = new Texture(Gdx.files.internal("Pad.png"));
        ball = new Texture(Gdx.files.internal("Ball.png"));

        //membuat rectangle untuk collusion detection dengan inputan posisi s y dan panjang lebarnya
        rectanglePad_1 = new Rectangle(0, Gdx.graphics.getHeight() / 2 - pad_1.getHeight() / 2,
                pad_1.getWidth(), pad_1.getHeight());
        rectanglePad_2 = new Rectangle(Gdx.graphics.getWidth() - pad_2.getWidth(), Gdx.graphics.getHeight() / 2 - pad_2.getHeight() / 2,
                pad_2.getWidth(), pad_2.getHeight());
        rectangleBall = new Rectangle(Gdx.graphics.getWidth() / 2 - ball.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - ball.getHeight() / 2, ball.getWidth(), ball.getHeight());
        loadData();

        //membuat object
        scoreFont = new BitmapFont();
        scoreText = scorePad1 + "   PONG   " + scorePad2;
    }

    @Override
    public void render(float delta) {

        //algoritma untuk menggerakan paddle 1 ke atas dan bawah
        if (Gdx.input.isKeyPressed(Keys.W) && rectanglePad_1.y < Gdx.graphics.getHeight() - rectanglePad_1.getHeight()) {
            rectanglePad_1.setPosition(rectanglePad_1.x, rectanglePad_1.y += padSpeed);
        } else if (Gdx.input.isKeyPressed(Keys.S) && rectanglePad_1.y > 0) {
            rectanglePad_1.setPosition(rectanglePad_1.x, rectanglePad_1.y -= padSpeed);
        }
        //algoritma untuk menggerakan paddle 2 ke atas dan bawah
        if (Gdx.input.isKeyPressed(Keys.UP) && rectanglePad_2.y < Gdx.graphics.getHeight() - rectanglePad_2.getHeight()) {
            rectanglePad_2.setPosition(rectanglePad_2.x, rectanglePad_2.y += padSpeed);
        } else if (Gdx.input.isKeyPressed(Keys.DOWN) && rectanglePad_2.y > 0) {
            rectanglePad_2.setPosition(rectanglePad_2.x, rectanglePad_2.y -= padSpeed);
        }
        //algoritma untuk membatasi agar bola memantul saat mengenai batas atas dan batas bawah layar
        if (rectangleBall.y >= Gdx.graphics.getHeight() - rectangleBall.height / 2 || rectangleBall.y <= 0) {
            ballSpeedY *= -1;
        }
        //algoritma untuk menambah score pada player 2 dan memindahkan bola ke tengah saat bola melewati paddle 1
        if (rectangleBall.x < 0 - 100) {
            scorePad2++;
            reset();
        }
        //algoritma untuk menambah score pada player 1 dan memindahkan bola ke tengah saat bola melewati paddle 2
        if (rectangleBall.x > Gdx.graphics.getWidth() + 100) {
            scorePad1++;
            reset();
        }
        //algoritma untuk pengecekan apakah paddle 1 bertabrakan dengan bola jika ya bola di pantulkan
        if (rectanglePad_1.overlaps(rectangleBall)) {
            ballSpeedX *= -1;
        }
        //algoritma untuk pengecekan apakah paddle 2 bertabrakan dengan bola jika ya bola di pantulkan
        if (rectanglePad_2.overlaps(rectangleBall)) {
            ballSpeedX *= -1;
        }
        //save game
        if(Gdx.input.isKeyPressed(Keys.P)) {
            saveData();
        }

        if(Gdx.input.isKeyPressed(Keys.O)) {
            clearData();
        }

        //algoritma untuk menggerakan bola
        rectangleBall.setPosition(rectangleBall.x += ballSpeedX, rectangleBall.y += ballSpeedY);

        Gdx.gl.glClearColor(0, 0, 0, 1);//draw warna hitam ke layar
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);//clear screen

        batch.begin();
        batch.draw(pad_1, rectanglePad_1.x, rectanglePad_1.y, rectanglePad_1.getWidth(), rectanglePad_1.getHeight());//draw paddle 1 ke layar
        batch.draw(pad_2, rectanglePad_2.x, rectanglePad_2.y, rectanglePad_2.getWidth(), rectanglePad_2.getHeight());//draw paddle 2 ke layar
        batch.draw(ball, rectangleBall.x, rectangleBall.y, rectangleBall.getWidth(), rectangleBall.getHeight());//draw bola ke layar
        scoreFont.draw(batch, scoreText,
                Gdx.graphics.getWidth() / 2 - scoreFont.getBounds(scoreText).width / 2, Gdx.graphics.getHeight() - scoreFont.getBounds(scoreText).height / 2);//draw score text ke layar
        batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //procedure untuk memindahkan bola ke tengah dan mengupdate scoretext
    private void reset() {
        rectangleBall.setPosition(Gdx.graphics.getWidth() / 2 - ball.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - ball.getHeight() / 2);
        scoreText = scorePad1 + "   PONG   " + scorePad2;
    }


    private void loadData() {
        scorePad1 = GameData.getPrefScore(GameData.PREF_SCORE_1);
        scorePad2 = GameData.getPrefScore(GameData.PREF_SCORE_2);
    }

    private void saveData() {
        GameData.setPrefScore(GameData.PREF_SCORE_1,scorePad1);
        GameData.setPrefScore(GameData.PREF_SCORE_2,scorePad2);
    }

    private void clearData() {
        GameData.clearData();
        SaveGame.clearData();
    }

}
