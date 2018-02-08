package org.support.project.knowledge.parser;

import java.io.IOException;

import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.knowledge.config.FileType;
import org.support.project.knowledge.parser.impl.TikaParser;

public final class ParserFactory {

    private ParserFactory() {
    }

    public static Parser getParser(String filePath) throws IOException {
        // String mime = Files.probeContentType(Paths.get(filePath));
        // FileType fileType = getFileType(mime, filePath);
        // if (fileType == FileType.NotCovered) {
        // return null;
        // }
        // if (fileType == FileType.Text) {
        // return TextParser.get();
        // } else if (fileType == FileType.HTML) {
        // return TextParser.get();
        // } else if (fileType == FileType.Java) {
        // return TextParser.get();
        // }

        // いったん全て、Tikaでパースする
        return Container.getComp(TikaParser.class);
    }

    public static FileType getFileType(String mime, String path) {
        if (mime != null) {
            switch (mime) {
                case "text/plain":
                    return FileType.Text;

                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                case "application/vnd.ms-excel":
                    return FileType.Excel;

                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                case "application/msword":
                    return FileType.Word;

                case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
                case "application/vnd.ms-powerpoint":
                    return FileType.PowerPoint;

                case "application/pdf":
                    return FileType.PDF;

                case "text/html":
                    return FileType.HTML;

                default:
                    break;
            }
        } else {
            String extention = StringUtils.getExtension(path);
            if (extention != null && extention.equals(".java")) {
                return FileType.Java;
            }
        }
        return FileType.NotCovered;
    }

}
