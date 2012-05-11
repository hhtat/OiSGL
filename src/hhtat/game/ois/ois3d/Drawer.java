package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;

public class Drawer {
  private Transformation viewportTransform;

  private Rasterizer rasterizer;

  private Vector3 tmpA;
  private Vector3 tmpB;
  private Vector3 tmpC;

  private Vector3 tmpAColor;
  private Vector3 tmpBColor;
  private Vector3 tmpCColor;

  public Drawer( Transformation viewportTransform, Rasterizer rasterizer ) {
    this.viewportTransform = viewportTransform;
    this.rasterizer = rasterizer;

    this.tmpA = new Vector3();
    this.tmpB = new Vector3();
    this.tmpC = new Vector3();

    this.tmpAColor = new Vector3();
    this.tmpBColor = new Vector3();
    this.tmpCColor = new Vector3();
  }

  public void drawPoint( Vector3 a, Vector3 aColor ) {
    a = this.tmpA.set( a );
    aColor = this.tmpAColor.set( aColor );

    // TODO clip

    this.viewportTransform.transform( a );

    this.rasterizer.rasterizePoint( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z() );
  }

  public void drawLine( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor ) {
    a = this.tmpA.set( a );
    b = this.tmpB.set( b );

    aColor = this.tmpAColor.set( aColor );
    bColor = this.tmpBColor.set( bColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );

    this.rasterizer.rasterizeLine( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z(), b.x(), b.y(), b.z(), bColor.x(), bColor.y(), bColor.z() );
  }

  public void drawTriangle( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor, Vector3 c, Vector3 cColor ) {
    a = this.tmpA.set( a );
    b = this.tmpB.set( b );
    c = this.tmpC.set( c );

    aColor = this.tmpAColor.set( aColor );
    bColor = this.tmpBColor.set( bColor );
    cColor = this.tmpCColor.set( cColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );
    this.viewportTransform.transform( c );

    this.rasterizer.rasterizeTriangle( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z(), b.x(), b.y(), b.z(), bColor.x(), bColor.y(), bColor.z(), c.x(),
        c.y(), c.z(), cColor.x(), cColor.y(), cColor.z() );
  }
}
