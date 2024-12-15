package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;
import ch.supsi.os.backend.business.NegativeTransformation;

public class NegativeCommand implements TransformationCommand{

    private final ImageTransformationStrategy transformation;

    public NegativeCommand() {
        this.transformation = new NegativeTransformation();
    }

    @Override
    public void execute(ImageModel imageModel) {
        transformation.applyTransformation(imageModel);
    }
}
