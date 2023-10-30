package scenes;
import java.util.Random;

import main.Entity;
import org.joml.Vector3f;

import Sound.SoundMaster;
import _2DPhysics.Physics2D;
import fontPancking.FontLoader;
import main.Input;
import main.Scene;
import main.Window;
import modeling.Model;
import modeling.ModelShape;
import modeling.TextMash;
import render.FrameBuffer;
import render.Renderer;

public class TestScene extends Scene{

    FrameBuffer fb;
    Renderer crt,g;
    
    public static void main(String[] args){
        Window.scenes = new Scene[1];
        Physics2D.setGrvity(0, 10);
        Window.scenes[0] = new TestScene();
        new Window(900,600,"test");
    }
    Model k[] = new Model[100];
    public void init() {
        fb = new FrameBuffer(Window.width(), Window.height());
        g = new Renderer("default", cam);
        Random r = new Random();
        float dis = 5;
        String[] modelName = new String[]{"bob","bus","mrkrab","pat","sandy"};
        for (int i = 0; i < 100; i++) {
            Entity entt = new Entity();
            entt.pos = new Vector3f(
                r.nextFloat(-dis, dis), r.nextFloat(-dis, dis),r.nextFloat(-dis, dis));
            entt.angleX = r.nextFloat(-180, 180);
            entt.angleY = r.nextFloat(-180, 180);
            entt.angleZ = r.nextFloat(-180, 180);
            entt.addComponent(k[i] = new Model(modelName[r.nextInt(modelName.length)],0,0,0,g));
        }
        crt = new Renderer("outline", cam);
        Entity entt = new Entity();
        entt.addComponent(new Model(fb.getTexturex(),0,0,-1,crt));
        entt = new Entity();
        tm = new TextMash("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz","arial");
        entt.addComponent(new Model(tm.getMash(),0,0,-20));

        fb.bind();
        g.render();
        fb.unbind();
    }
    TextMash tm;
    int Music = 0;
    String text = "";
    boolean lines = false;
    public void update(float dt) {
        fb.bind();
        g.render();
        crt.render();
        fb.unbind();
        g.render();
        crt.render();
        float x = 0;
        float y = 0;
        
        if(Input.getKeyPress("w")){
            x += 5.0f;
        }
        if(Input.getKeyPress("s")){
            x -= 5.0f;
        }
        if(Input.getKeyPress("a")){
            y += 5.0f;
        }
        if(Input.getKeyPress("d")){
            y -= 5.0f;
        }
        cam.getPos().add(cam.getLookDir().mul(x * dt,new Vector3f()));
        cam.setAngle(cam.getAngleX(), cam.getAngleY() + 90, cam.getAngleZ());
        cam.getPos().add(cam.getLookDir().mul(y * dt,new Vector3f()));
        cam.setAngle(cam.getAngleX(), cam.getAngleY() - 90, cam.getAngleZ());
        

        float angleX = 0;
        float angleY = 0;
        float angleZ = 0;

        if(Input.getKeyPress("q")){
            angleX += 90.0f;
        }
        if(Input.getKeyPress("e")){
            angleX -= 90.0f;
        }
        if(Input.getKeyPress("c")){
            angleY -= 90.0f;
        }
        if(Input.getKeyPress("v")){
            angleY += 90.0f;
        }
        if(Input.getKeyPress("k")){
            angleZ += 90.0f;
        }
        if(Input.getKeyPress("j")){
            angleZ -= 90.0f;
        }

        cam.setAngle(cam.getAngleX() + angleX * dt, cam.getAngleY() + angleY * dt,cam.getAngleZ() + angleZ * dt);

        if(Input.getKeyPress("r")){
            cam.setPerspective(cam.getFoV() + dt * 10);
        }
        if(Input.getKeyPress("t")){
            cam.setPerspective(cam.getFoV() - dt * 10);
        }

        if(Input.getKeyPressNow("y")){
            cam.setOrtho(10f);
        }

        if(Input.getKeyPressNow("i")){
            SoundMaster.playSound("2");
        }
        if(Input.getKeyPressNow("o")){
            SoundMaster.playSound("apple");
        }
        if(Input.getKeyPressNow("p")){
            Music = SoundMaster.playSound("BM");
        }
        if(Input.getKeyPressNow("0")){
            SoundMaster.stopSound(Music);
        }
        if(Input.getKeyPress("u")){
            tm.cangeText("time" + Window.time());
        }
        else{
            if(!tm.getText().equals(text)){
                tm.cangeText(text);
            }
        }
        if(Input.getKeysPressNow().length > 0){
            text += Input.getKeysPressNow()[0];
        }

        if(Input.getKeyPress("p")){
            lines = true;
            for (int i = 0; i < k.length; i++) {
                k[i].setModelShape(ModelShape.Lines);
            }
        }
        else if(lines == true){
            lines = false;
            for (int i = 0; i < k.length; i++) {
                k[i].setModelShape(ModelShape.triangles);
            }
        }
    }
}
