package hhtat.game.ois.test;

import hhtat.game.ois.ois3d.OiSGL;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test4OiSGL {
  public static void main( String[] args ) throws InterruptedException {
    int width = 640;
    int height = 480;

    JFrame frame = new JFrame( "OiSGL" );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    JPanel panel = new JPanel( true );
    panel.setPreferredSize( new Dimension( width, height ) );

    frame.getContentPane().add( panel );

    frame.pack();
    frame.setVisible( true );

    OiSGL gl = new OiSGL( width, height );

    gl.glViewport( 0, 0, width, height );

    gl.glMatrixMode( OiSGL.GL_PROJECTION );
    gl.glLoadIdentity();
    gl.gluOrtho2D( 0.0, width, 0.0, height );

    int framesSinceLastReport = 0;
    long lastReportTimeMillis = System.currentTimeMillis();

    int state = 0;

    while ( true ) {
      gl.glClear( OiSGL.GL_COLOR_BUFFER_BIT | OiSGL.GL_DEPTH_BUFFER_BIT );

      gl.glMatrixMode( OiSGL.GL_MODELVIEW );
      gl.glLoadIdentity();

      gl.glColor( 0.0, 1.0, 0.0 );

      switch ( state ) {
        case 0:
          gl.glBegin( OiSGL.GL_LINES );

          gl.glVertex( 0, 0 );
          gl.glVertex( 0, height - 1 );

          gl.glVertex( 0, height - 1 );
          gl.glVertex( width - 1, height - 1 );

          gl.glVertex( width - 1, height - 1 );
          gl.glVertex( width - 1, 0 );

          gl.glVertex( width - 1, 0 );
          gl.glVertex( 0, 0 );

          gl.glEnd();
          break;
        case 1:
          gl.glBegin( OiSGL.GL_LINES );

          for ( int x = 0; x < width; x += 2 ) {
            gl.glVertex( x, 0 );
            gl.glVertex( x, height - 1 );
          }

          gl.glEnd();
          break;
        case 2:
          gl.glBegin( OiSGL.GL_LINES );

          for ( int y = 0; y < height; y += 2 ) {
            gl.glVertex( 0, y );
            gl.glVertex( width - 1, y );
          }

          gl.glEnd();
          break;
        default:
          state = 0;
          continue;
      }

      state++;

      Graphics g = panel.getGraphics();
      g.drawImage( gl.getImage(), 0, 0, null );
      g.dispose();

      Thread.sleep( 1000 );

      if ( ++framesSinceLastReport == 100 ) {
        long currentTimeMillis = System.currentTimeMillis();

        System.out.println( ( ( 1000.0 * framesSinceLastReport ) / ( currentTimeMillis - lastReportTimeMillis ) ) + " fps" );

        framesSinceLastReport = 0;
        lastReportTimeMillis = currentTimeMillis;
      }
    }
  }
}
