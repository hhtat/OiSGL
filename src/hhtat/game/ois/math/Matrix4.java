package hhtat.game.ois.math;

public class Matrix4 {
  protected double e11, e12, e13, e14;
  protected double e21, e22, e23, e24;
  protected double e31, e32, e33, e34;
  protected double e41, e42, e43, e44;

  public Matrix4() {
    // @formatter:off
    this( 0.0, 0.0, 0.0, 0.0,
          0.0, 0.0, 0.0, 0.0,
          0.0, 0.0, 0.0, 0.0,
          0.0, 0.0, 0.0, 0.0 );
    // @formatter:on
  }

  public Matrix4( double e11, double e12, double e13, double e14, double e21, double e22, double e23, double e24, double e31, double e32, double e33,
      double e34, double e41, double e42, double e43, double e44 ) {
    this.e11 = e11;
    this.e12 = e12;
    this.e13 = e13;
    this.e14 = e14;
    this.e21 = e21;
    this.e22 = e22;
    this.e23 = e23;
    this.e24 = e24;
    this.e31 = e31;
    this.e32 = e32;
    this.e33 = e33;
    this.e34 = e34;
    this.e41 = e41;
    this.e42 = e42;
    this.e43 = e43;
    this.e44 = e44;
  }

  public Matrix4( Matrix4 matrix ) {
    // @formatter:off
    this( matrix.e11, matrix.e12, matrix.e13, matrix.e14,
          matrix.e21, matrix.e22, matrix.e23, matrix.e24,
          matrix.e31, matrix.e32, matrix.e33, matrix.e34,
          matrix.e41, matrix.e42, matrix.e43, matrix.e44 );
    // @formatter:on
  }

  public Matrix4 multiply( double scalar ) {
    this.e11 *= scalar;
    this.e12 *= scalar;
    this.e13 *= scalar;
    this.e14 *= scalar;
    this.e21 *= scalar;
    this.e22 *= scalar;
    this.e23 *= scalar;
    this.e24 *= scalar;
    this.e31 *= scalar;
    this.e32 *= scalar;
    this.e33 *= scalar;
    this.e34 *= scalar;
    this.e41 *= scalar;
    this.e42 *= scalar;
    this.e43 *= scalar;
    this.e44 *= scalar;

    return this;
  }

  public Matrix4 divide( double scalar ) {
    if ( scalar == 0.0 ) {
      throw new ArithmeticException( "cannot divide by zero" );
    }

    return this.multiply( 1.0 / scalar );
  }

  public Vector4 multiply( Vector4 vector ) {
    // @formatter:off
    return vector.set( ( this.e11 * vector.x ) + ( this.e12 * vector.y ) + ( this.e13 * vector.z ) + ( this.e14 * vector.w ),
                       ( this.e21 * vector.x ) + ( this.e22 * vector.y ) + ( this.e23 * vector.z ) + ( this.e24 * vector.w ),
                       ( this.e31 * vector.x ) + ( this.e32 * vector.y ) + ( this.e33 * vector.z ) + ( this.e34 * vector.w ),
                       ( this.e41 * vector.x ) + ( this.e42 * vector.y ) + ( this.e43 * vector.z ) + ( this.e44 * vector.w ) );
    // @formatter:on
  }

  public Matrix4 add( double e11, double e12, double e13, double e14, double e21, double e22, double e23, double e24, double e31, double e32, double e33,
      double e34, double e41, double e42, double e43, double e44 ) {
    this.e11 += e11;
    this.e12 += e12;
    this.e13 += e13;
    this.e14 += e14;
    this.e21 += e21;
    this.e22 += e22;
    this.e23 += e23;
    this.e24 += e24;
    this.e31 += e31;
    this.e32 += e32;
    this.e33 += e33;
    this.e34 += e34;
    this.e41 += e41;
    this.e42 += e42;
    this.e43 += e43;
    this.e44 += e44;

    return this;
  }

  public Matrix4 add( Matrix4 rhs ) {
    // @formatter:off
    return this.add( rhs.e11, rhs.e12, rhs.e13, rhs.e14,
                     rhs.e21, rhs.e22, rhs.e23, rhs.e24,
                     rhs.e31, rhs.e32, rhs.e33, rhs.e34,
                     rhs.e41, rhs.e42, rhs.e43, rhs.e44 );
    // @formatter:on
  }

  public Matrix4 subtract( Matrix4 rhs ) {
    // @formatter:off
    return this.add( -rhs.e11, -rhs.e12, -rhs.e13, -rhs.e14,
                     -rhs.e21, -rhs.e22, -rhs.e23, -rhs.e24,
                     -rhs.e31, -rhs.e32, -rhs.e33, -rhs.e34,
                     -rhs.e41, -rhs.e42, -rhs.e43, -rhs.e44 );
    // @formatter:on
  }

  public Matrix4 multiply( double e11, double e12, double e13, double e14, double e21, double e22, double e23, double e24, double e31, double e32, double e33,
      double e34, double e41, double e42, double e43, double e44 ) {
    // @formatter:off
    return this.set( ( this.e11 * e11 ) + ( this.e12 * e21 ) + ( this.e13 * e31 ) + ( this.e14 * e41 ),
                     ( this.e11 * e12 ) + ( this.e12 * e22 ) + ( this.e13 * e32 ) + ( this.e14 * e42 ),
                     ( this.e11 * e13 ) + ( this.e12 * e23 ) + ( this.e13 * e33 ) + ( this.e14 * e43 ),
                     ( this.e11 * e14 ) + ( this.e12 * e24 ) + ( this.e13 * e34 ) + ( this.e14 * e44 ),
                     ( this.e21 * e11 ) + ( this.e22 * e21 ) + ( this.e23 * e31 ) + ( this.e24 * e41 ),
                     ( this.e21 * e12 ) + ( this.e22 * e22 ) + ( this.e23 * e32 ) + ( this.e24 * e42 ),
                     ( this.e21 * e13 ) + ( this.e22 * e23 ) + ( this.e23 * e33 ) + ( this.e24 * e43 ),
                     ( this.e21 * e14 ) + ( this.e22 * e24 ) + ( this.e23 * e34 ) + ( this.e24 * e44 ),
                     ( this.e31 * e11 ) + ( this.e32 * e21 ) + ( this.e33 * e31 ) + ( this.e34 * e41 ),
                     ( this.e31 * e12 ) + ( this.e32 * e22 ) + ( this.e33 * e32 ) + ( this.e34 * e42 ),
                     ( this.e31 * e13 ) + ( this.e32 * e23 ) + ( this.e33 * e33 ) + ( this.e34 * e43 ),
                     ( this.e31 * e14 ) + ( this.e32 * e24 ) + ( this.e33 * e34 ) + ( this.e34 * e44 ),
                     ( this.e41 * e11 ) + ( this.e42 * e21 ) + ( this.e43 * e31 ) + ( this.e44 * e41 ),
                     ( this.e41 * e12 ) + ( this.e42 * e22 ) + ( this.e43 * e32 ) + ( this.e44 * e42 ),
                     ( this.e41 * e13 ) + ( this.e42 * e23 ) + ( this.e43 * e33 ) + ( this.e44 * e43 ),
                     ( this.e41 * e14 ) + ( this.e42 * e24 ) + ( this.e43 * e34 ) + ( this.e44 * e44 ) );
    // @formatter:on
  }

  public Matrix4 multiply( Matrix4 rhs ) {
    // @formatter:off
    return this.multiply( rhs.e11, rhs.e12, rhs.e13, rhs.e14,
                          rhs.e21, rhs.e22, rhs.e23, rhs.e24,
                          rhs.e31, rhs.e32, rhs.e33, rhs.e34,
                          rhs.e41, rhs.e42, rhs.e43, rhs.e44 );
    // @formatter:on
  }

  public Matrix4 transpose() {
    // @formatter:off
    return this.set( this.e11, this.e21, this.e31, this.e41,
                     this.e12, this.e22, this.e32, this.e42,
                     this.e13, this.e23, this.e33, this.e43,
                     this.e14, this.e24, this.e34, this.e44 );
    // @formatter:on
  }

  public Matrix4 invert() {
    double e11 = ( ( ( this.e22 * this.e33 * this.e44 ) - ( this.e22 * this.e34 * this.e43 ) - ( this.e32 * this.e23 * this.e44 ) )
        + ( this.e32 * this.e24 * this.e43 ) + ( this.e42 * this.e23 * this.e34 ) )
        - ( this.e42 * this.e24 * this.e33 );
    double e12 = ( ( ( -this.e12 * this.e33 * this.e44 ) + ( this.e12 * this.e34 * this.e43 ) + ( this.e32 * this.e13 * this.e44 ) )
        - ( this.e32 * this.e14 * this.e43 ) - ( this.e42 * this.e13 * this.e34 ) )
        + ( this.e42 * this.e14 * this.e33 );
    double e13 = ( ( ( this.e12 * this.e23 * this.e44 ) - ( this.e12 * this.e24 * this.e43 ) - ( this.e22 * this.e13 * this.e44 ) )
        + ( this.e22 * this.e14 * this.e43 ) + ( this.e42 * this.e13 * this.e24 ) )
        - ( this.e42 * this.e14 * this.e23 );
    double e14 = ( ( ( -this.e12 * this.e23 * this.e34 ) + ( this.e12 * this.e24 * this.e33 ) + ( this.e22 * this.e13 * this.e34 ) )
        - ( this.e22 * this.e14 * this.e33 ) - ( this.e32 * this.e13 * this.e24 ) )
        + ( this.e32 * this.e14 * this.e23 );
    double e21 = ( ( ( -this.e21 * this.e33 * this.e44 ) + ( this.e21 * this.e34 * this.e43 ) + ( this.e31 * this.e23 * this.e44 ) )
        - ( this.e31 * this.e24 * this.e43 ) - ( this.e41 * this.e23 * this.e34 ) )
        + ( this.e41 * this.e24 * this.e33 );
    double e22 = ( ( ( this.e11 * this.e33 * this.e44 ) - ( this.e11 * this.e34 * this.e43 ) - ( this.e31 * this.e13 * this.e44 ) )
        + ( this.e31 * this.e14 * this.e43 ) + ( this.e41 * this.e13 * this.e34 ) )
        - ( this.e41 * this.e14 * this.e33 );
    double e23 = ( ( ( -this.e11 * this.e23 * this.e44 ) + ( this.e11 * this.e24 * this.e43 ) + ( this.e21 * this.e13 * this.e44 ) )
        - ( this.e21 * this.e14 * this.e43 ) - ( this.e41 * this.e13 * this.e24 ) )
        + ( this.e41 * this.e14 * this.e23 );
    double e24 = ( ( ( this.e11 * this.e23 * this.e34 ) - ( this.e11 * this.e24 * this.e33 ) - ( this.e21 * this.e13 * this.e34 ) )
        + ( this.e21 * this.e14 * this.e33 ) + ( this.e31 * this.e13 * this.e24 ) )
        - ( this.e31 * this.e14 * this.e23 );
    double e31 = ( ( ( this.e21 * this.e32 * this.e44 ) - ( this.e21 * this.e34 * this.e42 ) - ( this.e31 * this.e22 * this.e44 ) )
        + ( this.e31 * this.e24 * this.e42 ) + ( this.e41 * this.e22 * this.e34 ) )
        - ( this.e41 * this.e24 * this.e32 );
    double e32 = ( ( ( -this.e11 * this.e32 * this.e44 ) + ( this.e11 * this.e34 * this.e42 ) + ( this.e31 * this.e12 * this.e44 ) )
        - ( this.e31 * this.e14 * this.e42 ) - ( this.e41 * this.e12 * this.e34 ) )
        + ( this.e41 * this.e14 * this.e32 );
    double e33 = ( ( ( this.e11 * this.e22 * this.e44 ) - ( this.e11 * this.e24 * this.e42 ) - ( this.e21 * this.e12 * this.e44 ) )
        + ( this.e21 * this.e14 * this.e42 ) + ( this.e41 * this.e12 * this.e24 ) )
        - ( this.e41 * this.e14 * this.e22 );
    double e34 = ( ( ( -this.e11 * this.e22 * this.e34 ) + ( this.e11 * this.e24 * this.e32 ) + ( this.e21 * this.e12 * this.e34 ) )
        - ( this.e21 * this.e14 * this.e32 ) - ( this.e31 * this.e12 * this.e24 ) )
        + ( this.e31 * this.e14 * this.e22 );
    double e41 = ( ( ( -this.e21 * this.e32 * this.e43 ) + ( this.e21 * this.e33 * this.e42 ) + ( this.e31 * this.e22 * this.e43 ) )
        - ( this.e31 * this.e23 * this.e42 ) - ( this.e41 * this.e22 * this.e33 ) )
        + ( this.e41 * this.e23 * this.e32 );
    double e42 = ( ( ( this.e11 * this.e32 * this.e43 ) - ( this.e11 * this.e33 * this.e42 ) - ( this.e31 * this.e12 * this.e43 ) )
        + ( this.e31 * this.e13 * this.e42 ) + ( this.e41 * this.e12 * this.e33 ) )
        - ( this.e41 * this.e13 * this.e32 );
    double e43 = ( ( ( -this.e11 * this.e22 * this.e43 ) + ( this.e11 * this.e23 * this.e42 ) + ( this.e21 * this.e12 * this.e43 ) )
        - ( this.e21 * this.e13 * this.e42 ) - ( this.e41 * this.e12 * this.e23 ) )
        + ( this.e41 * this.e13 * this.e22 );
    double e44 = ( ( ( this.e11 * this.e22 * this.e33 ) - ( this.e11 * this.e23 * this.e32 ) - ( this.e21 * this.e12 * this.e33 ) )
        + ( this.e21 * this.e13 * this.e32 ) + ( this.e31 * this.e12 * this.e23 ) )
        - ( this.e31 * this.e13 * this.e22 );

    double determinant = ( this.e11 * e11 ) + ( this.e21 * e12 ) + ( this.e31 * e13 ) + ( this.e41 * e14 );

    if ( determinant == 0.0 ) {
      throw new ArithmeticException( "matrix is singular" );
    }

    return this.set( e11, e12, e13, e14, e21, e22, e23, e24, e31, e32, e33, e34, e41, e42, e43, e44 ).divide( determinant );
  }

  public Matrix4 duplicate() {
    return new Matrix4( this );
  }

  public Matrix4 set( double e11, double e12, double e13, double e14, double e21, double e22, double e23, double e24, double e31, double e32, double e33,
      double e34, double e41, double e42, double e43, double e44 ) {
    this.e11 = e11;
    this.e12 = e12;
    this.e13 = e13;
    this.e14 = e14;
    this.e21 = e21;
    this.e22 = e22;
    this.e23 = e23;
    this.e24 = e24;
    this.e31 = e31;
    this.e32 = e32;
    this.e33 = e33;
    this.e34 = e34;
    this.e41 = e41;
    this.e42 = e42;
    this.e43 = e43;
    this.e44 = e44;

    return this;
  }

  public Matrix4 set( Matrix4 matrix ) {
    // @formatter:off
    return this.set( matrix.e11, matrix.e12, matrix.e13, matrix.e14,
                     matrix.e21, matrix.e22, matrix.e23, matrix.e24,
                     matrix.e31, matrix.e32, matrix.e33, matrix.e34,
                     matrix.e41, matrix.e42, matrix.e43, matrix.e44 );
    // @formatter:on
  }

  public Matrix4 setIdentity() {
    // @formatter:off
    return this.set( 1.0, 0.0, 0.0, 0.0,
                     0.0, 1.0, 0.0, 0.0,
                     0.0, 0.0, 1.0, 0.0,
                     0.0, 0.0, 0.0, 1.0 );
    // @formatter:on
  }

  public double e11() {
    return this.e11;
  }

  public double e12() {
    return this.e12;
  }

  public double e13() {
    return this.e13;
  }

  public double e14() {
    return this.e14;
  }

  public double e21() {
    return this.e21;
  }

  public double e22() {
    return this.e22;
  }

  public double e23() {
    return this.e23;
  }

  public double e24() {
    return this.e24;
  }

  public double e31() {
    return this.e31;
  }

  public double e32() {
    return this.e32;
  }

  public double e33() {
    return this.e33;
  }

  public double e34() {
    return this.e34;
  }

  public double e41() {
    return this.e41;
  }

  public double e42() {
    return this.e42;
  }

  public double e43() {
    return this.e43;
  }

  public double e44() {
    return this.e44;
  }

  public boolean equals( Matrix4 matrix ) {
    // @formatter:off
    return ( this.e11 == matrix.e11 ) && ( this.e12 == matrix.e12 ) && ( this.e13 == matrix.e13 ) && ( this.e14 == matrix.e14 ) &&
           ( this.e21 == matrix.e21 ) && ( this.e22 == matrix.e22 ) && ( this.e23 == matrix.e23 ) && ( this.e24 == matrix.e24 ) &&
           ( this.e31 == matrix.e31 ) && ( this.e32 == matrix.e32 ) && ( this.e33 == matrix.e33 ) && ( this.e34 == matrix.e34 ) &&
           ( this.e41 == matrix.e41 ) && ( this.e42 == matrix.e42 ) && ( this.e43 == matrix.e43 ) && ( this.e44 == matrix.e44 );
    // @formatter:on
  }

  public String toString() {
    // @formatter:off
    return "((" + this.e11 + "," + this.e12 + "," + this.e13 + "," + this.e14 + ")," +
            "(" + this.e21 + "," + this.e22 + "," + this.e23 + "," + this.e24 + ")," +
            "(" + this.e31 + "," + this.e32 + "," + this.e33 + "," + this.e34 + ")," +
            "(" + this.e41 + "," + this.e42 + "," + this.e43 + "," + this.e44 + "))";
    // @formatter:on
  }
}
