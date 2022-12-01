package com.orangetoolz.fileparsing.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class FileProperty {

    @Value("${file.location.input}")
    private String inputFileLocation;

    @Value("${file.location.output}")
    private String outputFileLocation;

    @Value("${chunk.size.db.write}")
    private int dbWriteChunkSize;

    @Value("${chunk.size.file.write}")
    private int fileWriteChunkSize;
}
