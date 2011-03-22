
package earth.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.resources.client.ClientBundle.Source;

public interface Resources extends ClientBundle {

	/** The instance of the Resources ClientBundle. */
	public static Resources INSTANCE = GWT.create(Resources.class);

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

	/**
	 * The texture to use in the example.
	 * 
	 * @return the image to use as texture.
	 */
	@Source(value = { "textureMapa.gif" })
	ImageResource texture();
}
