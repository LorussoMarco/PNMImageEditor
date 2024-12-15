package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.FlipUpsideDownTransformation;
import ch.supsi.os.backend.business.ImageModel;
import ch.supsi.os.backend.business.ImageTransformationStrategy;

public class FlipUpsideDownCommand implements TransformationCommand {
    private final ImageTransformationStrategy transformation;

    public FlipUpsideDownCommand() {
        this.transformation = new FlipUpsideDownTransformation();
    }

    @Override
    public void execute(ImageModel imageModel) {
        transformation.applyTransformation(imageModel);
    }
}
