package hhtat.game.ois.util;

public class OiSU {
  public static double abs( double value ) {
    return value < 0.0 ? -value : value;
  }

  public static int roundToInt( double value ) {
    return (int) ( value < 0.0 ? value - 0.5 : value + 0.5 );
  }

  public static boolean isInRange( int value, int min, int max ) {
    return ( min <= value ) && ( value <= max );
  }

  public static boolean isInRange( double value, double min, double max ) {
    return ( min <= value ) && ( value <= max );
  }

  public static int clamp( int value, int min, int max ) {
    return value <= min ? min : value >= max ? max : value;
  }

  public static double clamp( double value, double min, double max ) {
    return value <= min ? min : value >= max ? max : value;
  }

  public static int toIntRGB( double red, double green, double blue ) {
    int r = OiSU.clamp( OiSU.roundToInt( 255.0 * red ), 0, 255 );
    int g = OiSU.clamp( OiSU.roundToInt( 255.0 * green ), 0, 255 );
    int b = OiSU.clamp( OiSU.roundToInt( 255.0 * blue ), 0, 255 );

    return ( r << 16 ) | ( g << 8 ) | b;
  }
}
