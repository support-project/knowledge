package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;

import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

@DI(instance = Instance.Singleton)
public class DiffLogic {

    public static DiffLogic get() {
        return Container.getComp(DiffLogic.class);
    }

    public List<String> diff(String content, String content2) {
        List<String> changes = new ArrayList<>();
        List<String> oldLines = loadLines(content);
        List<String> newLines = loadLines(content2);

        Patch patch = DiffUtils.diff(oldLines, newLines);
        for (Delta delta : patch.getDeltas()) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("[History line-(%d)]", delta.getOriginal().getPosition() + 1));
            builder.append("\n");
            for (Object line : delta.getOriginal().getLines()) {
                builder.append(line);
                builder.append("\n");
            }

            builder.append("\n");
            builder.append("↓");
            builder.append("\n");

            builder.append(String.format("[Now line-(%d)]", delta.getRevised().getPosition() + 1));
            builder.append("\n");
            for (Object line : delta.getRevised().getLines()) {
                builder.append(line);
                builder.append("\n");
            }
            builder.append("\n\n"); // 次の差分表示までの間に2行あける

            changes.add(builder.toString());
        }
        return changes;
    }

    private List<String> loadLines(String content) {
        List<String> strings = new ArrayList<String>();
        String[] splits = content.split("\r\n");
        for (String string : splits) {
            String[] split2 = string.split("\n");
            for (String string2 : split2) {
                String[] split3 = string2.split("\r");
                for (String string3 : split3) {
                    strings.add(string3);
                }
            }
        }
        return strings;
    }

}
