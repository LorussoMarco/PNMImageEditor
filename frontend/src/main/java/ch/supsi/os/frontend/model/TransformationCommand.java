package ch.supsi.os.frontend.model;

import ch.supsi.os.backend.business.ImageModel;

public interface TransformationCommand {
    void execute(ImageModel imageModel);
}