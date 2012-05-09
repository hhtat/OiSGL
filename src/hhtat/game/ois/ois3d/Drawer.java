package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;

public class Drawer {
  private Transformation viewportTransform;

  private Rasterizer rasterizer;

  private Vector3 tv3a1;
  private Vector3 tv3a2;
  private Vector3 tv3b1;
  private Vector3 tv3b2;
  private Vector3 tv3c1;
  private Vector3 tv3c2;

  public Drawer( Transformation viewportTransform, Rasterizer rasterizer ) {
    this.viewportTransform = viewportTransform;
    this.rasterizer = rasterizer;

    this.tv3a1 = new Vector3();
    this.tv3a2 = new Vector3();
    this.tv3b1 = new Vector3();
    this.tv3b2 = new Vector3();
    this.tv3c1 = new Vector3();
    this.tv3c2 = new Vector3();
  }

  public void drawPoint( Vector3 a, Vector3 aColor ) {
    a = this.tv3a1.set( a );
    aColor = this.tv3a2.set( aColor );

    // TODO clip

    this.viewportTransform.transform( a );

    this.rasterizer.rasterizePoint( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z() );
  }

  public void drawLine( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor ) {
    a = this.tv3a1.set( a );
    b = this.tv3b1.set( b );

    aColor = this.tv3a2.set( aColor );
    bColor = this.tv3b2.set( bColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );

    this.rasterizer.rasterizeLine( a.x(), a.y(), a.z(), b.x(), b.y(), b.z(), aColor.x(), aColor.y(), aColor.z(), bColor.x(), bColor.y(), bColor.z() );
  }

  public void drawTriangle( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor, Vector3 c, Vector3 cColor ) {
    this.drawLine( a, aColor, b, bColor );
    this.drawLine( b, bColor, c, cColor );
    this.drawLine( c, cColor, a, aColor );
  }
}
