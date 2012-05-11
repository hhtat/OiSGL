package hhtat.game.ois.math;

public class Vector4 {
  protected double x;
  protected double y;
  protected double z;
  protected double w;

  public Vector4() {
    this( 0.0, 0.0, 0.0, 0.0 );
  }

  public Vector4( double x, double y, double z, double w ) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Vector4( Vector4 vector ) {
    this( vector.x, vector.y, vector.z, vector.w );
  }

  public Vector4( Vector3 vector, double w ) {
    this( vector.x, vector.y, vector.z, w );
  }

  public Vector4 multiply( double scalar ) {
    this.x *= scalar;
    this.y *= scalar;
    this.z *= scalar;
    this.w *= scalar;

    return this;
  }

  public Vector4 divide( double scalar ) {
    if ( scalar == 0.0 ) {
      throw new IllegalArgumentException( "cannot divide by zero" );
    }

    return this.multiply( 1.0 / scalar );
  }

  public Vector4 add( double x, double y, double z, double w ) {
    this.x += x;
    this.y += y;
    this.z += z;
    this.w += w;

    return this;
  }

  public Vector4 add( Vector4 rhs ) {
    return this.add( rhs.x, rhs.y, rhs.z, rhs.w );
  }

  public Vector4 subtract( Vector4 rhs ) {
    return this.add( -rhs.x, -rhs.y, -rhs.z, -rhs.w );
  }

  public double dot( double x, double y, double z, double w ) {
    return ( this.x * x ) + ( this.y * y ) + ( this.z * z ) + ( this.w * w );
  }

  public double dot( Vector4 rhs ) {
    return ( this.x * rhs.x ) + ( this.y * rhs.y ) + ( this.z * rhs.z ) + ( this.w * rhs.w );
  }

  public double length() {
    return Math.sqrt( this.dot( this ) );
  }

  public Vector4 normalize() {
    double length = this.length();

    if ( length != 1.0 ) {
      this.divide( this.length() );
    }

    return this;
  }

  public Vector4 duplicate() {
    return new Vector4( this );
  }

  public Vector4 set( double x, double y, double z, double w ) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;

    return this;
  }

  public Vector4 set( Vector4 vector ) {
    return this.set( vector.x, vector.y, vector.z, vector.w );
  }

  public Vector4 set( Vector3 vector, double w ) {
    return this.set( vector.x, vector.y, vector.z, w );
  }

  public double x() {
    return this.x;
  }

  public double y() {
    return this.y;
  }

  public double z() {
    return this.z;
  }

  public double w() {
    return this.w;
  }

  public boolean equals( Vector4 vector ) {
    return ( this.x == vector.x ) && ( this.y == vector.y ) && ( this.z == vector.z ) && ( this.w == vector.w );
  }

  public String toString() {
    return "(" + this.x + "," + this.y + "," + this.z + "," + this.w + ")";
  }
}
