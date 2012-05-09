package hhtat.game.ois.ois3d;

public class Buffer1 {
  protected int width;
  protected int height;

  protected double[][] data;

  public Buffer1( int width, int height ) {
    this.width = width;
    this.height = height;

    this.data = new double[ height ][ width ];
  }
}
