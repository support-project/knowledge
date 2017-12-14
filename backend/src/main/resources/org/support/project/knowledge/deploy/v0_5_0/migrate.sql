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

ALTER TABLE KNOWLEDGE_FILES ADD COLUMN COMMENT_NO bigint;

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

comment on column KNOWLEDGE_FILES.COMMENT_NO is 'コメント番号';





-- メールアドレス変更確認
drop table if exists CONFIRM_MAIL_CHANGES cascade;

create table CONFIRM_MAIL_CHANGES (
  ID character varying(256) not null
  , USER_ID integer not null
  , MAIL_ADDRESS character varying(256) not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint CONFIRM_MAIL_CHANGES_PKC primary key (ID)
) ;

-- ロケール
drop table if exists LOCALES cascade;

create table LOCALES (
  KEY character varying(12) not null
  , LANGUAGE character varying(4)
  , COUNTRY character varying(4)
  , Variant character varying(4)
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LOCALES_PKC primary key (KEY)
) ;

ALTER TABLE USERS ADD COLUMN LOCALE_KEY character varying(12);

ALTER TABLE PROVISIONAL_REGISTRATIONS ADD COLUMN LOCALE_KEY character varying(12);



comment on table CONFIRM_MAIL_CHANGES is 'メールアドレス変更確認';
comment on column CONFIRM_MAIL_CHANGES.ID is 'リセット用ID';
comment on column CONFIRM_MAIL_CHANGES.USER_ID is 'ユーザID';
comment on column CONFIRM_MAIL_CHANGES.MAIL_ADDRESS is 'メールアドレス';
comment on column CONFIRM_MAIL_CHANGES.ROW_ID is '行ID';
comment on column CONFIRM_MAIL_CHANGES.INSERT_USER is '登録ユーザ';
comment on column CONFIRM_MAIL_CHANGES.INSERT_DATETIME is '登録日時';
comment on column CONFIRM_MAIL_CHANGES.UPDATE_USER is '更新ユーザ';
comment on column CONFIRM_MAIL_CHANGES.UPDATE_DATETIME is '更新日時';
comment on column CONFIRM_MAIL_CHANGES.DELETE_FLAG is '削除フラグ';

comment on table LOCALES is 'ロケール';
comment on column LOCALES.KEY is 'キー';
comment on column LOCALES.LANGUAGE is '言語';
comment on column LOCALES.COUNTRY is '国';
comment on column LOCALES.Variant is 'バリアント';
comment on column LOCALES.ROW_ID is '行ID';
comment on column LOCALES.INSERT_USER is '登録ユーザ';
comment on column LOCALES.INSERT_DATETIME is '登録日時';
comment on column LOCALES.UPDATE_USER is '更新ユーザ';
comment on column LOCALES.UPDATE_DATETIME is '更新日時';
comment on column LOCALES.DELETE_FLAG is '削除フラグ';

comment on column USERS.LOCALE_KEY is 'ロケール';
comment on column PROVISIONAL_REGISTRATIONS.LOCALE_KEY is 'ロケール';


