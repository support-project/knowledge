package org.support.project.knowledge.parser.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.ParsingReader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.parser.Parser;
import org.support.project.knowledge.vo.ParseResult;

@DI(instance = Instance.Prototype)
public class TikaParser implements Parser {
    /** ログ */
    private static Log log = LogFactory.getLog(MethodHandles.lookup());

    public static TikaParser get() {
        return Container.getComp(TikaParser.class);
    }

    public String read(File file) throws Exception {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = getReader(file.toPath());
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0) {
                    builder.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            // //読み込めない場合は、catchして読み込み終了(エラーにしない)
            StringBuilder msg = new StringBuilder();
            msg.append("read error. skip read.\n");
            msg.append("\t[Path]      ").append(file.getAbsolutePath()).append("\n");
            msg.append("\t[Exception] ").append(e.getClass().getName()).append("\n");
            msg.append("\t[Message]   ").append(e.getMessage()).append("\n");
            log.warn(msg.toString());
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return builder.toString();
    }

    public BufferedReader getReader(Path path) throws Exception {
        AutoDetectParser parser = new AutoDetectParser();
        ParseContext context = new ParseContext();
        Metadata metadata = new Metadata();
        Path filename = path.getFileName();
        if (filename != null) {
            metadata.set(Metadata.RESOURCE_NAME_KEY, filename.toString());
        }

        InputStream inputStream = Files.newInputStream(path);
        BufferedReader reader = new BufferedReader(new ParsingReader(parser, inputStream, metadata, context));
        return reader;
    }

    @Override
    public ParseResult parse(File file) throws Exception {
        String text = read(file);
        ParseResult parseResult = new ParseResult();
        parseResult.setText(text);
        return parseResult;
    }

}
