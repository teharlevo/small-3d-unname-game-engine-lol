package scenes;

import java.util.Random;

import main.Assets;
import main.Entity;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import Sound.SoundMaster;
import main.Input;
import main.Scene;
import main.Window;
import modeling.Mash;
import modeling.Model;
import modeling.ModelShape;
import render.FrameBuffer;
import render.Renderer;
import render.light.LightSource;
import render.light.Material;

public class TestScene extends Scene{

    public static void main(String[] args){
        Window.scenes = new Scene[1];
        Window.scenes[0] = new TestScene();
        new Window(900,600,"test",true);
    }

    Model k[] = new Model[10];
    Entity light;
    Renderer g;
    public void init() {
        Random r = new Random();
        int x = 10;
        int y = 10;
        float size = 800;
        float[] vertex = new float[8 * x * y * 6];
        float[] addShit = new float[]{0,0,1,0,1,1,1,1,0,1,0,0};
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int t = 0; t < 6; t++) {
                    int miniOffset = t * 2;
                    int offset = i * x * 8 * 6+ j * 8 * 6 + t * 8;
                    vertex[offset]     = addShit[miniOffset] + i;
                    vertex[offset + 1] = r.nextFloat(-0.1f, 0.1f);
                    vertex[offset + 2] = addShit[miniOffset + 1] + j;
                    vertex[offset + 3] = 0;
                    vertex[offset + 4] = 1;
                    vertex[offset + 5] = 0;
                    vertex[offset + 6] = (float)(addShit[miniOffset    ] + (i))/(float)(x); 
                    vertex[offset + 7] = (float)(addShit[miniOffset + 1] + (j))/(float)(y); 
                }
            }
        }
        Mash grund = new Mash(vertex, "4", new Material(null,256));
        Entity t = new Entity();
        t.addComponent(new Model(grund, 0,0,0));

        float dis = 5f;
        String[] modelName = new String[]{"tree","tree2","tree4"};
        for (int i = 0; i < modelName.length; i++) {
            Assets.getModelMesh(modelName[i]).getMaterial().setShininess(1);
        }
        for (int i = 0; i < k.length; i++) {
            Entity entt = new Entity();
            entt.setPos(
                r.nextFloat(-dis, dis),0,r.nextFloat(-dis, dis));
            entt.setAngle(r.nextFloat(-15,15),r.nextFloat(-180,180),r.nextFloat(-15,15));
            k[i] = new Model(modelName[r.nextInt(modelName.length)],0,0,0);
            k[i].getMash().getMaterial().setShininess(1);
            k[i].setScale(r.nextFloat(0.008f,0.012f) ,r.nextFloat(0.008f,0.012f)
            ,r.nextFloat(0.008f,0.012f) );
            //k[i].setColor(r.nextFloat(),r.nextFloat(),r.nextFloat(),r.nextFloat());
            entt.addComponent(k[i]);
        }
        Model colorModel = new Model("bob",0,0,0); 
        light = new Entity();
        light.addComponent(colorModel);
        LightSource lightSource = 
        new LightSource(new Vector3f(0f),new Vector3f(0.1f),new Vector3f(0.5f),new Vector3f(0.5f),32);
        getRenderer().addLightSource(lightSource);
        
        light.addComponent(lightSource);
    }
    
    int Music = 0;
    String text = "";
    boolean lines = false;

    public void update(float dt) {
        render();;
        float x = 0;
        float speed = 10.0f;
        
        if(Input.getMouseButtonPress(0)){
            x += speed;
        }
        if(Input.getMouseButtonPress(1)){
            x -= speed;
        }
        cam.getPos().add(cam.getLookDir().mul(x * dt,new Vector3f()));
        
        float angleX = cam.getAngleX();
        float angleY = cam.getAngleY();
        float angleSpeed = 1000.0f;
        Input.mouseToCenterOFscreen();
        angleX += (-(float)Input.getMousePosY() + (float)Window.height()/2)/(float)Window.height() * angleSpeed * dt;
        angleY += (((float)Input.getMousePosX() - (float)Window.width()/2)/(float)Window.width() ) * angleSpeed * dt;
        
        float limit = 80;
        if(angleX > limit){
            angleX = limit;
        }
        else if(angleX < -limit){
            angleX = -limit;
        }
        cam.setAngle(angleX,angleY,0);

        if(Input.getKeyPress("r")){
            cam.setPerspective(cam.getFoV() + dt * 10);
        }
        if(Input.getKeyPress("t")){
            cam.setPerspective(cam.getFoV() - dt * 10);
        }

        if(Input.getKeyPress("l")){
            if(Window.getCurrentScene().getRenderer().getShader() !=
            Assets.getShader("fog")){
                Window.getCurrentScene().getRenderer().setShader(Assets.getShader("fog"));
            }
        }
        else if(Window.getCurrentScene().getRenderer().getShader() !=
        Assets.getShader("default")){
                Window.getCurrentScene().getRenderer().setShader(Assets.getShader("default"));
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

        if(Input.getKeysPressNow().length > 0){
            text += Input.getKeysPressNow()[0];
        }

        if(Input.getKeyPress("p")){
            lines = true;
            for (int i = 0; i < k.length; i++) {
                k[i].getMash().setModelShape(ModelShape.Lines);
            }
        }
        else if(lines == true){
            lines = false;
            for (int i = 0; i < k.length; i++) {
                k[i].getMash().setModelShape(ModelShape.triangles);
            }
        }

        if(Input.getKeyPress("m")){
            FrameBuffer fb = new FrameBuffer(Window.width(), Window.height());
            fb.bind();
            render();
            fb.unbind();
            fb.getTexturex().saveImage("ScreenShot" + new Random().nextInt(9999999),"jpg");
        }

        float lx = light.getPos().x;
        float ly = light.getPos().y;
        float lz = light.getPos().z;
        float lightSpeed = 5;

        if(Input.getKeyPress("right")){
            lx += dt * lightSpeed;
        }
        if(Input.getKeyPress("left")){
            lx -= dt * lightSpeed;
        }
        if(Input.getKeyPress("up")){
            ly += dt * lightSpeed;
        }
        if(Input.getKeyPress("down")){
            ly -= dt * lightSpeed;
        }
        if(Input.getKeyPress("1")){
            lz += dt * lightSpeed;
        }
        if(Input.getKeyPress("2")){
            lz -= dt * lightSpeed;
        }
        light.setPos(lx,ly,lz);
    }
}