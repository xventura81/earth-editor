
package earth.editor;

import static com.google.gwt.core.client.GWT.log;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.array.Uint16Array;
import com.googlecode.gwtgl.array.Uint32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLTexture;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

import earth.editor.figure.CubeFactory;
import earth.editor.figure.IndexedMesh;
import earth.editor.figure.Mesh;
import earth.editor.figure.SphereFactory;
import earth.editor.maths.FloatMatrix;

/**
 * Example that shows the Editor created with the GwtGL binding.
 */
public class EarthEditorViewer extends AbstractEarthEditorViewer {

	private Mesh mesh = SphereFactory.createNewInstance(8 ,8, 1);
	//private Mesh mesh = CubeFactory.createNewInstance(1.0f);
	
	private WebGLProgram shaderProgram;
	private int vertexPositionAttribute;
	private int textureCoordAttribute;
	
	private float translateZ = -2;
	private FloatMatrix perspectiveMatrix;
	private FloatMatrix translationMatrix;
	private FloatMatrix resultingMatrix;
	
	private WebGLBuffer vertexBuffer;
	private WebGLBuffer vertexTextureCoordBuffer;
	private WebGLBuffer indexBuffer;
	private WebGLUniformLocation projectionMatrixUniform;
	private WebGLUniformLocation textureUniform;
	private WebGLTexture texture;
	
	private Camera camera = new Camera();
	private FloatMatrix rotationMatrix;

	/*
	 * (non-Javadoc)
	 * 
	 * @see earth.editor.AbstractEarthEditorViewer#init()
	 */
	protected void init() {
		initParams();
		initTexture();
		initShaders();
		initBuffers();
		
		initControls();
	}

	/**
	 * Initialized the params of WebGL.
	 */
	private void initParams() {
		glContext.viewport(0, 0, webGLCanvas.getOffsetWidth(), webGLCanvas.getOffsetHeight());
		
		// clear with color black
		glContext.clearColor(0.5f, 0.5f, 0.5f, 0.9f);

		// clear the whole image
		glContext.clearDepth(1.0f);

		// enable the depth test
		glContext.enable(WebGLRenderingContext.DEPTH_TEST);
		glContext.depthFunc(WebGLRenderingContext.LEQUAL);
	}
	
	/**
	 * Initializes the controls of the example.
	 */
	private void initControls() {
		webGLCanvas.addMouseMoveHandler(camera);		
		webGLCanvas.addMouseDownHandler(camera);		
		webGLCanvas.addMouseUpHandler(camera);
		
		webGLCanvas.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_PAGEUP) {
					translateZ += 0.1f;
					event.stopPropagation();
					event.preventDefault();
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_PAGEDOWN) {
					translateZ -= 0.1f;
					event.stopPropagation();
					event.preventDefault();
				}
			}
		});
	}


	/**
	 * Initializes the vertexBuffer containing the vertex and color coordinates.
	 */
	private void initBuffers() {
		vertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(mesh.getVertices()),
				WebGLRenderingContext.STATIC_DRAW);
		vertexTextureCoordBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexTextureCoordBuffer);
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array.create(mesh.getTexCoords()), WebGLRenderingContext.STATIC_DRAW);
		
		// create the indexBuffer
		if(mesh instanceof IndexedMesh){
		    indexBuffer = glContext.createBuffer();
		    glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, indexBuffer);
		    int[] indices = ((IndexedMesh)mesh).getIndices();
		    glContext.bufferData(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER,
				Uint16Array.create(indices),
				WebGLRenderingContext.STATIC_DRAW);				    
		}
		checkErrors();
	}

	/**
	 * Checks the WebGL Errors and throws an exception if there is an error.
	 */
	private void checkErrors() {
		int error = glContext.getError();
		if (error != WebGLRenderingContext.NO_ERROR) {
			String message = "WebGL Error: " + error;
			GWT.log(message, null);
			throw new RuntimeException(message);
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see earth.editor.AbstractEarthEditorViewer#draw()
	 */
	protected void draw() {
		// Clear the color vertexBuffer and the depth vertexBuffer
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

		glContext.vertexAttribPointer(vertexPositionAttribute, 3,
				WebGLRenderingContext.FLOAT, false, 0, 0);
		
		// Load the vertex data
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);
		
		// Load the texture coordinates data
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexTextureCoordBuffer);
		glContext.vertexAttribPointer(textureCoordAttribute, 2, WebGLRenderingContext.FLOAT, false, 0, 0);
		
		if(mesh instanceof IndexedMesh){
		    glContext.bindBuffer(WebGLRenderingContext.ELEMENT_ARRAY_BUFFER, indexBuffer);
		}
		
		perspectiveMatrix = MatrixUtil.createPerspectiveMatrix(45, 1.0f, 0.1f, 100);
		translationMatrix = MatrixUtil.createTranslationMatrix(0, 0, translateZ);
		rotationMatrix = MatrixUtil.createRotationMatrix(camera.getRotationXAxis(), camera.getRotationYAxis(), 0);
		
		resultingMatrix = perspectiveMatrix.multiply(translationMatrix).multiply(rotationMatrix);;
		

		glContext.uniformMatrix4fv(projectionMatrixUniform, false, resultingMatrix.getColumnWiseFlatData());
		
		// Bind the texture to texture unit 0
		glContext.activeTexture(WebGLRenderingContext.TEXTURE0);
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);

		// Point the uniform sampler to texture unit 0
		glContext.uniform1i(textureUniform, 0);
		if(mesh instanceof IndexedMesh){
		    glContext.drawElements(WebGLRenderingContext.TRIANGLES, ((IndexedMesh)mesh).getIndices().length, WebGLRenderingContext.UNSIGNED_SHORT, 0);
		}
		else{
		    glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, mesh.getVertices().length/3);
		}
				
		glContext.flush();
		checkErrors();
	}
	
	/**
	 * Initializes the texture of this example.
	 */
	private void initTexture() {
		texture = glContext.createTexture();
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
		final Image img = getImage(Resources.INSTANCE.texture());
		img.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				RootPanel.get().remove(img);
				GWT.log("texture image loaded", null);
				glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, texture);
				glContext.texImage2D(WebGLRenderingContext.TEXTURE_2D, 0, WebGLRenderingContext.RGBA, WebGLRenderingContext.RGBA, WebGLRenderingContext.UNSIGNED_BYTE, img.getElement());
			}
		});
		checkErrors();
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MAG_FILTER, WebGLRenderingContext.LINEAR);
		glContext.texParameteri(WebGLRenderingContext.TEXTURE_2D, WebGLRenderingContext.TEXTURE_MIN_FILTER, WebGLRenderingContext.LINEAR);
		glContext.bindTexture(WebGLRenderingContext.TEXTURE_2D, null);
		checkErrors();
	}

	/**
	 * Converts ImageResource to Image.
	 * @param imageResource
	 * @return {@link Image} to be used as a texture
	 */
	public Image getImage(final ImageResource imageResource) {
		final Image img = new Image();
		img.setVisible(false);
		RootPanel.get().add(img);

		img.setUrl(imageResource.getURL());
	
		return img;
	}	
	
	/**
	 * Creates the ShaderProgram used by the editor to render.
	 */
	private void initShaders() {
		// Create the Resources
		WebGLShader fragmentShader = getShader(WebGLRenderingContext.FRAGMENT_SHADER, Resources.INSTANCE.fragmentShader().getText());
		log("Created fragment shader");
		
		WebGLShader vertexShader = getShader(WebGLRenderingContext.VERTEX_SHADER, Resources.INSTANCE.vertexShader().getText());
		log("Created vertex shader");
		if (vertexShader == null || fragmentShader == null) {
			log("Shader error");
			throw new RuntimeException("shader error");
		}

		// create the ShaderProgram and attach the Resources
		shaderProgram = glContext.createProgram();
		if (shaderProgram == null || glContext.getError() != WebGLRenderingContext.NO_ERROR) {
			log("Program errror");
			throw new RuntimeException("program error");
		}

		log("Shader program created");
		glContext.attachShader(shaderProgram, vertexShader);
		log("vertex shader attached to shader program");
		glContext.attachShader(shaderProgram, fragmentShader);
		log("fragment shader attached to shader program");

		// Bind vertexPosition to attribute 0
		glContext.bindAttribLocation(shaderProgram, 0, "vertexPosition");
		// Bind texPosition to attribute 1
		glContext.bindAttribLocation(shaderProgram, 1, "texPosition");
		
		// Link the Shader Program
		glContext.linkProgram(shaderProgram);
		if (!glContext.getProgramParameterb(shaderProgram,
				WebGLRenderingContext.LINK_STATUS)) {
			throw new RuntimeException("Could not initialise shaders: " + glContext.getProgramInfoLog (shaderProgram));
		}
		log("Shader program linked");
		

		// Set the ShaderProgram active
		glContext.useProgram(shaderProgram);

		vertexPositionAttribute = glContext.getAttribLocation(shaderProgram, "vertexPosition");
		glContext.enableVertexAttribArray(vertexPositionAttribute);
		
		textureCoordAttribute = glContext.getAttribLocation(shaderProgram, "texPosition");
	    glContext.enableVertexAttribArray(textureCoordAttribute);
		
		// get the position of the projectionMatrix uniform.
		projectionMatrixUniform = glContext.getUniformLocation(shaderProgram, "projectionMatrix");
		
		// get the position of the tex uniform.
		textureUniform = glContext.getUniformLocation(shaderProgram, "tex");
		
		checkErrors();
	}

	/**
	 * Creates an Shader instance defined by the ShaderType and the source.
	 * 
	 * @param type
	 *            the type of the shader to create
	 * @param source
	 *            the source of the shader
	 * @return the created Shader instance.
	 */
	private WebGLShader getShader(int type, String source) {
		WebGLShader shader = glContext.createShader(type);

		glContext.shaderSource(shader, source);
		glContext.compileShader(shader);

		// check if the Shader is successfully compiled
		if (!glContext.getShaderParameterb(shader,
				WebGLRenderingContext.COMPILE_STATUS)) {
			throw new RuntimeException(glContext.getShaderInfoLog(shader));
		}

		return shader;
	}
}
