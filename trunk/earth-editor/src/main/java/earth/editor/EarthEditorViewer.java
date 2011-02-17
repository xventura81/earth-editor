
package earth.editor;

import static com.google.gwt.core.client.GWT.log;

import com.googlecode.gwtgl.array.Float32Array;
import com.googlecode.gwtgl.binding.WebGLBuffer;
import com.googlecode.gwtgl.binding.WebGLProgram;
import com.googlecode.gwtgl.binding.WebGLRenderingContext;
import com.googlecode.gwtgl.binding.WebGLShader;
import com.googlecode.gwtgl.binding.WebGLUniformLocation;

/**
 * Example that shows the Editor created with the GwtGL binding.
 */
public class EarthEditorViewer extends AbstractEarthEditorViewer {

	private WebGLProgram shaderProgram;
	private int vertexPositionAttribute;
	private FloatMatrix4x4 projectionMatrix;
	private WebGLBuffer vertexBuffer;
	private WebGLUniformLocation projectionMatrixUniform;

	/*
	 * (non-Javadoc)
	 * 
	 * @see earth.editor.AbstractEarthEditorViewer#init()
	 */
	@Override
	protected void init() {
		createShaderProgram();
		initParams();
		initProjectionMatrix();
		initVertexBuffer();
	}

	/**
	 * Initialized the params of WebGL.
	 */
	private void initParams() {
		glContext.viewport(0, 0, webGLCanvas.getOffsetWidth(), webGLCanvas.getOffsetHeight());
		
		// clear with color black
		glContext.clearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// clear the whole image
		glContext.clearDepth(1.0f);

		// enable the depth test
		glContext.enable(WebGLRenderingContext.DEPTH_TEST);
		glContext.depthFunc(WebGLRenderingContext.LEQUAL);
	}

	/**
	 * Initializes the buffer containing the vertex coordinates.
	 */
	private void initVertexBuffer() {
		// One Triangle with 3 Points à 3 coordinates
		vertexBuffer = glContext.createBuffer();
		glContext.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, vertexBuffer);
		float[] vertices = new float[] { 0.0f, 1.0f, -5.0f, // x y z des ersten
				// Dreieckpunktes
				-1.0f, -1.0f, -5.0f, // x y z des zweiten Dreieckpunktes
				1.0f, -1.0f, -5.0f // x y z des dritten Dreieckpunktes
		};
		glContext.bufferData(WebGLRenderingContext.ARRAY_BUFFER,
				Float32Array.create(vertices),
				WebGLRenderingContext.STATIC_DRAW);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see earth.editor.AbstractEarthEditorViewer#draw()
	 */
	@Override
	protected void draw() {
		glContext.clear(WebGLRenderingContext.COLOR_BUFFER_BIT | WebGLRenderingContext.DEPTH_BUFFER_BIT);

		glContext.vertexAttribPointer(vertexPositionAttribute, 3, WebGLRenderingContext.FLOAT, false, 0, 0);

		glContext.uniformMatrix4fv(projectionMatrixUniform, false, projectionMatrix.getColumnWiseFlatData());
		glContext.drawArrays(WebGLRenderingContext.TRIANGLES, 0, 3);
	}

	/**
	 * initializes the projection matrix used in this editor.
	 */
	private void initProjectionMatrix() {
		projectionMatrix = MatrixUtil.createPerspectiveMatrix(45, 1.0f, 0.1f, 100);
	}

	/**
	 * Creates the ShaderProgram used by the editor to render.
	 */
	private void createShaderProgram() {
		// Create the Shaders
		WebGLShader fragmentShader = getShader(WebGLRenderingContext.FRAGMENT_SHADER, Shaders.INSTANCE.fragmentShader().getText());
		log("Created fragment shader");
		
		WebGLShader vertexShader = getShader(WebGLRenderingContext.VERTEX_SHADER, Shaders.INSTANCE.vertexShader().getText());
		log("Created vertex shader");
		if (vertexShader == null || fragmentShader == null) {
			log("Shader error");
			throw new RuntimeException("shader error");
		}

		// create the ShaderProgram and attach the Shaders
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

		// Link the Shader Program
		glContext.linkProgram(shaderProgram);
		if (!glContext.getProgramParameterb(shaderProgram,
				WebGLRenderingContext.LINK_STATUS)) {
			throw new RuntimeException("Could not initialise shaders: " + glContext.getProgramInfoLog (shaderProgram));
		}
		log("Shader program linked");
		

		// Set the ShaderProgram active
		glContext.useProgram(shaderProgram);

		// Get the position of the
		vertexPositionAttribute = glContext.getAttribLocation(shaderProgram,
				"vertexPosition");
		glContext.enableVertexAttribArray(vertexPositionAttribute);

		// get the position of the projectionMatrix uniform.
		projectionMatrixUniform = glContext.getUniformLocation(shaderProgram,
				"projectionMatrix");
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
