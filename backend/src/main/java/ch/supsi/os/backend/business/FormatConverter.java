package ch.supsi.os.backend.business;

public interface FormatConverter {
    boolean supports(String magicNumber);
    ImageModel convert(ImageModel sourceImage);
}
