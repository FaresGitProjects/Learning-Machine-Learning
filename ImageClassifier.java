import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.List;


public class ImageClassifier {

    private MultiLayerNetwork model;
    private int seed = 123;
    private double learningRate = 0.01;
    private int batchSize = 50;
    private int nEpochs = 10;

    public ImageClassifier() throws Exception {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.NESTEROVS)
                .list()
                .layer(0, new DenseLayer.Builder()
                        // .nIn(numInputs)
                        // .nOut(numHiddenNodes)
                        .weightInit(WeightInit.XAVIER)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .weightInit(WeightInit.XAVIER)
                        .activation(Activation.SOFTMAX)
                        // .nIn(numHiddenNodes)
                        // .nOut(numOutputs)
                        .build())
                .backprop(true)
                .build();
        model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(10));
    }

    public void train(List<DataSet> trainingData) {
        for (int i = 0; i < nEpochs; i++) {
            model.fit(new ListDataSetIterator(trainingData,batchSize));
        }
    }

    public void saveModel(File modelFile) throws Exception {
        ModelSerializer.writeModel(model, modelFile, true);
    }
}
