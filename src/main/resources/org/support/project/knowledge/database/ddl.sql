-- ナレッジ更新履歴
drop table if exists KNOWLEDGE_HISTORIES cascade;

create table KNOWLEDGE_HISTORIES (
  KNOWLEDGE_ID bigint not null
  , HISTORY_NO integer not null
  , TITLE character varying(1024) not null
  , CONTENT text
  , PUBLIC_FLAG integer
  , TAG_IDS character varying(1024)
  , TAG_NAMES text
  , LIKE_COUNT bigint
  , COMMENT_COUNT integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_HISTORIES_PKC primary key (KNOWLEDGE_ID,HISTORY_NO)
) ;

-- 通知待ちキュー
drop table if exists NOTIFY_QUEUES cascade;

create table NOTIFY_QUEUES (
  HASH character varying(32) not null
  , TYPE integer not null
  , ID bigint not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint NOTIFY_QUEUES_PKC primary key (HASH)
) ;

-- 通知設定
drop table if exists NOTIFY_CONFIGS cascade;

create table NOTIFY_CONFIGS (
  USER_ID integer not null
  , NOTIFY_MAIL integer
  , NOTIFY_DESKTOP integer
  , MY_ITEM_COMMENT integer
  , MY_ITEM_LIKE integer
  , MY_ITEM_STOCK integer
  , TO_ITEM_SAVE integer
  , TO_ITEM_COMMENT integer
  , TO_ITEM_IGNORE_PUBLIC integer
  , STOCK_ITEM_SAVE integer
  , STOKE_ITEM_COMMENT integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint NOTIFY_CONFIGS_PKC primary key (USER_ID)
) ;

-- アカウントの画像
drop table if exists ACCOUNT_IMAGES cascade;

create table ACCOUNT_IMAGES (
  IMAGE_ID BIGSERIAL not null
  , USER_ID integer
  , FILE_NAME character varying(256)
  , FILE_SIZE double precision
  , FILE_BINARY BYTEA
  , EXTENSION character varying(256)
  , CONTENT_TYPE character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ACCOUNT_IMAGES_PKC primary key (IMAGE_ID)
) ;

create unique index IDX_ACCOUNT_IMAGES_USER_ID
  on ACCOUNT_IMAGES(USER_ID);

-- いいね
drop table if exists LIKES cascade;

create table LIKES (
  NO BIGSERIAL not null
  , KNOWLEDGE_ID bigint not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LIKES_PKC primary key (NO)
) ;

create index IDX_LIKES_KNOWLEDGE_ID
  on LIKES(KNOWLEDGE_ID);

-- コメント
drop table if exists COMMENTS cascade;

create table COMMENTS (
  COMMENT_NO BIGSERIAL not null
  , KNOWLEDGE_ID bigint not null
  , COMMENT text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint COMMENTS_PKC primary key (COMMENT_NO)
) ;

create index IDX_COMMENTS_KNOWLEDGE_ID
  on COMMENTS(KNOWLEDGE_ID);

-- 投票
drop table if exists VOTES cascade;

create table VOTES (
  VOTE_NO BIGSERIAL not null
  , KNOWLEDGE_ID bigint not null
  , VOTE_KIND integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint VOTES_PKC primary key (VOTE_NO)
) ;

create index IDX_VOTES_KNOWLEDGE_ID
  on VOTES(KNOWLEDGE_ID);

-- ナレッジの参照履歴
drop table if exists VIEW_HISTORIES cascade;

create table VIEW_HISTORIES (
  HISTORY_NO BIGSERIAL not null
  , KNOWLEDGE_ID bigint not null
  , VIEW_DATE_TIME timestamp not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint VIEW_HISTORIES_PKC primary key (HISTORY_NO)
) ;

create index IDX_VIEW_HISTORIES_KNOWLEDGE_ID
  on VIEW_HISTORIES(KNOWLEDGE_ID);

-- ストックしたナレッジ
drop table if exists STOCKS cascade;

create table STOCKS (
  USER_ID integer not null
  , KNOWLEDGE_ID bigint not null
  , COMMENT character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint STOCKS_PKC primary key (USER_ID,KNOWLEDGE_ID)
) ;

-- アクセス可能なグループ
drop table if exists KNOWLEDGE_GROUPS cascade;

create table KNOWLEDGE_GROUPS (
  KNOWLEDGE_ID bigint not null
  , GROUP_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_GROUPS_PKC primary key (KNOWLEDGE_ID,GROUP_ID)
) ;

-- アクセス可能なユーザ
drop table if exists KNOWLEDGE_USERS cascade;

create table KNOWLEDGE_USERS (
  KNOWLEDGE_ID bigint not null
  , USER_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_USERS_PKC primary key (KNOWLEDGE_ID,USER_ID)
) ;

-- ナレッジが持つタグ
drop table if exists KNOWLEDGE_TAGS cascade;

create table KNOWLEDGE_TAGS (
  KNOWLEDGE_ID bigint not null
  , TAG_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_TAGS_PKC primary key (KNOWLEDGE_ID,TAG_ID)
) ;

-- タグ
drop table if exists TAGS cascade;

create table TAGS (
  TAG_ID SERIAL not null
  , TAG_NAME character varying(128) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TAGS_PKC primary key (TAG_ID)
) ;

-- 添付ファイル
drop table if exists KNOWLEDGE_FILES cascade;

create table KNOWLEDGE_FILES (
  FILE_NO BIGSERIAL not null
  , KNOWLEDGE_ID bigint
  , COMMENT_NO bigint
  , FILE_NAME character varying(256)
  , FILE_SIZE double precision
  , FILE_BINARY BYTEA
  , PARSE_STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_FILES_PKC primary key (FILE_NO)
) ;

create index IDX_KNOWLEDGE_FILES_KNOWLEDGE_ID
  on KNOWLEDGE_FILES(KNOWLEDGE_ID);

-- ナレッジ
drop table if exists KNOWLEDGES cascade;

create table KNOWLEDGES (
  KNOWLEDGE_ID BIGSERIAL not null
  , TITLE character varying(1024) not null
  , CONTENT text
  , PUBLIC_FLAG integer
  , TAG_IDS character varying(1024)
  , TAG_NAMES text
  , LIKE_COUNT bigint
  , COMMENT_COUNT integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGES_PKC primary key (KNOWLEDGE_ID)
) ;

comment on table KNOWLEDGE_HISTORIES is 'ナレッジ更新履歴';
comment on column KNOWLEDGE_HISTORIES.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_HISTORIES.HISTORY_NO is '履歴番号';
comment on column KNOWLEDGE_HISTORIES.TITLE is 'タイトル';
comment on column KNOWLEDGE_HISTORIES.CONTENT is '内容';
comment on column KNOWLEDGE_HISTORIES.PUBLIC_FLAG is '公開区分';
comment on column KNOWLEDGE_HISTORIES.TAG_IDS is 'タグID一覧';
comment on column KNOWLEDGE_HISTORIES.TAG_NAMES is 'タグ名称一覧';
comment on column KNOWLEDGE_HISTORIES.LIKE_COUNT is 'いいね件数';
comment on column KNOWLEDGE_HISTORIES.COMMENT_COUNT is 'コメント件数';
comment on column KNOWLEDGE_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table NOTIFY_QUEUES is '通知待ちキュー';
comment on column NOTIFY_QUEUES.HASH is 'HASH';
comment on column NOTIFY_QUEUES.TYPE is '種類';
comment on column NOTIFY_QUEUES.ID is '通知する種類のID';
comment on column NOTIFY_QUEUES.INSERT_USER is '登録ユーザ';
comment on column NOTIFY_QUEUES.INSERT_DATETIME is '登録日時';
comment on column NOTIFY_QUEUES.UPDATE_USER is '更新ユーザ';
comment on column NOTIFY_QUEUES.UPDATE_DATETIME is '更新日時';
comment on column NOTIFY_QUEUES.DELETE_FLAG is '削除フラグ';

comment on table NOTIFY_CONFIGS is '通知設定';
comment on column NOTIFY_CONFIGS.USER_ID is 'ユーザID';
comment on column NOTIFY_CONFIGS.NOTIFY_MAIL is 'メール通知する';
comment on column NOTIFY_CONFIGS.NOTIFY_DESKTOP is 'デスクトップ通知する';
comment on column NOTIFY_CONFIGS.MY_ITEM_COMMENT is '自分が登録した投稿にコメントが登録されたら通知';
comment on column NOTIFY_CONFIGS.MY_ITEM_LIKE is '自分が登録した投稿にいいねが追加されたら通知';
comment on column NOTIFY_CONFIGS.MY_ITEM_STOCK is '自分が登録した投稿がストックされたら通知';
comment on column NOTIFY_CONFIGS.TO_ITEM_SAVE is '自分宛の投稿が更新されたら通知';
comment on column NOTIFY_CONFIGS.TO_ITEM_COMMENT is '自分宛の投稿にコメントが登録されたら通知';
comment on column NOTIFY_CONFIGS.TO_ITEM_IGNORE_PUBLIC is '自分宛の投稿で「公開」は除外';
comment on column NOTIFY_CONFIGS.STOCK_ITEM_SAVE is 'ストックしたナレッジが更新されたら通知';
comment on column NOTIFY_CONFIGS.STOKE_ITEM_COMMENT is 'ストックしたナレッジにコメントが登録されたら通知';
comment on column NOTIFY_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column NOTIFY_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column NOTIFY_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column NOTIFY_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column NOTIFY_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table ACCOUNT_IMAGES is 'アカウントの画像';
comment on column ACCOUNT_IMAGES.IMAGE_ID is 'IMAGE_ID';
comment on column ACCOUNT_IMAGES.USER_ID is 'ユーザID';
comment on column ACCOUNT_IMAGES.FILE_NAME is 'ファイル名';
comment on column ACCOUNT_IMAGES.FILE_SIZE is 'ファイルサイズ';
comment on column ACCOUNT_IMAGES.FILE_BINARY is 'バイナリ';
comment on column ACCOUNT_IMAGES.EXTENSION is '拡張子';
comment on column ACCOUNT_IMAGES.CONTENT_TYPE is 'CONTENT_TYPE';
comment on column ACCOUNT_IMAGES.INSERT_USER is '登録ユーザ';
comment on column ACCOUNT_IMAGES.INSERT_DATETIME is '登録日時';
comment on column ACCOUNT_IMAGES.UPDATE_USER is '更新ユーザ';
comment on column ACCOUNT_IMAGES.UPDATE_DATETIME is '更新日時';
comment on column ACCOUNT_IMAGES.DELETE_FLAG is '削除フラグ';

comment on table LIKES is 'いいね';
comment on column LIKES.NO is 'NO';
comment on column LIKES.KNOWLEDGE_ID is 'ナレッジID';
comment on column LIKES.INSERT_USER is '登録ユーザ';
comment on column LIKES.INSERT_DATETIME is '登録日時';
comment on column LIKES.UPDATE_USER is '更新ユーザ';
comment on column LIKES.UPDATE_DATETIME is '更新日時';
comment on column LIKES.DELETE_FLAG is '削除フラグ';

comment on table COMMENTS is 'コメント';
comment on column COMMENTS.COMMENT_NO is 'コメント番号';
comment on column COMMENTS.KNOWLEDGE_ID is 'ナレッジID';
comment on column COMMENTS.COMMENT is 'コメント';
comment on column COMMENTS.INSERT_USER is '登録ユーザ';
comment on column COMMENTS.INSERT_DATETIME is '登録日時';
comment on column COMMENTS.UPDATE_USER is '更新ユーザ';
comment on column COMMENTS.UPDATE_DATETIME is '更新日時';
comment on column COMMENTS.DELETE_FLAG is '削除フラグ';

comment on table VOTES is '投票';
comment on column VOTES.VOTE_NO is 'VOTE_NO';
comment on column VOTES.KNOWLEDGE_ID is 'ナレッジID';
comment on column VOTES.VOTE_KIND is '投票区分';
comment on column VOTES.INSERT_USER is '登録ユーザ';
comment on column VOTES.INSERT_DATETIME is '登録日時';
comment on column VOTES.UPDATE_USER is '更新ユーザ';
comment on column VOTES.UPDATE_DATETIME is '更新日時';
comment on column VOTES.DELETE_FLAG is '削除フラグ';

comment on table VIEW_HISTORIES is 'ナレッジの参照履歴';
comment on column VIEW_HISTORIES.HISTORY_NO is 'HISTORY_NO';
comment on column VIEW_HISTORIES.KNOWLEDGE_ID is 'ナレッジID';
comment on column VIEW_HISTORIES.VIEW_DATE_TIME is '日時';
comment on column VIEW_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column VIEW_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column VIEW_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column VIEW_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column VIEW_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table STOCKS is 'ストックしたナレッジ';
comment on column STOCKS.USER_ID is 'USER_ID';
comment on column STOCKS.KNOWLEDGE_ID is 'ナレッジID';
comment on column STOCKS.COMMENT is 'コメント';
comment on column STOCKS.INSERT_USER is '登録ユーザ';
comment on column STOCKS.INSERT_DATETIME is '登録日時';
comment on column STOCKS.UPDATE_USER is '更新ユーザ';
comment on column STOCKS.UPDATE_DATETIME is '更新日時';
comment on column STOCKS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_GROUPS is 'アクセス可能なグループ';
comment on column KNOWLEDGE_GROUPS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_GROUPS.GROUP_ID is 'GROUP_ID';
comment on column KNOWLEDGE_GROUPS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_GROUPS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_GROUPS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_GROUPS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_GROUPS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_USERS is 'アクセス可能なユーザ';
comment on column KNOWLEDGE_USERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_USERS.USER_ID is 'USER_ID';
comment on column KNOWLEDGE_USERS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_USERS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_USERS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_USERS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_USERS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_TAGS is 'ナレッジが持つタグ';
comment on column KNOWLEDGE_TAGS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_TAGS.TAG_ID is 'タグ_ID';
comment on column KNOWLEDGE_TAGS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_TAGS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_TAGS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_TAGS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_TAGS.DELETE_FLAG is '削除フラグ';

comment on table TAGS is 'タグ';
comment on column TAGS.TAG_ID is 'タグ_ID';
comment on column TAGS.TAG_NAME is 'タグ名称';
comment on column TAGS.INSERT_USER is '登録ユーザ';
comment on column TAGS.INSERT_DATETIME is '登録日時';
comment on column TAGS.UPDATE_USER is '更新ユーザ';
comment on column TAGS.UPDATE_DATETIME is '更新日時';
comment on column TAGS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_FILES is '添付ファイル';
comment on column KNOWLEDGE_FILES.FILE_NO is '添付ファイル番号';
comment on column KNOWLEDGE_FILES.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_FILES.COMMENT_NO is 'コメント番号';
comment on column KNOWLEDGE_FILES.FILE_NAME is 'ファイル名';
comment on column KNOWLEDGE_FILES.FILE_SIZE is 'ファイルサイズ';
comment on column KNOWLEDGE_FILES.FILE_BINARY is 'バイナリ';
comment on column KNOWLEDGE_FILES.PARSE_STATUS is 'パース結果';
comment on column KNOWLEDGE_FILES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_FILES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_FILES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_FILES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_FILES.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGES is 'ナレッジ';
comment on column KNOWLEDGES.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGES.TITLE is 'タイトル';
comment on column KNOWLEDGES.CONTENT is '内容';
comment on column KNOWLEDGES.PUBLIC_FLAG is '公開区分';
comment on column KNOWLEDGES.TAG_IDS is 'タグID一覧';
comment on column KNOWLEDGES.TAG_NAMES is 'タグ名称一覧';
comment on column KNOWLEDGES.LIKE_COUNT is 'いいね件数';
comment on column KNOWLEDGES.COMMENT_COUNT is 'コメント件数';
comment on column KNOWLEDGES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGES.DELETE_FLAG is '削除フラグ';
