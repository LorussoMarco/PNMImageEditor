package ch.supsi.os.frontend.controller;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;
import ch.supsi.os.backend.business.*;
import ch.supsi.os.frontend.view.LogBarViewFxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformationPipelineController {
    private static TransformationPipelineController instance;
    private final List<ImageTransformationStrategy> transformationPipeline;
    private final LocalizationController localizationController;

    // Mappa per associare le classi di trasformazioni alle chiavi di localizzazione
    private final Map<Class<? extends ImageTransformationStrategy>, String> transformationKeys;

    private TransformationPipelineController() {
        transformationPipeline = new ArrayList<>();
        localizationController = LocalizationController.getInstance();

        // Popola la mappa delle trasformazioni
        transformationKeys = new HashMap<>();
        transformationKeys.put(FlipUpsideDownTransformation.class, "transformation.flipupsidedown");
        transformationKeys.put(FlipSideToSideTransformation.class, "transformation.flipsidetoside");
        transformationKeys.put(Rotate90ClockwiseTransformation.class, "transformation.rotateclockwise");
        transformationKeys.put(Rotate90AntiClockwiseTransformation.class, "transformation.rotateanticlockwise");
        transformationKeys.put(NegativeTransformation.class, "transformation.negative");
    }

    public static TransformationPipelineController getInstance() {
        if (instance == null) {
            instance = new TransformationPipelineController();
        }
        return instance;
    }

    public void addTransformation(ImageTransformationStrategy transformation) {
        if (!transformationPipeline.contains(transformation)) {
            transformationPipeline.add(transformation);
        }
    }

    public void applyPipeline(ImageModel imageModel) {
        if (imageModel == null) return;

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
            String transformationName = getLocalizedTransformationName(transformation.getClass());
            description.append(transformationName).append("\n");
        }
        return description.toString();
    }

    private String getLocalizedTransformationName(Class<? extends ImageTransformationStrategy> transformationClass) {
        String key = transformationKeys.getOrDefault(transformationClass, "transformation.unknown");
        return localizationController.getLocalizedText(key);
    }
}
