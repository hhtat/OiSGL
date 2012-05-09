package hhtat.game.ois.math;

public class Vector3 {
  protected double x;
  protected double y;
  protected double z;

  public Vector3() {
    this( 0.0, 0.0, 0.0 );
  }

  public Vector3( double x, double y, double z ) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3( Vector3 vector ) {
    this( vector.x, vector.y, vector.z );
  }

  public Vector3 multiply( double scalar ) {
    this.x *= scalar;
    this.y *= scalar;
    this.z *= scalar;

    return this;
  }

  public Vector3 divide( double scalar ) {
    if ( scalar == 0.0 ) {
      throw new IllegalArgumentException( "cannot divide by zero" );
    }

    return this.multiply( 1.0 / scalar );
  }

  public Vector3 add( double x, double y, double z ) {
    this.x += x;
    this.y += y;
    this.z += z;

    return this;
  }

  public Vector3 add( Vector3 rhs ) {
    return this.add( rhs.x, rhs.y, rhs.z );
  }

  public Vector3 subtract( Vector3 rhs ) {
    return this.add( -rhs.x, -rhs.y, -rhs.z );
  }

  public double dot( double x, double y, double z ) {
    return ( this.x * x ) + ( this.y * y ) + ( this.z * z );
  }

  public double dot( Vector3 rhs ) {
    return this.dot( rhs.x, rhs.y, rhs.z );
  }

  public Vector3 cross( double x, double y, double z ) {
    return this.set( ( this.y * z ) - ( this.z * y ), ( this.z * x ) - ( this.x * z ), ( this.x * y ) - ( this.y * x ) );
  }

  public Vector3 cross( Vector3 rhs ) {
    return this.cross( rhs.x, rhs.y, rhs.z );
  }

  public double length() {
    return Math.sqrt( this.dot( this ) );
  }

  public Vector3 normalize() {
    double length = this.length();

    if ( length != 1.0 ) {
      this.divide( this.length() );
    }

    return this;
  }

  public Vector3 duplicate() {
    return new Vector3( this );
  }

  public Vector3 set( double x, double y, double z ) {
    this.x = x;
    this.y = y;
    this.z = z;

    return this;
  }

  public Vector3 set( Vector3 vector ) {
    return this.set( vector.x, vector.y, vector.z );
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

  public boolean equals( Vector3 vector ) {
    return ( this.x == vector.x ) && ( this.y == vector.y ) && ( this.z == vector.z );
  }

  public String toString() {
    return "(" + this.x + "," + this.y + "," + this.z + ")";
  }
}
