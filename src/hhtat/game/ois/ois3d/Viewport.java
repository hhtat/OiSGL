package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.math.Vector4;
import hhtat.game.ois.util.OiSU;

public class Viewport {
  private double xScale;
  private double yScale;
  private double zScale;

  private double xOffset;
  private double yOffset;
  private double zOffset;

  private Clipper clipper;
  private Rasterizer rasterizer;

  private Vector4 tmpA = new Vector4();
  private Vector4 tmpB = new Vector4();
  private Vector4 tmpC = new Vector4();

  private Vector3 tmpAColor = new Vector3();
  private Vector3 tmpBColor = new Vector3();
  private Vector3 tmpCColor = new Vector3();

  public Viewport( double width, double height, Clipper clipper, Rasterizer rasterizer ) {
    this.clipper = clipper;
    this.rasterizer = rasterizer;

    this.setViewport( 0.0, 0.0, width, height );
    this.setDepthRange( 0.0, 1.0 );
  }

  public void setViewport( double x, double y, double width, double height ) {
    double halfWidth = 0.5 * width;
    double halfHeight = 0.5 * height;

    this.xScale = halfWidth;
    this.yScale = halfHeight;

    this.xOffset = x + halfWidth;
    this.yOffset = y + halfHeight;
  }

  public void setDepthRange( double near, double far ) {
    this.zScale = 0.5 * ( far - near );
    this.zOffset = 0.5 * ( near + far );
  }

  public void drawPoint( Vector4 a, Vector3 aColor ) {
    double ax = a.x();
    double ay = a.y();
    double az = a.z();
    double aw = a.w();

    double awAbs = OiSU.abs( aw );

    if ( ( -awAbs <= ax ) && ( ax <= awAbs ) && ( -awAbs <= ay ) && ( ay <= awAbs ) && ( -awAbs <= az ) && ( az <= awAbs ) ) {
      a = this.tmpA.set( ax, ay, az, aw );

      // TODO when we start working with color (lighting, etc)
      // aColor = this.tmpAColor.set( aColor );

      a.divide( a.w() );

      // @formatter:off
      this.rasterizer.rasterizePoint( ( this.xScale * a.x() ) + this.xOffset,
                                      ( this.yScale * a.y() ) + this.yOffset,
                                      ( this.zScale * a.z() ) + this.zOffset,
                                      aColor.x(), aColor.y(), aColor.z() );
      // @formatter:on
    }
  }

  public void drawLine( Vector4 a, Vector3 aColor, Vector4 b, Vector3 bColor ) {
    a = this.tmpA.set( a );
    b = this.tmpB.set( b );

    aColor = this.tmpAColor.set( aColor );
    bColor = this.tmpBColor.set( bColor );

    if ( this.clipper.clipLine( a, aColor, b, bColor ) ) {
      a.divide( a.w() );
      b.divide( b.w() );

      // @formatter:off
      this.rasterizer.rasterizeLine( ( this.xScale * a.x() ) + this.xOffset,
                                     ( this.yScale * a.y() ) + this.yOffset,
                                     ( this.zScale * a.z() ) + this.zOffset,
                                     aColor.x(), aColor.y(), aColor.z(),
                                     ( this.xScale * b.x() ) + this.xOffset,
                                     ( this.yScale * b.y() ) + this.yOffset,
                                     ( this.zScale * b.z() ) + this.zOffset,
                                     bColor.x(), bColor.y(), bColor.z() );
      // @formatter:on
    }
  }

  public void drawTriangle( Vector4 a, Vector3 aColor, Vector4 b, Vector3 bColor, Vector4 c, Vector3 cColor ) {
    a = this.tmpA.set( a );
    b = this.tmpB.set( b );
    c = this.tmpC.set( c );

    aColor = this.tmpAColor.set( aColor );
    bColor = this.tmpBColor.set( bColor );
    cColor = this.tmpCColor.set( cColor );

    // TODO clip

    a.divide( a.w() );
    b.divide( b.w() );
    c.divide( c.w() );

    // @formatter:off
    this.rasterizer.rasterizeTriangle( ( this.xScale * a.x() ) + this.xOffset,
                                       ( this.yScale * a.y() ) + this.yOffset,
                                       ( this.zScale * a.z() ) + this.zOffset,
                                       aColor.x(), aColor.y(), aColor.z(),
                                       ( this.xScale * b.x() ) + this.xOffset,
                                       ( this.yScale * b.y() ) + this.yOffset,
                                       ( this.zScale * b.z() ) + this.zOffset,
                                       bColor.x(), bColor.y(), bColor.z(),
                                       ( this.xScale * c.x() ) + this.xOffset,
                                       ( this.yScale * c.y() ) + this.yOffset,
                                       ( this.zScale * c.z() ) + this.zOffset,
                                       cColor.x(), cColor.y(), cColor.z() );
    // @formatter:on
  }
}
