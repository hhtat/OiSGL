import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.ois3d.OiSGL;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test2OiSGL {
  public static void main( String[] args ) throws InterruptedException {
    int width = 640;
    int height = 480;

    double aspect = (double) width / (double) height;

    JFrame frame = new JFrame( "OiSGL" );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    JPanel panel = new JPanel( true );
    panel.setPreferredSize( new Dimension( width, height ) );

    frame.getContentPane().add( panel );

    frame.pack();
    frame.setVisible( true );

    OiSGL gl = new OiSGL( width, height );

    gl.glViewportAndDepthRange( 0, 0, width, height, 0.0, 1.0 );

    gl.glMatrixMode( OiSGL.GL_PROJECTION );
    gl.glLoadIdentity();
    // gl.glOrtho( aspect * -10.0, aspect * 10.0, -10.0, 10.0, -10.0, 10.0 );
    // gl.glFrustum( aspect * -10.0, aspect * 10.0, -10.0, 10.0, 50.0, 500.0 );
    gl.gluPerspective( 40.0, aspect, 10.0, 100.0 );

    double degrees = 0.0;

    int framesSinceLastReport = 0;
    long lastReportTimeMillis = System.currentTimeMillis();

    List< Vector3 > vectors = new LinkedList< Vector3 >();

    for ( int i = 0; i < 10000; i++ ) {
      vectors.add( new Vector3( Math.random(), Math.random(), Math.random() ) );
    }

    while ( true ) {
      gl.glClear( OiSGL.GL_COLOR_BUFFER_BIT | OiSGL.GL_DEPTH_BUFFER_BIT );

      gl.glMatrixMode( OiSGL.GL_MODELVIEW );
      gl.glLoadIdentity();
      gl.glTranslate( 0.0, 0.0, -50.0 );

      gl.glRotate( 0.2 * degrees, 1.0, 0.0, 0.0 );
      gl.glRotate( 0.4 * degrees, 0.0, 1.0, 0.0 );
      gl.glRotate( 0.8 * degrees, 0.0, 0.0, 1.0 );
      gl.glTranslate( -5.0, -5.0, -5.0 );

      degrees += 1.0;

      gl.glBegin( OiSGL.GL_LINES );

      for ( Vector3 vector : vectors ) {
        gl.glColor( vector.x(), vector.y(), vector.z() );
        gl.glVertex( 10.0 * vector.x(), 10.0 * vector.y(), 10.0 * vector.z() );
      }

      gl.glEnd();

      gl.glBegin( OiSGL.GL_LINES );

      gl.glColor( 1.0, 0.0, 0.0 );
      gl.glVertex( 0.0, 0.0, 0.0 );
      gl.glVertex( 20.0, 0.0, 0.0 );

      gl.glColor( 0.0, 1.0, 0.0 );
      gl.glVertex( 0.0, 0.0, 0.0 );
      gl.glVertex( 0.0, 20.0, 0.0 );

      gl.glColor( 0.0, 0.0, 1.0 );
      gl.glVertex( 0.0, 0.0, 0.0 );
      gl.glVertex( 0.0, 0.0, 20.0 );

      gl.glEnd();

      Graphics g = panel.getGraphics();
      g.drawImage( gl.getImage(), 0, 0, null );
      g.dispose();

      if ( ++framesSinceLastReport == 100 ) {
        long currentTimeMillis = System.currentTimeMillis();

        System.out.println( ( ( 1000.0 * framesSinceLastReport ) / ( currentTimeMillis - lastReportTimeMillis ) ) + " fps" );

        framesSinceLastReport = 0;
        lastReportTimeMillis = currentTimeMillis;
      }
    }
  }
}
