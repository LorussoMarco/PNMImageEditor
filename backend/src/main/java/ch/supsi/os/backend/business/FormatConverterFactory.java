package ch.supsi.os.backend.business;

import java.util.List;

public class FormatConverterFactory {
    private final List<FormatConverter> converters;

    public FormatConverterFactory() {
        converters = List.of(
                new PpmFormatConverter(),
                new PgmFormatConverter(),
                new PbmFormatConverter()
        );
    }

    public FormatConverter getConverter(String magicNumber) {
        return converters.stream()
                .filter(converter -> converter.supports(magicNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported format: " + magicNumber));
    }
}
