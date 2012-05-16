package hhtat.game.ois.ois3d;

import hhtat.game.ois.math.Vector3;
import hhtat.game.ois.math.Vector4;
import hhtat.game.ois.util.OiSU;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.Stack;

public class OiSGL {
  protected static final int BUFFER_RED_IDX = 0;
  protected static final int BUFFER_GREEN_IDX = 1;
  protected static final int BUFFER_BLUE_IDX = 2;

  public static final int GL_MODELVIEW = 0x1700;
  public static final int GL_PROJECTION = 0x1701;

  public static final int GL_COLOR_BUFFER_BIT = 0x4000;
  public static final int GL_DEPTH_BUFFER_BIT = 0x100;

  private static final int GL_NOT_BEGUN = -1;
  public static final int GL_POINTS = 0x0;
  public static final int GL_LINES = 0x1;
  public static final int GL_TRIANGLES = 0x4;
  public static final int GL_QUADS = 0x7;

  private int width;
  private int height;

  private Transformation projectionMatrix;
  private Transformation modelViewMatrix;

  private Stack< Transformation > projectionMatrixStack;
  private Stack< Transformation > modelViewMatrixStack;

  private Transformation currentMatrix;

  private Vector3 currentColor;

  private int currentPrimitiveMode;
  private Transformation currentTransformation;
  private ArrayList< Vector4 > currentVertices;
  private ArrayList< Vector3 > currentColors;

  private Vector3 clearColor;
  private double clearDepth;

  private Buffer3 colorBuffer;
  private Buffer1 depthBuffer;

  private Clipper clipper;
  private Rasterizer rasterizer;

  private Viewport viewport;

  private BufferedImage image;
  private WritableRaster raster;
  private byte[] rasterData;

  public OiSGL( int width, int height ) {
    this.width = width;
    this.height = height;

    {
      this.projectionMatrix = new Transformation().loadIdentity();
      this.modelViewMatrix = new Transformation().loadIdentity();

      this.projectionMatrixStack = new Stack< Transformation >();
      this.modelViewMatrixStack = new Stack< Transformation >();

      this.currentMatrix = this.modelViewMatrix;
    }

    {
      this.currentColor = new Vector3( 1.0, 1.0, 1.0 );

      this.currentPrimitiveMode = OiSGL.GL_NOT_BEGUN;
      this.currentTransformation = new Transformation();
      this.currentVertices = new ArrayList< Vector4 >();
      this.currentColors = new ArrayList< Vector3 >();
    }

    {
      this.clearColor = new Vector3( 0.0, 0.0, 0.0 );
      this.clearDepth = 1.0;

      this.colorBuffer = new Buffer3( width, height );
      this.depthBuffer = new Buffer1( width, height );

      this.glClear( OiSGL.GL_COLOR_BUFFER_BIT | OiSGL.GL_DEPTH_BUFFER_BIT );

      this.clipper = new Clipper();
      this.rasterizer = new Rasterizer( this.colorBuffer, this.depthBuffer );

      this.viewport = new Viewport( width, height, this.clipper, this.rasterizer );
    }

    {
      this.image = new BufferedImage( width, height, BufferedImage.TYPE_3BYTE_BGR );
      this.raster = this.image.getRaster();
      this.rasterData = ( (DataBufferByte) this.raster.getDataBuffer() ).getData();
    }
  }

  public BufferedImage getImage() {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    for ( int i = this.height - 1, k = 0; i >= 0; i-- ) {
      for ( int j = 0; j < this.width; j++, k += 3 ) {
        this.rasterData[ k + 0 ] = (byte) OiSU.clamp( OiSU.roundToInt( 255.0 * this.colorBuffer.data[ OiSGL.BUFFER_BLUE_IDX ][ i ][ j ] ), 0, 255 );
        this.rasterData[ k + 1 ] = (byte) OiSU.clamp( OiSU.roundToInt( 255.0 * this.colorBuffer.data[ OiSGL.BUFFER_GREEN_IDX ][ i ][ j ] ), 0, 255 );
        this.rasterData[ k + 2 ] = (byte) OiSU.clamp( OiSU.roundToInt( 255.0 * this.colorBuffer.data[ OiSGL.BUFFER_RED_IDX ][ i ][ j ] ), 0, 255 );
      }
    }

    return this.image;
  }

  public void glMatrixMode( int mode ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    switch ( mode ) {
      case GL_MODELVIEW:
        this.currentMatrix = this.modelViewMatrix;
        break;
      case GL_PROJECTION:
        this.currentMatrix = this.projectionMatrix;
        break;
      default:
        throw new IllegalArgumentException( "invalid mode" );
    }
  }

  public void glPushMatrix() {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    if ( this.currentMatrix == this.modelViewMatrix ) {
      this.modelViewMatrixStack.push( this.modelViewMatrix );
      this.currentMatrix = this.modelViewMatrix = this.modelViewMatrix.duplicate();
    } else if ( this.currentMatrix == this.projectionMatrix ) {
      this.projectionMatrixStack.push( this.projectionMatrix );
      this.currentMatrix = this.projectionMatrix = this.projectionMatrix.duplicate();
    }
  }

  public void glPopMatrix() {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    if ( this.currentMatrix == this.modelViewMatrix ) {
      if ( this.modelViewMatrixStack.isEmpty() ) {
        throw new IllegalStateException( "no more matrices to pop from modelview stack" );
      }

      this.currentMatrix = this.modelViewMatrix = this.modelViewMatrixStack.pop();
    } else if ( this.currentMatrix == this.projectionMatrix ) {
      if ( this.projectionMatrixStack.isEmpty() ) {
        throw new IllegalStateException( "no more matrices to pop from projection stack" );
      }

      this.currentMatrix = this.projectionMatrix = this.projectionMatrixStack.pop();
    }
  }

  public void glLoadIdentity() {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.currentMatrix.loadIdentity();
  }

  public void glViewport( int x, int y, int width, int height ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    if ( ( width < 0 ) || ( height < 0 ) ) {
      throw new IllegalArgumentException( "width and height cannot be negative" );
    }

    this.viewport.setViewport( x, y, width, height );
  }

  public void glDepthRange( double nearVal, double farVal ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.viewport.setDepthRange( OiSU.clamp( nearVal, 0.0, 1.0 ), OiSU.clamp( farVal, 0.0, 1.0 ) );
  }

  public void glFrustum( double left, double right, double bottom, double top, double nearVal, double farVal ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    if ( ( nearVal <= 0 ) || ( farVal <= 0 ) ) {
      throw new IllegalArgumentException( "both nearVal and farVal must be positive" );
    }

    if ( ( left == right ) || ( bottom == top ) ) {
      throw new IllegalArgumentException( "left and right must not be the same nor can bottom and top be the same" );
    }

    this.currentMatrix.frustum( left, right, bottom, top, nearVal, farVal );
  }

  public void glOrtho( double left, double right, double bottom, double top, double nearVal, double farVal ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.currentMatrix.orthographic( left, right, bottom, top, nearVal, farVal );
  }

  public void gluPerspective( double fovy, double aspect, double zNear, double zFar ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    double top = zNear * Math.tan( ( Math.PI / 360.0 ) * fovy );
    this.currentMatrix.frustum( -top * aspect, top * aspect, -top, top, zNear, zFar );
  }

  public void gluOrtho2D( double left, double right, double bottom, double top ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.currentMatrix.orthographic( left, right, bottom, top, -1.0, 1.0 );
  }

  public void glClearColor( double red, double green, double blue ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.clearColor.set( red, green, blue );
  }

  public void glClearDepth( double depth ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.clearDepth = depth;
  }

  public void glClear( int mask ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    if ( ( mask & ( OiSGL.GL_COLOR_BUFFER_BIT | OiSGL.GL_DEPTH_BUFFER_BIT ) ) == ( OiSGL.GL_COLOR_BUFFER_BIT | OiSGL.GL_DEPTH_BUFFER_BIT ) ) {
      double r = this.clearColor.x();
      double g = this.clearColor.y();
      double b = this.clearColor.z();

      for ( int i = 0; i < this.height; i++ ) {
        for ( int j = 0; j < this.width; j++ ) {
          this.colorBuffer.data[ OiSGL.BUFFER_RED_IDX ][ i ][ j ] = r;
          this.colorBuffer.data[ OiSGL.BUFFER_GREEN_IDX ][ i ][ j ] = g;
          this.colorBuffer.data[ OiSGL.BUFFER_BLUE_IDX ][ i ][ j ] = b;

          this.depthBuffer.data[ i ][ j ] = this.clearDepth;
        }
      }
    } else if ( ( mask & OiSGL.GL_COLOR_BUFFER_BIT ) == OiSGL.GL_COLOR_BUFFER_BIT ) {
      double r = this.clearColor.x();
      double b = this.clearColor.y();
      double g = this.clearColor.z();

      for ( int i = 0; i < this.height; i++ ) {
        for ( int j = 0; j < this.width; j++ ) {
          this.colorBuffer.data[ OiSGL.BUFFER_RED_IDX ][ i ][ j ] = r;
          this.colorBuffer.data[ OiSGL.BUFFER_GREEN_IDX ][ i ][ j ] = g;
          this.colorBuffer.data[ OiSGL.BUFFER_BLUE_IDX ][ i ][ j ] = b;
        }
      }
    } else if ( ( mask & OiSGL.GL_DEPTH_BUFFER_BIT ) == OiSGL.GL_DEPTH_BUFFER_BIT ) {
      for ( int i = 0; i < this.height; i++ ) {
        for ( int j = 0; j < this.width; j++ ) {
          this.depthBuffer.data[ i ][ j ] = this.clearDepth;
        }
      }
    }
  }

  public void glRotate( double angle, double x, double y, double z ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.currentMatrix.rotate( ( Math.PI / 180.0 ) * angle, x, y, z );
  }

  public void glScale( double x, double y, double z ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.currentMatrix.scale( x, y, z );
  }

  public void glTranslate( double x, double y, double z ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    this.currentMatrix.translate( x, y, z );
  }

  public void glColor( double red, double green, double blue ) {
    this.currentColor = new Vector3( red, green, blue );
  }

  public void glBegin( int mode ) {
    if ( this.currentPrimitiveMode != OiSGL.GL_NOT_BEGUN ) {
      throw new IllegalStateException( "glEnd has not be called for last call to glBegin" );
    }

    switch ( mode ) {
      case OiSGL.GL_POINTS:
      case OiSGL.GL_LINES:
      case OiSGL.GL_TRIANGLES:
      case OiSGL.GL_QUADS:
        this.currentPrimitiveMode = mode;
        this.currentTransformation.set( this.projectionMatrix ).multiply( this.modelViewMatrix );
        this.currentVertices.clear();
        break;
      default:
        throw new IllegalArgumentException( "invalid mode" );
    }
  }

  public void glVertex( double x, double y ) {
    this.glVertex( x, y, 0.0 );
  }

  public void glVertex( double x, double y, double z ) {
    switch ( this.currentPrimitiveMode ) {
      case OiSGL.GL_NOT_BEGUN:
        throw new IllegalStateException( "glBegin has not be called after last call to glEnd" );
      case OiSGL.GL_POINTS: {
        Vector4 a = new Vector4( x, y, z, 1.0 );
        Vector3 aColor = this.currentColor;

        this.currentTransformation.transform( a );

        this.viewport.drawPoint( a, aColor );
        break;
      }
      case OiSGL.GL_LINES: {
        if ( this.currentVertices.size() < 1 ) {
          this.currentVertices.add( new Vector4( x, y, z, 1.0 ) );
          this.currentColors.add( this.currentColor );
        } else {
          Vector4 a = this.currentVertices.get( 0 );
          Vector4 b = new Vector4( x, y, z, 1.0 );

          Vector3 aColor = this.currentColors.get( 0 );
          Vector3 bColor = this.currentColor;

          this.currentVertices.clear();
          this.currentColors.clear();

          this.currentTransformation.transform( a );
          this.currentTransformation.transform( b );

          this.viewport.drawLine( a, aColor, b, bColor );
        }
        break;
      }
      case OiSGL.GL_TRIANGLES: {
        if ( this.currentVertices.size() < 2 ) {
          this.currentVertices.add( new Vector4( x, y, z, 1.0 ) );
          this.currentColors.add( this.currentColor );
        } else {
          Vector4 a = this.currentVertices.get( 0 );
          Vector4 b = this.currentVertices.get( 1 );
          Vector4 c = new Vector4( x, y, z, 1.0 );

          Vector3 aColor = this.currentColors.get( 0 );
          Vector3 bColor = this.currentColors.get( 1 );
          Vector3 cColor = this.currentColor;

          this.currentVertices.clear();
          this.currentColors.clear();

          this.currentTransformation.transform( a );
          this.currentTransformation.transform( b );
          this.currentTransformation.transform( c );

          this.viewport.drawTriangle( a, aColor, b, bColor, c, cColor );
        }
        break;
      }
      case OiSGL.GL_QUADS: {
        if ( this.currentVertices.size() < 3 ) {
          this.currentVertices.add( new Vector4( x, y, z, 1.0 ) );
          this.currentColors.add( this.currentColor );
        } else {
          Vector4 a = this.currentVertices.get( 0 );
          Vector4 b = this.currentVertices.get( 1 );
          Vector4 c = this.currentVertices.get( 2 );
          Vector4 d = new Vector4( x, y, z, 1.0 );

          Vector3 aColor = this.currentColors.get( 0 );
          Vector3 bColor = this.currentColors.get( 1 );
          Vector3 cColor = this.currentColors.get( 2 );
          Vector3 dColor = this.currentColor;

          this.currentVertices.clear();
          this.currentColors.clear();

          this.currentTransformation.transform( a );
          this.currentTransformation.transform( b );
          this.currentTransformation.transform( c );
          this.currentTransformation.transform( d );

          this.viewport.drawTriangle( a, aColor, b, bColor, c, cColor );
          this.viewport.drawTriangle( c, cColor, d, dColor, a, aColor );
        }
        break;
      }
      default:
        throw new InternalError( "currentPrimitiveMode is invalid" );
    }
  }

  public void glEnd() {
    switch ( this.currentPrimitiveMode ) {
      case OiSGL.GL_NOT_BEGUN:
        throw new IllegalStateException( "glBegin has not be called after last call to glEnd" );
      case OiSGL.GL_POINTS:
      case OiSGL.GL_LINES:
      case OiSGL.GL_TRIANGLES:
      case OiSGL.GL_QUADS:
        this.currentPrimitiveMode = OiSGL.GL_NOT_BEGUN;
        break;
      default:
        throw new InternalError( "currentPrimitiveMode is invalid" );
    }
  }
}
