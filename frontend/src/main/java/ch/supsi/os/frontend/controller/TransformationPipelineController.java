package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;

import java.util.ArrayList;
import java.util.List;

public class TransformationPipelineController {
    private static TransformationPipelineController instance;
    private final List<ImageTransformationStrategy> transformationPipeline;

    private TransformationPipelineController() {
        transformationPipeline = new ArrayList<>();
    }

    public static TransformationPipelineController getInstance() {
        if (instance == null) {
            instance = new TransformationPipelineController();
        }
        return instance;
    }

    public void addTransformation(ImageTransformationStrategy transformation) {
        transformationPipeline.add(transformation);
    }

    public void applyPipeline(ImageModel imageModel) {
        for (ImageTransformationStrategy transformation : transformationPipeline) {
            transformation.applyTransformation(imageModel);
        }
    }

    public void clearPipeline() {
        transformationPipeline.clear();
    }

    public String getPipelineDescription() {
        StringBuilder description = new StringBuilder();
        for (ImageTransformationStrategy transformation : transformationPipeline) {
            description.append(transformation.getClass().getSimpleName()).append("\n");
        }
        return description.toString();
    }
}
