package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Matrix4;

public class Transformation extends hhtat.game.ois.math.Transformation {
  public Transformation() {
    super();
  }

  public Transformation( Matrix4 transformation ) {
    super( transformation );
  }

  public Transformation duplicate() {
    return new Transformation( this );
  }

  public Transformation loadIdentity() {
    return (Transformation) super.setIdentity();
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
}
