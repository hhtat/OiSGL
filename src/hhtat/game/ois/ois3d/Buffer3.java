package hhtat.game.ois.ois3d;

public class Buffer3 {
  protected int width;
  protected int height;

  protected double[][][] data;

  public Buffer3( int width, int height ) {
    this.width = width;
    this.height = height;

    this.data = new double[ 3 ][ height ][ width ];
  }
}
