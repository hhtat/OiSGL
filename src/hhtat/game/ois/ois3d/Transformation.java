package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Matrix4;
import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.math.Vector4;
import hhtat.game.ois.util.VectorBank;

public class Transformation extends Matrix4 {
  private VectorBank vectorBank;

  public Transformation() {
    this( new Matrix4( 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0 ) );
  }

  public Transformation( Matrix4 transformation ) {
    super( transformation );

    this.vectorBank = new VectorBank();
    this.vectorBank.deposit( new Vector3() );
    this.vectorBank.deposit( new Vector4() );
  }

  public Transformation duplicate() {
    return new Transformation( this );
  }

  public Transformation loadIdentity() {
    return (Transformation) this.setIdentity();
  }

  public Transformation rotate( double theta, double x, double y, double z ) {
    Vector3 temp = this.vectorBank.withdrawVector3();

    temp.set( x, y, z ).normalize();

    x = temp.x();
    y = temp.y();
    z = temp.z();

    temp = this.vectorBank.deposit( temp );

    double cos = Math.cos( theta );
    double sin = Math.sin( theta );

    double cosR = 1.0 - cos;

    double xx = x * x;
    double xy = x * y;
    double xz = x * z;
    double yy = y * y;
    double yz = y * z;
    double zz = z * z;

    double xSin = x * sin;
    double ySin = y * sin;
    double zSin = z * sin;

    // @formatter:off
    return (Transformation) this.multiply(
        ( xx * cosR ) + cos,      ( xy * cosR ) - zSin,     ( xz * cosR ) + ySin,     0.0,
        ( xy * cosR ) + zSin,     ( yy * cosR ) + cos,      ( yz * cosR ) - xSin,     0.0,
        ( xz * cosR ) - ySin,     ( yz * cosR ) + xSin,     ( zz * cosR ) + cos,      0.0,
        0.0,                  0.0,                  0.0,                  1.0 );
    // @formatter:on
  }

  public Transformation scale( double x, double y, double z ) {
    // @formatter:off
    return (Transformation) this.multiply(
        x,    0.0,  0.0,  0.0,
        0.0,  y,    0.0,  0.0,
        0.0,  0.0,  z,    0.0,
        0.0,  0.0,  0.0,  1.0 );
    // @formatter:on
  }

  public Transformation translate( double x, double y, double z ) {
    // @formatter:off
    return (Transformation) this.multiply(
        1.0, 0.0, 0.0, x,
        0.0, 1.0, 0.0, y,
        0.0, 0.0, 1.0, z,
        0.0, 0.0, 0.0, 1.0 );
    // @formatter:on
  }

  public Transformation frustum( double left, double right, double bottom, double top, double near, double far ) {
    double rightMinusLeftInv = 1.0 / ( right - left );
    double topMinusBottomInv = 1.0 / ( top - bottom );
    double farMinusNearInv = 1.0 / ( far - near );

    // @formatter:off
    return (Transformation) this.multiply(
        2.0 * near * rightMinusLeftInv,     0.0,                                ( right + left ) * rightMinusLeftInv,     0.0,
        0.0,                                2.0 * near * topMinusBottomInv,     ( top + bottom ) * topMinusBottomInv,     0.0,
        0.0,                                0.0,                                -( far + near ) * farMinusNearInv,        -2.0 * far * near * farMinusNearInv,
        0.0,                                0.0,                                -1.0,                                     0.0 );
    // @formatter:on
  }

  public Transformation orthographic( double left, double right, double bottom, double top, double near, double far ) {
    double rightMinusLeftInv = 1.0 / ( right - left );
    double topMinusBottomInv = 1.0 / ( top - bottom );
    double farMinusNearInv = 1.0 / ( far - near );

    // @formatter:off
    return (Transformation) this.multiply(
        2.0 * rightMinusLeftInv,    0.0,                        0.0,                        -( right + left ) * rightMinusLeftInv,
        0.0,                        2.0 * topMinusBottomInv,    0.0,                        -( top + bottom ) * topMinusBottomInv,
        0.0,                        0.0,                        -2.0 * farMinusNearInv,     -( far + near ) * farMinusNearInv,
        0.0,                        0.0,                        0.0,                        1.0 );
    // @formatter:on
  }

  public Transformation viewport( double x, double y, double width, double height, double near, double far ) {
    double halfWidth = 0.5 * width;
    double halfHeight = 0.5 * height;

    // @formatter:off
    return (Transformation) this.multiply(
        halfWidth,    0.0,            0.0,                      x + halfWidth,
        0.0,          halfHeight,     0.0,                      y + halfHeight,
        0.0,          0.0,            0.5 * ( far - near ),     0.5 * ( near + far ),
        0.0,          0.0,            0.0,                      1.0 );
    // @formatter:on
  }

  public Vector4 transform( Vector4 vector ) {
    return this.multiply( vector );
  }

  public Vector3 transform( Vector3 vector ) {
    Vector4 temp = this.vectorBank.withdrawVector4();

    this.multiply( temp.set( vector.x(), vector.y(), vector.z(), 1.0 ) );

    double x = temp.x();
    double y = temp.y();
    double z = temp.z();
    double w = temp.w();

    temp = this.vectorBank.deposit( temp );

    if ( w > 0.0 ) {
      return vector.set( x / w, y / w, z / w );
    } else if ( w < 0.0 ) {
      // TODO Fix
      return vector.set( x / w, y / w, z / w );
    } else {
      // TODO Fix
      return vector.set( x / w, y / w, z / w );
    }
  }
}
