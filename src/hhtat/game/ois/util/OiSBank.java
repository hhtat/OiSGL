package hhtat.game.ois.util;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.math.Vector4;

import java.util.Stack;

public class OiSBank {
  private static int objectCount = 0;
  private static int nextReport = 1;

  private static Stack< Vector3 > vector3Vault = new Stack< Vector3 >();
  private static Stack< Vector4 > vector4Vault = new Stack< Vector4 >();

  private static void incrementObjectCount() {
    OiSBank.objectCount++;

    if ( OiSBank.objectCount >= OiSBank.nextReport ) {
      System.out.println( "OiSBank has created a total of " + OiSBank.objectCount + " objects!" );

      OiSBank.nextReport *= 2;
    }
  }

  public static Vector3 withdrawVector3() {
    if ( !OiSBank.vector3Vault.isEmpty() ) {
      return OiSBank.vector3Vault.pop();
    } else {
      OiSBank.incrementObjectCount();
      return new Vector3();
    }
  }

  public static Vector3 deposit( Vector3 vector ) {
    if ( vector == null ) {
      throw new IllegalArgumentException( "cannot deposit null objects" );
    }

    OiSBank.vector3Vault.push( vector );

    return null;
  }

  public static Vector4 withdrawVector4() {
    if ( !OiSBank.vector4Vault.isEmpty() ) {
      return OiSBank.vector4Vault.pop();
    } else {
      OiSBank.incrementObjectCount();
      return new Vector4();
    }
  }

  public static Vector4 deposit( Vector4 vector ) {
    if ( vector == null ) {
      throw new IllegalArgumentException( "cannot deposit null objects" );
    }

    OiSBank.vector4Vault.push( vector );

    return null;
  }
}
