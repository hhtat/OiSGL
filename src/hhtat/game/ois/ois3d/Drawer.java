package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.util.VectorBank;

public class Drawer {
  private Transformation viewportTransform;

  private Rasterizer rasterizer;

  private VectorBank vectorBank;

  public Drawer( Transformation viewportTransform, Rasterizer rasterizer ) {
    this.viewportTransform = viewportTransform;
    this.rasterizer = rasterizer;

    this.vectorBank = new VectorBank();
    this.vectorBank.deposit( new Vector3() );
    this.vectorBank.deposit( new Vector3() );
    this.vectorBank.deposit( new Vector3() );
    this.vectorBank.deposit( new Vector3() );
    this.vectorBank.deposit( new Vector3() );
    this.vectorBank.deposit( new Vector3() );
  }

  public void drawPoint( Vector3 a, Vector3 aColor ) {
    a = this.vectorBank.withdrawVector3().set( a );
    aColor = this.vectorBank.withdrawVector3().set( aColor );

    // TODO clip

    this.viewportTransform.transform( a );

    this.rasterizer.rasterizePoint( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z() );

    // TODO move this before rasterization
    a = this.vectorBank.deposit( a );
    aColor = this.vectorBank.deposit( aColor );
  }

  public void drawLine( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor ) {
    a = this.vectorBank.withdrawVector3().set( a );
    b = this.vectorBank.withdrawVector3().set( b );

    aColor = this.vectorBank.withdrawVector3().set( aColor );
    bColor = this.vectorBank.withdrawVector3().set( bColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );

    System.out.println( a + "\t" + b );

    this.rasterizer.rasterizeLine( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z(), b.x(), b.y(), b.z(), bColor.x(), bColor.y(), bColor.z() );

    // TODO move this before rasterization
    a = this.vectorBank.deposit( a );
    b = this.vectorBank.deposit( b );

    aColor = this.vectorBank.deposit( aColor );
    bColor = this.vectorBank.deposit( bColor );
  }

  public void drawTriangle( Vector3 a, Vector3 aColor, Vector3 b, Vector3 bColor, Vector3 c, Vector3 cColor ) {
    a = this.vectorBank.withdrawVector3().set( a );
    b = this.vectorBank.withdrawVector3().set( b );
    c = this.vectorBank.withdrawVector3().set( c );

    aColor = this.vectorBank.withdrawVector3().set( aColor );
    bColor = this.vectorBank.withdrawVector3().set( bColor );
    cColor = this.vectorBank.withdrawVector3().set( cColor );

    // TODO clip

    this.viewportTransform.transform( a );
    this.viewportTransform.transform( b );
    this.viewportTransform.transform( c );

    this.rasterizer.rasterizeTriangle( a.x(), a.y(), a.z(), aColor.x(), aColor.y(), aColor.z(), b.x(), b.y(), b.z(), bColor.x(), bColor.y(), bColor.z(), c.x(),
        c.y(), c.z(), cColor.x(), cColor.y(), cColor.z() );

    // TODO move this before rasterization
    a = this.vectorBank.deposit( a );
    b = this.vectorBank.deposit( b );
    c = this.vectorBank.deposit( c );

    aColor = this.vectorBank.deposit( aColor );
    bColor = this.vectorBank.deposit( bColor );
    cColor = this.vectorBank.deposit( cColor );
  }
}
