
package earth.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface Shaders extends ClientBundle {

	/** The instance of the Shaders ClientBundle. */
	public static Shaders INSTANCE = GWT.create(Shaders.class);

	/**
	 * The fragment shader to use in the example.
	 * 
	 * @return the source of the fragment shader.
	 */
	@Source(value = { "fragment-shader.txt" })
	TextResource fragmentShader();

	/**
	 * The vertex shader to use in the example.
	 * 
	 * @return the source of the vertex shader.
	 */
	@Source(value = { "vertex-shader.txt" })
	TextResource vertexShader();

}
