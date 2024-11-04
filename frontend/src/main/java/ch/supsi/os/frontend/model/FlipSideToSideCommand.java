package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.FlipSideToSideTransformation;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;

public class FlipSideToSideCommand implements TransformationCommand{

    private final ImageTransformationStrategy transformation;

    public FlipSideToSideCommand() {
        this.transformation = new FlipSideToSideTransformation();
    }

    @Override
    public void execute(ImageModel imageModel) {
        transformation.applyTransformation(imageModel);
    }

    @Override
    public String getDescription() {
        return "Flip side to side applied";
    }
}
