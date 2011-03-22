package earth.editor;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

/**
 * Simple camera class that can consume mouse events and provides camera rotation data.
 * @author Sönke Sothmann
 */
public class Camera implements MouseMoveHandler, MouseDownHandler, MouseUpHandler {

	int oldMouseX = 0;
	int oldMouseY = 0;
	boolean mouseDown = false;
	private int angleX;
	private int angleY;
	
	/**
	 * Get camera rotation on x axis
	 * @return rotation on x axis
	 */
	public int getRotationXAxis() {
		return angleX;
	}

	/**
	 * Get camera rotation on y axis
	 * @return rotation on y axis
	 */
	public int getRotationYAxis() {
		return angleY;
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		int mouseMoveX = event.getX()-oldMouseX;
		int mouseMoveY = event.getY()-oldMouseY;
		oldMouseX = event.getX();
		oldMouseY = event.getY();
		if(mouseDown){
			angleX -= (mouseMoveY/10f) % 360;
			angleY -= (mouseMoveX/10f) % 360;
		}
		
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		mouseDown = true;
		
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		mouseDown = false;
	}
}
