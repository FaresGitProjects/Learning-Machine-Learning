import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;

public class ImageClassifier {

    private MultiLayerNetwork model;

    public ImageClassifier() throws Exception {
        File modelFile = new File("path/to/model.zip");
        model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
    }

    public void classify(File imageFile) throws Exception {
        // Load image data and convert to a feature vector
        // INDArray features = // ...

        // Perform forward pass through the model to get the output
        INDArray output = model.output(features);

        // Get the index of the highest output value (i.e., the predicted label)
        int label = Nd4j.argMax(output, 1).getInt(0);

        if (label == 0) {
            System.out.println("Image is a Hyena");
        } else {
            System.out.println("Image is a Lion");
        }
    }
}
