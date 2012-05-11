package hhtat.game.ois.util;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.math.Vector4;

import java.util.Stack;

public class VectorBank {
  private Stack< Vector3 > vector3Stack;
  private Stack< Vector4 > vector4Stack;

  public VectorBank() {
    this.vector3Stack = new Stack< Vector3 >();
    this.vector4Stack = new Stack< Vector4 >();
  }

  public Vector3 deposit( Vector3 vector ) {
    if ( vector == null ) {
      throw new IllegalArgumentException( "cannot deposit a null reference" );
    }

    this.vector3Stack.push( vector );

    return null;
  }

  public Vector4 deposit( Vector4 vector ) {
    if ( vector == null ) {
      throw new IllegalArgumentException( "cannot deposit a null reference" );
    }

    this.vector4Stack.push( vector );

    return null;
  }

  public Vector3 withdrawVector3() {
    if ( this.vector3Stack.isEmpty() ) {
      throw new IllegalStateException( "out of objects of class Vector3" );
    }

    return this.vector3Stack.pop();
  }

  public Vector4 withdrawVector4() {
    if ( this.vector3Stack.isEmpty() ) {
      throw new IllegalStateException( "out of objects of class Vector4" );
    }

    return this.vector4Stack.pop();
  }
}
