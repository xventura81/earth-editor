package earth.editor.figure;

import java.util.ArrayList;
import java.util.List;

import earth.editor.ConversionUtils;

/**
 * @author Rob
 *
 */
public class SphereFactory {
    /**
     * Constructs a new sphere with the given scale.
     * @param latitudeBands
     * @param longitudeBands
     * @param radius
     * @return sphere
     */
    public static Mesh createNewInstance(int latitudeBands, int longitudeBands, int radius){
	IndexedMesh mesh = new IndexedMesh();
	List<Float> vertexNormalsList = new ArrayList<Float>();
	List<Float> texCoordsList = new ArrayList<Float>();
	List<Float> verticesList = new ArrayList<Float>();

	for (int latNumber = 0; latNumber <= latitudeBands; latNumber++) {
	    double theta = latNumber * Math.PI / latitudeBands;
	    double sinTheta = Math.sin(theta);
	    double cosTheta = Math.cos(theta);

	    for (int longNumber = 0; longNumber <= longitudeBands; longNumber++) {
		double phi = longNumber * 2 * Math.PI / longitudeBands;
		double sinPhi = Math.sin(phi);
		double cosPhi = Math.cos(phi);

		double x = cosPhi * sinTheta;
		double y = cosTheta;
		double z = sinPhi * sinTheta;
		double u = 1 - ((double)longNumber / (double)longitudeBands);
		double v = (double)latNumber / (double)latitudeBands;

		vertexNormalsList.add((float) x);
		vertexNormalsList.add((float) y);
		vertexNormalsList.add((float) z);
		texCoordsList.add((float) u);
		texCoordsList.add((float) v);
		verticesList.add((float) (radius * x));
		verticesList.add((float) (radius * y));
		verticesList.add((float) (radius * z));
	    }
	}

	List<Integer> indicesList = new ArrayList<Integer>();
	for (int latNumber = 0; latNumber < latitudeBands; latNumber++) {
	    for (int longNumber = 0; longNumber < longitudeBands; longNumber++) {
		int first = ((latNumber * (longitudeBands + 1)) + longNumber);
		int second = (first + longitudeBands + 1);
		indicesList.add(first);
		indicesList.add(second);
		indicesList.add((first + 1));

		indicesList.add(second);
		indicesList.add( second + 1);
		indicesList.add(first + 1);
	    }
	}

	mesh.verticesArray = ConversionUtils.floatListToFloatArray(verticesList);
	mesh.texCoordsArray = ConversionUtils.floatListToFloatArray(texCoordsList);
	mesh.vertexNormalsArray = ConversionUtils.floatListToFloatArray(vertexNormalsList);
	mesh.indices = ConversionUtils.integerListToIntegerArray(indicesList);

	return mesh;
    }


}
