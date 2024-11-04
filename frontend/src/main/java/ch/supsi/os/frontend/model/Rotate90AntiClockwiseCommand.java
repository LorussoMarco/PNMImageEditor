package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;
import ch.supsi.os.backend.business.Rotate90AntiClockwiseTransformation;

public class Rotate90AntiClockwiseCommand implements TransformationCommand{
    private final ImageTransformationStrategy transformation;

    public Rotate90AntiClockwiseCommand() {
        this.transformation = new Rotate90AntiClockwiseTransformation();
    }

    @Override
    public void execute(ImageModel imageModel) {
        transformation.applyTransformation(imageModel);
    }


    @Override
    public String getDescription() {
        return "Rotate 90 anticlockwise applied";
    }
}
