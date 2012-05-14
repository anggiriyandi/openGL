package org.yourorghere;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/**
 * OpenGL.java <BR> author: Brian Paul (converted to Java by Ron Cemer and Sven
 * Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class OpenGL implements GLEventListener {

    private GLU glu;
    private Texture tex;
    private float atas, bawah, kiri, kanan;
    private float rot = 0;
    

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new OpenGL());
        frame.add(canvas);
        frame.setSize(640, 480);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        gl.glEnable(GL.GL_DEPTH_TEST);

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
        glu = new GLU();

        try {
            InputStream stream = getClass().getResourceAsStream("gmbr.jpg");
            TextureData data = TextureIO.newTextureData(stream, false, "jpg");
            tex = TextureIO.newTexture(data);

            TextureCoords textureCoords = tex.getImageTexCoords();
            atas = textureCoords.top();
            bawah = textureCoords.bottom();
            kiri = textureCoords.left();
            kanan = textureCoords.right();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        rot = rot+10;
        
        
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        GLUquadric gLUquadric = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(gLUquadric, GLU.GLU_FILL);
        glu.gluQuadricTexture(gLUquadric, true);
        tex.enable();
        tex.bind();
        
        
        gl.glTranslatef(-0.5f, 0.0f, -10.0f);
        gl.glRotatef(rot,rot,rot,rot);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(kiri, bawah);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(kanan, bawah);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glEnd();
        
          gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(kiri, bawah);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(kanan, bawah);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        
          gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(kiri, bawah);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(kanan, bawah);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glEnd();
        
             gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(kiri, bawah);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(kanan, bawah);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glEnd();
        
           gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(kiri, bawah);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(kanan, bawah);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glEnd();
        
             gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(kiri, bawah);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(kanan, bawah);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(kiri, atas);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glEnd();
        
        
        
       

        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
