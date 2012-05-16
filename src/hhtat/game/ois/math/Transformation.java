package hhtat.game.ois.math;

public class Transformation extends Matrix4 {
  private Vector3 tmpVector3 = new Vector3();
  private Vector4 tmpVector4 = new Vector4();

  public Transformation() {
    this.setIdentity();
  }

  public Transformation( Matrix4 transformation ) {
    this.set( transformation );
  }

  public Transformation duplicate() {
    return new Transformation( this );
  }

  public Transformation rotate( double theta, double x, double y, double z ) {
    this.tmpVector3.set( x, y, z ).normalize();

    x = this.tmpVector3.x();
    y = this.tmpVector3.y();
    z = this.tmpVector3.z();

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

  public Vector3 transform( Vector3 vector ) {
    return vector.set( this.transform( this.tmpVector4.set( vector, 1.0 ) ).divide( this.tmpVector4.w ) );
  }

  public Vector4 transform( Vector4 vector ) {
    return this.multiply( vector );
  }
}
