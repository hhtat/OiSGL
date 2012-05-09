package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;

public class Vertex3 extends Vector3 {
  protected Vector3 color;

  public Vertex3( double x, double y, double z, Vector3 color ) {
    super( x, y, z );

    this.color = color;
  }

  public Vertex3 duplicate() {
    return new Vertex3( this.x, this.y, this.z, this.color );
  }

  public boolean equals( Vertex3 vertex ) {
    return ( this.x == vertex.x ) && ( this.y == vertex.y ) && ( this.z == vertex.z ) && this.color.equals( vertex.color );
  }

  public String toString() {
    return "(" + this.x + "," + this.y + "," + this.z + ")c" + this.color.toString();
  }
}
