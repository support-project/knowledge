package org.support.project.knowledge.indexer.impl;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.support.project.common.config.ConfigLoader;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.indexer.Indexer;
import org.support.project.knowledge.indexer.IndexingValue;

public class LuceneIndexer implements Indexer {
    /** ログ */
    private static Log log = LogFactory.getLog(LuceneIndexer.class);

    public static final String FIELD_LABEL_TYPE = "type";
    public static final String FIELD_LABEL_ID = "id";
    public static final String FIELD_LABEL_TITLE = "title";
    public static final String FIELD_LABEL_CONTENTS = "contents";
    public static final String FIELD_LABEL_TAGS = "tags";
    public static final String FIELD_LABEL_USERS = "users";
    public static final String FIELD_LABEL_GROUPS = "groups";
    public static final String FIELD_LABEL_CREATE_USER = "creator";
    public static final String FIELD_LABEL_TIME = "time";
    public static final String FIELD_LABEL_TEMPLATE = "template";

    // private Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_4_10_2);
    private Analyzer analyzer = new JapaneseAnalyzer();

    private String getIndexPath() {
        AppConfig appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
        log.debug("lucene index: " + appConfig.getIndexPath());
        return appConfig.getIndexPath();
    }

    public void writeIndex(IndexingValue indexingValue) throws Exception {
        synchronized (FIELD_LABEL_TYPE) {
            boolean create = true;
            File indexDir = new File(getIndexPath());
            if (!indexDir.exists()) {
                indexDir.mkdirs();
            } else {
                String[] children = indexDir.list();
                if (children != null && children.length > 0) {
                    create = false;
                }
            }
            Directory dir = FSDirectory.open(indexDir);
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
            if (create) {
                iwc.setOpenMode(OpenMode.CREATE);
            } else {
                iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
            }
            IndexWriter writer = null;
            try {
                writer = new IndexWriter(dir, iwc);
                addDoc(writer, indexingValue);
                writer.commit();
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }
    }

    private void addDoc(IndexWriter writer, IndexingValue indexingValue) throws Exception {
        Document doc = new Document();
        // type
        Field typeField = new IntField(FIELD_LABEL_TYPE, indexingValue.getType(), Field.Store.YES);
        doc.add(typeField);
        // id
        Field idField = new StringField(FIELD_LABEL_ID, indexingValue.getId(), Field.Store.YES);
        doc.add(idField);
        // template
        if (indexingValue.getTemplate() != null) {
            Field templateField = new IntField(FIELD_LABEL_TEMPLATE, indexingValue.getTemplate(), Field.Store.YES);
            doc.add(templateField);
        }
        // タイトル
        doc.add(new TextField(FIELD_LABEL_TITLE, indexingValue.getTitle(), Field.Store.YES));
        // 内容
        doc.add(new TextField(FIELD_LABEL_CONTENTS, indexingValue.getContents(), Field.Store.YES));

        // タグ
        Field tagField = new TextField(FIELD_LABEL_TAGS, indexingValue.getTags(), Field.Store.YES);
        doc.add(tagField);
        // アクセスできるユーザ
        Field userField = new TextField(FIELD_LABEL_USERS, indexingValue.getUsers().toString(), Field.Store.YES);
        doc.add(userField);
        // アクセスできるグループ
        Field groupField = new TextField(FIELD_LABEL_GROUPS, indexingValue.getGroups().toString(), Field.Store.YES);
        doc.add(groupField);

        // 登録者
        Field creatorField = new StringField(FIELD_LABEL_CREATE_USER, indexingValue.getCreator(), Field.Store.YES);
        doc.add(creatorField);

        // 時刻
        Field timeField = new LongField(FIELD_LABEL_TIME, indexingValue.getTime(), Field.Store.YES);
        doc.add(timeField);

        if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
            log.debug("adding " + indexingValue.getId());
            writer.addDocument(doc);
        } else {
            log.debug("updating " + indexingValue.getId());
            writer.updateDocument(new Term(FIELD_LABEL_ID, indexingValue.getId()), doc);
        }
    }

    @Override
    public void deleteItem(String id) throws Exception {
        boolean create = true;
        File indexDir = new File(getIndexPath());
        if (!indexDir.exists()) {
            indexDir.mkdirs();
        } else {
            String[] children = indexDir.list();
            if (children != null && children.length > 0) {
                create = false;
            }
        }
        Directory dir = FSDirectory.open(indexDir);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        if (create) {
            iwc.setOpenMode(OpenMode.CREATE);
        } else {
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        }
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(dir, iwc);
            writer.deleteDocuments(new Term(FIELD_LABEL_ID, id));
            writer.commit();

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public void deleteOnCreator(Integer creator) throws Exception {
        boolean create = true;
        File indexDir = new File(getIndexPath());
        if (!indexDir.exists()) {
            indexDir.mkdirs();
        } else {
            String[] children = indexDir.list();
            if (children != null && children.length > 0) {
                create = false;
            }
        }
        Directory dir = FSDirectory.open(indexDir);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
        if (create) {
            iwc.setOpenMode(OpenMode.CREATE);
        } else {
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
        }
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(dir, iwc);
            writer.deleteDocuments(new Term(FIELD_LABEL_CREATE_USER, StringUtils.zeroPadding(creator, IndexingValue.ID_ZEROPADDING_DIGIT)));
            writer.commit();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
