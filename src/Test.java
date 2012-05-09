import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.ois3d.Transformation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test {
  private static void drawLine( Graphics g, Vector3 a, Vector3 b, Transformation viewport, Transformation projection, Transformation modelView ) {
    Vector3 aScreen = viewport.transform( projection.transform( modelView.transform( a.duplicate() ) ) );
    Vector3 bScreen = viewport.transform( projection.transform( modelView.transform( b.duplicate() ) ) );

    g.drawLine( (int) Math.round( aScreen.x() ), (int) Math.round( aScreen.y() ), (int) Math.round( bScreen.x() ), (int) Math.round( bScreen.y() ) );
  }

  public static void main( String[] args ) throws InterruptedException {
    int width = 640;
    int height = 480;

    double aspect = (double) width / (double) height;

    JFrame frame = new JFrame( "Test" );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    JPanel panel = new JPanel( true );
    panel.setPreferredSize( new Dimension( width, height ) );

    frame.getContentPane().add( panel );

    frame.pack();
    frame.setVisible( true );

    BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );

    Graphics g = image.getGraphics();

    Transformation viewport = new Transformation();
    Transformation projection = new Transformation();
    Transformation modelView = new Transformation();

    viewport.viewport( 0.0, height, width, -height, 0.0, 1.0 );
    // projection.orthographic( aspect * -10.0, aspect * 10.0, -10.0, 10.0,
    // -10.0, 10.0 );
    projection.frustum( aspect * -10.0, aspect * 10.0, -10.0, 10.0, 50.0, 500.0 );
    // projection.perspective( 45.0, aspect, 50.0, 500.0 );

    double degrees = 0.0;

    while ( true ) {
      g.clearRect( 0, 0, width, height );

      modelView.loadIdentity();
      modelView.translate( 0.0, 0.0, -100.0 );
      modelView.rotate( ( Math.PI / 180.0 ) * degrees, 1.0, 1.0, 1.0 );

      degrees += 1.0;

      Test.drawLine( g, new Vector3( 0.0, 0.0, 0.0 ), new Vector3( 1.0, 0.0, 0.0 ), viewport, projection, modelView );
      Test.drawLine( g, new Vector3( 0.0, 0.0, 0.0 ), new Vector3( 0.0, 10.0, 0.0 ), viewport, projection, modelView );
      Test.drawLine( g, new Vector3( 0.0, 0.0, 0.0 ), new Vector3( 0.0, 0.0, 10.0 ), viewport, projection, modelView );

      Graphics panelG = panel.getGraphics();
      panelG.drawImage( image, 0, 0, null );
      panelG.dispose();

      Thread.sleep( 20 );
    }
  }
}
