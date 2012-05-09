package hhtat.game.ois.ois3d;

import hhtat.game.ois.util.OiSU;

public class Rasterizer {
  private int width;
  private int height;

  private Buffer3 colorBuffer;
  private Buffer1 depthBuffer;

  public Rasterizer( Buffer3 colorBuffer, Buffer1 depthBuffer ) {
    this.width = colorBuffer.width;
    this.height = colorBuffer.height;

    if ( ( depthBuffer.width < this.width ) || ( depthBuffer.height < this.height ) ) {
      throw new IllegalArgumentException( "depthBuffer must be larger then or equal to colorBuffer in size" );
    }

    this.colorBuffer = colorBuffer;
    this.depthBuffer = depthBuffer;
  }

  private boolean depthTest( double a, double b ) {
    return a < b;
  }

  public void rasterizePoint( double x, double y, double z, double red, double green, double blue ) {
    int yInt = OiSU.roundToInt( y );

    if ( OiSU.isInRange( yInt, 0, this.height - 1 ) ) {
      int xInt = OiSU.roundToInt( x );

      if ( OiSU.isInRange( xInt, 0, this.width - 1 ) ) {
        if ( this.depthTest( z, this.depthBuffer.data[ yInt ][ xInt ] ) ) {
          this.colorBuffer.data[ OiSGL.BUFFER_RED_IDX ][ yInt ][ xInt ] = red;
          this.colorBuffer.data[ OiSGL.BUFFER_GREEN_IDX ][ yInt ][ xInt ] = green;
          this.colorBuffer.data[ OiSGL.BUFFER_BLUE_IDX ][ yInt ][ xInt ] = blue;

          this.depthBuffer.data[ yInt ][ xInt ] = z;
        }
      }
    }
  }

  public void rasterizeLine( double ax, double ay, double az, double bx, double by, double bz, double aRed, double aGreen, double aBlue, double bRed,
      double bGreen, double bBlue ) {
    // TODO in the drawing loop, if x/y were integers can we do this much
    // faster?

    if ( ( ax != bx ) || ( ay != by ) ) {
      double xd = bx - ax;
      double yd = by - ay;

      if ( Math.abs( xd ) > Math.abs( yd ) ) {
        double xMin, xMax;

        double y, dy;
        double z, dz;

        double red, dRed;
        double green, dGreen;
        double blue, dBlue;

        if ( ax < bx ) {
          xMin = ax;
          xMax = bx;

          y = ay;
          z = az;

          red = aRed;
          green = aGreen;
          blue = aBlue;

          double xDiffInv = 1.0 / ( xMax - xMin );

          dy = xDiffInv * ( by - y );
          dz = xDiffInv * ( bz - z );

          dRed = xDiffInv * ( bRed - red );
          dGreen = xDiffInv * ( bGreen - green );
          dBlue = xDiffInv * ( bBlue - blue );
        } else {
          xMin = bx;
          xMax = ax;

          y = by;
          z = bz;

          red = bRed;
          green = bGreen;
          blue = bBlue;

          double xDiffInv = 1.0 / ( xMax - xMin );

          dy = xDiffInv * ( ay - y );
          dz = xDiffInv * ( az - z );

          dRed = xDiffInv * ( aRed - red );
          dGreen = xDiffInv * ( aGreen - green );
          dBlue = xDiffInv * ( aBlue - blue );
        }

        for ( double x = xMin; x <= xMax; x += 1.0 ) {
          this.rasterizePoint( x, y, z, red, green, blue );

          y += dy;
          z += dz;

          red += dRed;
          green += dGreen;
          blue += dBlue;
        }
      } else {
        double yMin, yMay;

        double x, dx;
        double z, dz;

        double red, dRed;
        double green, dGreen;
        double blue, dBlue;

        if ( ay < by ) {
          yMin = ay;
          yMay = by;

          x = ax;
          z = az;

          red = aRed;
          green = aGreen;
          blue = aBlue;

          double yDiffInv = 1.0 / ( yMay - yMin );

          dx = yDiffInv * ( bx - x );
          dz = yDiffInv * ( bz - z );

          dRed = yDiffInv * ( bRed - red );
          dGreen = yDiffInv * ( bGreen - green );
          dBlue = yDiffInv * ( bBlue - blue );
        } else {
          yMin = by;
          yMay = ay;

          x = bx;
          z = bz;

          red = bRed;
          green = bGreen;
          blue = bBlue;

          double yDiffInv = 1.0 / ( yMay - yMin );

          dx = yDiffInv * ( ax - x );
          dz = yDiffInv * ( az - z );

          dRed = yDiffInv * ( aRed - red );
          dGreen = yDiffInv * ( aGreen - green );
          dBlue = yDiffInv * ( aBlue - blue );
        }

        for ( double y = yMin; y <= yMay; y += 1.0 ) {
          this.rasterizePoint( x, y, z, red, green, blue );

          x += dx;
          z += dz;

          red += dRed;
          green += dGreen;
          blue += dBlue;
        }
      }
    } else {
      // TODO we can do better
      if ( this.depthTest( az, bz ) ) {
        this.rasterizePoint( ax, ay, az, aRed, aGreen, aBlue );
      } else {
        this.rasterizePoint( bx, by, bz, bRed, bGreen, bBlue );
      }
    }
  }
}
