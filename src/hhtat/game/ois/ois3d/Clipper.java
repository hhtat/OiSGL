package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.math.Vector4;

public class Clipper {
  private static final Vector4 CLIP_X_LOWER = new Vector4( 1.0, 0.0, 0.0, 1.0 );
  private static final Vector4 CLIP_X_UPPER = new Vector4( -1.0, 0.0, 0.0, 1.0 );

  private static final Vector4 CLIP_Y_LOWER = new Vector4( 0.0, 1.0, 0.0, 1.0 );
  private static final Vector4 CLIP_Y_UPPER = new Vector4( 0.0, -1.0, 0.0, 1.0 );

  private static final Vector4 CLIP_Z_LOWER = new Vector4( 0.0, 0.0, 1.0, 1.0 );
  private static final Vector4 CLIP_Z_UPPER = new Vector4( 0.0, 0.0, -1.0, 1.0 );

  private static final double OVERSHOOT = 0.000001;

  private Vector4 tv4;
  private Vector3 tv3;

  public Clipper() {
    this.tv4 = new Vector4();
    this.tv3 = new Vector3();
  }

  public boolean clipLine( Vector4 a, Vector3 aColor, Vector4 b, Vector3 bColor ) {
    double aDotClipXL = a.dot( Clipper.CLIP_X_LOWER );
    double aDotClipXU = a.dot( Clipper.CLIP_X_UPPER );
    double aDotClipYL = a.dot( Clipper.CLIP_Y_LOWER );
    double aDotClipYU = a.dot( Clipper.CLIP_Y_UPPER );
    double aDotClipZL = a.dot( Clipper.CLIP_Z_LOWER );
    double aDotClipZU = a.dot( Clipper.CLIP_Z_UPPER );
    double bDotClipXL = b.dot( Clipper.CLIP_X_LOWER );
    double bDotClipXU = b.dot( Clipper.CLIP_X_UPPER );
    double bDotClipYL = b.dot( Clipper.CLIP_Y_LOWER );
    double bDotClipYU = b.dot( Clipper.CLIP_Y_UPPER );
    double bDotClipZL = b.dot( Clipper.CLIP_Z_LOWER );
    double bDotClipZU = b.dot( Clipper.CLIP_Z_UPPER );

    boolean aClipXLFailed = aDotClipXL < 0.0;
    boolean aClipXUFailed = aDotClipXU < 0.0;
    boolean aClipYLFailed = aDotClipYL < 0.0;
    boolean aClipYUFailed = aDotClipYU < 0.0;
    boolean aClipZLFailed = aDotClipZL < 0.0;
    boolean aClipZUFailed = aDotClipZU < 0.0;
    boolean bClipXLFailed = bDotClipXL < 0.0;
    boolean bClipXUFailed = bDotClipXU < 0.0;
    boolean bClipYLFailed = bDotClipYL < 0.0;
    boolean bClipYUFailed = bDotClipYU < 0.0;
    boolean bClipZLFailed = bDotClipZL < 0.0;
    boolean bClipZUFailed = bDotClipZU < 0.0;

    while ( aClipXLFailed || aClipXUFailed || aClipYLFailed || aClipYUFailed || aClipZLFailed || aClipZUFailed || bClipXLFailed || bClipXUFailed
        || bClipYLFailed || bClipYUFailed || bClipZLFailed || bClipZUFailed ) {
      // @formatter:off  
      if ( ( aClipXLFailed && bClipXLFailed ) ||
           ( aClipXUFailed && bClipXUFailed ) ||
           ( aClipYLFailed && bClipYLFailed ) ||
           ( aClipYUFailed && bClipYUFailed ) ||
           ( aClipZLFailed && bClipZLFailed ) || 
           ( aClipZUFailed && bClipZUFailed ) ) {
        return false;
      }
      // @formatter:on

      double aToBTMax = 0.0;
      double bToATMax = 0.0;

      Vector4 bMinusA = this.tv4.set( b ).subtract( a );

      if ( aClipXLFailed || bClipXLFailed ) {
        double invAMinusBDotClipXL = 1.0 / bMinusA.dot( Clipper.CLIP_X_LOWER );

        if ( aClipXLFailed ) {
          aToBTMax = Math.max( aToBTMax, aDotClipXL * -invAMinusBDotClipXL );
        }

        if ( bClipXLFailed ) {
          bToATMax = Math.max( bToATMax, bDotClipXL * invAMinusBDotClipXL );
        }
      }

      if ( aClipXUFailed || bClipXUFailed ) {
        double invAMinusBDotClipXU = 1.0 / bMinusA.dot( Clipper.CLIP_X_UPPER );

        if ( aClipXUFailed ) {
          aToBTMax = Math.max( aToBTMax, aDotClipXU * -invAMinusBDotClipXU );
        }

        if ( bClipXUFailed ) {
          bToATMax = Math.max( bToATMax, bDotClipXU * invAMinusBDotClipXU );
        }
      }

      if ( aClipYLFailed || bClipYLFailed ) {
        double invAMinusBDotClipYL = 1.0 / bMinusA.dot( Clipper.CLIP_Y_LOWER );

        if ( aClipYLFailed ) {
          aToBTMax = Math.max( aToBTMax, aDotClipYL * -invAMinusBDotClipYL );
        }

        if ( bClipYLFailed ) {
          bToATMax = Math.max( bToATMax, bDotClipYL * invAMinusBDotClipYL );
        }
      }

      if ( aClipYUFailed || bClipYUFailed ) {
        double invAMinusBDotClipYU = 1.0 / bMinusA.dot( Clipper.CLIP_Y_UPPER );

        if ( aClipYUFailed ) {
          aToBTMax = Math.max( aToBTMax, aDotClipYU * -invAMinusBDotClipYU );
        }

        if ( bClipYUFailed ) {
          bToATMax = Math.max( bToATMax, bDotClipYU * invAMinusBDotClipYU );
        }
      }

      if ( aClipZLFailed || bClipZLFailed ) {
        double invAMinusBDotClipZL = 1.0 / bMinusA.dot( Clipper.CLIP_Z_LOWER );

        if ( aClipZLFailed ) {
          aToBTMax = Math.max( aToBTMax, aDotClipZL * -invAMinusBDotClipZL );
        }

        if ( bClipZLFailed ) {
          bToATMax = Math.max( bToATMax, bDotClipZL * invAMinusBDotClipZL );
        }
      }

      if ( aClipZUFailed || bClipZUFailed ) {
        double invAMinusBDotClipZU = 1.0 / bMinusA.dot( Clipper.CLIP_Z_UPPER );

        if ( aClipZUFailed ) {
          aToBTMax = Math.max( aToBTMax, aDotClipZU * -invAMinusBDotClipZU );
        }

        if ( bClipZUFailed ) {
          bToATMax = Math.max( bToATMax, bDotClipZU * invAMinusBDotClipZU );
        }
      }

      if ( ( aToBTMax + bToATMax ) > 1.0 ) {
        return false;
      }

      Vector3 bColorMinusAColor = this.tv3.set( bColor ).subtract( aColor );

      double overshoot = Clipper.OVERSHOOT / bMinusA.length();

      if ( aToBTMax > 0.0 ) {
        if ( aToBTMax > overshoot ) {
          aToBTMax += overshoot;
        }

        a.add( aToBTMax, bMinusA );
        aColor.add( aToBTMax, bColorMinusAColor );

        aDotClipXL = a.dot( Clipper.CLIP_X_LOWER );
        aDotClipXU = a.dot( Clipper.CLIP_X_UPPER );
        aDotClipYL = a.dot( Clipper.CLIP_Y_LOWER );
        aDotClipYU = a.dot( Clipper.CLIP_Y_UPPER );
        aDotClipZL = a.dot( Clipper.CLIP_Z_LOWER );
        aDotClipZU = a.dot( Clipper.CLIP_Z_UPPER );

        aClipXLFailed = aDotClipXL < 0.0;
        aClipXUFailed = aDotClipXU < 0.0;
        aClipYLFailed = aDotClipYL < 0.0;
        aClipYUFailed = aDotClipYU < 0.0;
        aClipZLFailed = aDotClipZL < 0.0;
        aClipZUFailed = aDotClipZU < 0.0;
      }

      if ( bToATMax > 0.0 ) {
        if ( bToATMax > overshoot ) {
          bToATMax += overshoot;
        }

        b.add( -bToATMax, bMinusA );
        bColor.add( -bToATMax, bColorMinusAColor );

        bDotClipXL = b.dot( Clipper.CLIP_X_LOWER );
        bDotClipXU = b.dot( Clipper.CLIP_X_UPPER );
        bDotClipYL = b.dot( Clipper.CLIP_Y_LOWER );
        bDotClipYU = b.dot( Clipper.CLIP_Y_UPPER );
        bDotClipZL = b.dot( Clipper.CLIP_Z_LOWER );
        bDotClipZU = b.dot( Clipper.CLIP_Z_UPPER );

        bClipXLFailed = bDotClipXL < 0.0;
        bClipXUFailed = bDotClipXU < 0.0;
        bClipYLFailed = bDotClipYL < 0.0;
        bClipYUFailed = bDotClipYU < 0.0;
        bClipZLFailed = bDotClipZL < 0.0;
        bClipZUFailed = bDotClipZU < 0.0;
      }
    }

    return true;
  }
}
