package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;
import ch.supsi.os.backend.business.Rotate90ClockwiseTransformation;

public class Rotate90ClockwiseCommand implements TransformationCommand{
    private final ImageTransformationStrategy transformation;

    public Rotate90ClockwiseCommand() {
        this.transformation = new Rotate90ClockwiseTransformation();
    }

    @Override
    public void execute(ImageModel imageModel) {
        transformation.applyTransformation(imageModel);
    }
}
