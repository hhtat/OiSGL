package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.util.OiSBank;

public class Drawer {
  private Transformation viewportTransform;

  private Rasterizer rasterizer;

  public Drawer( Transformation viewportTransform, Rasterizer rasterizer ) {
    this.viewportTransform = viewportTransform;
    this.rasterizer = rasterizer;

  }

  public void drawPoint( Vector3 a, Vector3 aColor ) {
    a = OiSBank.withdrawVector3().set( a );
    aColor = OiSBank.withdrawVector3().set( aColor );

    // TODO clip

    this.viewportTransform.transform( a );

    this.rasterizer.rasterizePoint( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z() );

    // TODO move this before rasterization
    a = OiSBank.deposit( a );
    aColor = OiSBank.deposit( aColor );
  }

  public void drawLine( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor ) {
    a = OiSBank.withdrawVector3().set( a );
    b = OiSBank.withdrawVector3().set( b );

    aColor = OiSBank.withdrawVector3().set( aColor );
    bColor = OiSBank.withdrawVector3().set( bColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );

    this.rasterizer.rasterizeLine( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z(), b.x(), b.y(), b.z(), bColor.x(), bColor.y(), bColor.z() );

    // TODO move this before rasterization
    a = OiSBank.deposit( a );
    b = OiSBank.deposit( b );

    aColor = OiSBank.deposit( aColor );
    bColor = OiSBank.deposit( bColor );
  }

  public void drawTriangle( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor, Vector3 c, Vector3 cColor ) {
    a = OiSBank.withdrawVector3().set( a );
    b = OiSBank.withdrawVector3().set( b );
    c = OiSBank.withdrawVector3().set( c );

    aColor = OiSBank.withdrawVector3().set( aColor );
    bColor = OiSBank.withdrawVector3().set( bColor );
    cColor = OiSBank.withdrawVector3().set( cColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );
    this.viewportTransform.transform( c );

    this.rasterizer.rasterizeTriangle( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z(), b.x(), b.y(), b.z(), bColor.x(), bColor.y(), bColor.z(), c.x(),
        c.y(), c.z(), cColor.x(), cColor.y(), cColor.z() );

    // TODO move this before rasterization
    a = OiSBank.deposit( a );
    b = OiSBank.deposit( b );
    c = OiSBank.deposit( c );

    aColor = OiSBank.deposit( aColor );
    bColor = OiSBank.deposit( bColor );
    cColor = OiSBank.deposit( cColor );
  }
}
