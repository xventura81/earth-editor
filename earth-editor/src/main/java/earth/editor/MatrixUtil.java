
package earth.editor;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Contains several helper methods to construct matrices like projection (etc).
 */
public final class MatrixUtil {
	private MatrixUtil() {
	}

	/**
	 * 
	 * @param fieldOfViewVertical
	 *            Vertikaler Öffnungswinkel in Grad
	 * @param aspectRatio
	 *            Verhältnis von Höhe zu Breite
	 * @param minimumClearance
	 *            Mindestabstand sichtbarer Punkte
	 * @param maximumClearance
	 *            Höchstabstand sichtbarer Punkte
	 * @return the created perspective matrix
	 */
	public static FloatMatrix4x4 createPerspectiveMatrix(
			int fieldOfViewVertical, float aspectRatio, float minimumClearance,
			float maximumClearance) {
		double fieldOfViewInRad = fieldOfViewVertical * Math.PI / 180.0;
		return new FloatMatrix4x4(new float[][] {
				new float[] {
						(float) (Math.tan(fieldOfViewInRad) / aspectRatio), 0,
						0, 0 },
				new float[] {
						0,
						(float) (1 / Math.tan(fieldOfViewVertical * Math.PI
								/ 180.0)), 0, 0 },
				new float[] {
						0,
						0,
						(minimumClearance + maximumClearance)
								/ (minimumClearance - maximumClearance),
						2 * minimumClearance * maximumClearance
								/ (minimumClearance - maximumClearance) },
				new float[] { 0, 0, -1, 0 } });
	};

	/**
	 * Creates a rotation matrix.
	 * 
	 * @param angleX
	 *            the angle in degrees for the rotation around the x axis
	 * @param angleY
	 *            the angle in degrees for the rotation around the y axis
	 * @param angleZ
	 *            the angle in degrees for the rotation around the z axis
	 * @return the created matrix
	 */
	public static FloatMatrix4x4 createRotationMatrix(int angleX, int angleY,
			int angleZ) {
		return createRotationMatrixX(angleX).multiply(
				createRotationMatrixY(angleY)).multiply(
				createRotationMatrixZ(angleZ));
	}

	private static FloatMatrix4x4 createRotationMatrixX(int angle) {
		double angleInRad = angle * (Math.PI / 180.0);

		// 1 0 0 0
		//
		// 0 cos(q) sin(q) 0
		//
		// 0 -sin(q) cos(q) 0
		//
		// 0 0 0 1

		return new FloatMatrix4x4(new float[][] {
				new float[] { 1, 0, 0, 0 },
				new float[] { 0, (float) cos(angleInRad),
						(float) sin(angleInRad), 0 },
				new float[] { 0, (float) -sin(angleInRad),
						(float) cos(angleInRad), 0 },
				new float[] { 0, 0, 0, 1 } });
	}

	private static FloatMatrix4x4 createRotationMatrixY(int angle) {
		double angleInRad = angle * (Math.PI / 180.0);

		// cos(a) 0 -sin(a) 0
		//
		// 0 1 0 0
		//
		// sin(a) 0 cos(a) 0
		//
		// 0 0 0 1

		return new FloatMatrix4x4(new float[][] {
				new float[] { (float) cos(angleInRad), 0,
						(float) -sin(angleInRad), 0 },
				new float[] { 0, 1, 0, 0 },
				new float[] { (float) sin(angleInRad), 0.0f,
						(float) cos(angleInRad), 0.0f },
				new float[] { 0, 0, 0, 1 } });
	}

	private static FloatMatrix4x4 createRotationMatrixZ(int angle) {
		double angleInRad = angle * (Math.PI / 180.0);

		// cos(a) sin(a) 0 0
		//
		// -sin(a) cos(a) 0 0
		//
		// 0 0 1 0
		//
		// 0 0 0 1

		return new FloatMatrix4x4(new float[][] {
				new float[] { (float) cos(angleInRad), (float) sin(angleInRad),
						0, 0 },
				new float[] { (float) -sin(angleInRad),
						(float) cos(angleInRad), 0, 0 },
				new float[] { 0, 0, 1, 0 }, new float[] { 0, 0, 0, 1 } });
	}

	/**
	 * Creates a translation matrix.
	 * 
	 * @param translateX
	 *            the amount to translate parallel to the x axis
	 * @param translateY
	 *            the amount to translate parallel to the y axis
	 * @param translateZ
	 *            the amount to translate parallel to the z axis
	 * @return the created matrix
	 */
	public static FloatMatrix4x4 createTranslationMatrix(float translateX,
			float translateY, float translateZ) {
		return new FloatMatrix4x4(
				new float[][] { new float[] { 1, 0, 0, translateX },
						new float[] { 0, 1, 0, translateY },
						new float[] { 0, 0, 1, translateZ },
						new float[] { 0, 0, 0, 1 } });
	}
}
