
package earth.editor.maths;

/**
 * Represents a vector with two elements.
 */
public class Vector2f implements Vectorf {
	private float u;
	private float v;

	/**
	 * Constructs a new instance of the Vector2f with the given coordinates to
	 * set.
	 * 
	 * @param u
	 * @param v
	 */
	public Vector2f(float u, float v) {
		super();
		this.u = u;
		this.v = v;
	}

	/**
	 * Returns the u coordinate.
	 * 
	 * @return the u coordinate
	 */
	public float getU() {
		return u;
	}

	/**
	 * Sets the u coordinate.
	 * 
	 * @param u
	 *            the u coordinate to set
	 */
	public void setU(float u) {
		this.u = u;
	}

	/**
	 * Returns the v coordinate.
	 * 
	 * @return the v coordinate
	 */
	public float getV() {
		return v;
	}

	/**
	 * Sets the v coordinate.
	 * 
	 * @param v
	 *            the v coordinate to set
	 */
	public void setV(float v) {
		this.v = v;
	}

	/* (non-Javadoc)
	 * @see com.googlecode.gwtgl.example.client.util.math.Vector#multiply(float)
	 */
	public Vectorf multiply(float scalar) {
		return new Vector2f(this.u * scalar, this.v * scalar);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.gwtgl.example.client.util.math.Vector#length()
	 */
	public float length() {
		return (float) Math.sqrt(this.u * this.u + this.v * this.v);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.gwtgl.example.client.util.math.Vector#toUnitVector()
	 */
	public Vectorf toUnitVector() {
		float length = length();
		return new Vector2f(this.u / length, this.v / length);
	}

	/* (non-Javadoc)
	 * @see com.googlecode.gwtgl.example.client.util.math.Vector#toArray()
	 */
	public float[] toArray() {
		return new float[] { this.u, this.v };
	}
}
