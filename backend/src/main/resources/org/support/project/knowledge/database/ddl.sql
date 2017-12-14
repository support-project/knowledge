-- いいねの通知状態
drop table if exists NOTIFICATION_STATUS cascade;

create table NOTIFICATION_STATUS (
  TYPE integer not null
  , TARGET_ID bigint not null
  , USER_ID integer not null
  , STATUS integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint NOTIFICATION_STATUS_PKC primary key (TYPE,TARGET_ID,USER_ID)
) ;

-- メールから投稿の際の除外条件
drop table if exists MAIL_HOOK_IGNORE_CONDITIONS cascade;

create table MAIL_HOOK_IGNORE_CONDITIONS (
  HOOK_ID INTEGER not null
  , CONDITION_NO integer not null
  , IGNORE_CONDITION_NO integer not null
  , CONDITION_KIND integer not null
  , CONDITION character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_HOOK_IGNORE_CONDITIONS_PKC primary key (HOOK_ID,CONDITION_NO,IGNORE_CONDITION_NO)
) ;

-- ユーザのポイント獲得履歴
drop table if exists POINT_USER_HISTORIES cascade;

create table POINT_USER_HISTORIES (
  USER_ID INTEGER not null
  , HISTORY_NO BIGINT not null
  , ACTIVITY_NO BIGINT not null
  , TYPE integer not null
  , POINT integer not null
  , BEFORE_TOTAL integer not null
  , TOTAL integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint POINT_USER_HISTORIES_PKC primary key (USER_ID,HISTORY_NO)
) ;

create index IDX_POINT_USER_HISTORIES_INSERT_DATETIME
  on POINT_USER_HISTORIES(INSERT_DATETIME);

-- ナレッジのポイント獲得履歴
drop table if exists POINT_KNOWLEDGE_HISTORIES cascade;

create table POINT_KNOWLEDGE_HISTORIES (
  KNOWLEDGE_ID BIGINT not null
  , HISTORY_NO BIGINT not null
  , ACTIVITY_NO BIGINT not null
  , TYPE integer not null
  , POINT integer not null
  , BEFORE_TOTAL integer not null
  , TOTAL integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint POINT_KNOWLEDGE_HISTORIES_PKC primary key (KNOWLEDGE_ID,HISTORY_NO)
) ;

create index IDX_POINT_KNOWLEDGE_HISTORIES_INSERT_DATETIME
  on POINT_KNOWLEDGE_HISTORIES(INSERT_DATETIME);

-- ユーザの称号
drop table if exists USER_BADGES cascade;

create table USER_BADGES (
  USER_ID INTEGER not null
  , NO INTEGER not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint USER_BADGES_PKC primary key (USER_ID,NO)
) ;

-- アクティビティ
drop table if exists ACTIVITIES cascade;

create table ACTIVITIES (
  ACTIVITY_NO BIGSERIAL not null
  , USER_ID INTEGER not null
  , KIND integer not null
  , TARGET character varying(64) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ACTIVITIES_PKC primary key (ACTIVITY_NO)
) ;

create index IDX_ACTIVITIES_USER_ID
  on ACTIVITIES(USER_ID);

create index IDX_ACTIVITIES_KIND_TARGET
  on ACTIVITIES(KIND,TARGET);

-- 称号
drop table if exists BADGES cascade;

create table BADGES (
  NO SERIAL not null
  , NAME character varying(128) not null
  , DISPLAY_TEXT character varying(32) not null
  , DESCRIPTION text
  , IMAGE character varying(64)
  , POINT integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint BADGES_PKC primary key (NO)
) ;

-- コメントのイイネ
drop table if exists LIKE_COMMENTS cascade;

create table LIKE_COMMENTS (
  NO BIGSERIAL not null
  , COMMENT_NO bigint not null
  , LIKE_CLASS integer default 1
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint LIKE_COMMENTS_PKC primary key (NO)
) ;

create index IDX_LIKE_COMMENTS_COMMENT_NO
  on LIKE_COMMENTS(COMMENT_NO);

-- 認証トークン
drop table if exists TOKENS cascade;

create table TOKENS (
  TOKEN character varying(128) not null
  , USER_ID integer not null
  , EXPIRES timestamp not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TOKENS_PKC primary key (TOKEN)
) ;

create unique index TOKENS_IX1
  on TOKENS(USER_ID);

-- メール受信設定
drop table if exists MAIL_PROPERTIES cascade;

create table MAIL_PROPERTIES (
  HOOK_ID INTEGER not null
  , PROPERTY_KEY character varying(128) not null
  , PROPERTY_VALUE character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_PROPERTIES_PKC primary key (HOOK_ID,PROPERTY_KEY)
) ;

-- ロケール毎のメールテンプレート
drop table if exists MAIL_LOCALE_TEMPLATES cascade;

create table MAIL_LOCALE_TEMPLATES (
  TEMPLATE_ID character varying(32) not null
  , KEY character varying(12) not null
  , TITLE text not null
  , CONTENT text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_LOCALE_TEMPLATES_PKC primary key (TEMPLATE_ID,KEY)
) ;

-- メールテンプレート
drop table if exists MAIL_TEMPLATES cascade;

create table MAIL_TEMPLATES (
  TEMPLATE_ID character varying(32) not null
  , TEMPLATE_TITLE character varying(128) not null
  , DESCRIPTION text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_TEMPLATES_PKC primary key (TEMPLATE_ID)
) ;

-- アンケートの回答の値
drop table if exists SURVEY_ITEM_ANSWERS cascade;

create table SURVEY_ITEM_ANSWERS (
  KNOWLEDGE_ID bigint not null
  , ANSWER_ID integer not null
  , ITEM_NO integer not null
  , ITEM_VALUE text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_ITEM_ANSWERS_PKC primary key (KNOWLEDGE_ID,ANSWER_ID,ITEM_NO)
) ;

-- アンケートの回答
drop table if exists SURVEY_ANSWERS cascade;

create table SURVEY_ANSWERS (
  KNOWLEDGE_ID bigint not null
  , ANSWER_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_ANSWERS_PKC primary key (KNOWLEDGE_ID,ANSWER_ID)
) ;

-- アンケートの選択肢の値
drop table if exists SURVEY_CHOICES cascade;

create table SURVEY_CHOICES (
  KNOWLEDGE_ID bigint not null
  , ITEM_NO integer not null
  , CHOICE_NO integer not null
  , CHOICE_VALUE character varying(256) not null
  , CHOICE_LABEL character varying(256) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_CHOICES_PKC primary key (KNOWLEDGE_ID,ITEM_NO,CHOICE_NO)
) ;

-- アンケート項目
drop table if exists SURVEY_ITEMS cascade;

create table SURVEY_ITEMS (
  KNOWLEDGE_ID bigint not null
  , ITEM_NO integer not null
  , ITEM_NAME character varying(32) not null
  , ITEM_TYPE integer not null
  , DESCRIPTION character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEY_ITEMS_PKC primary key (KNOWLEDGE_ID,ITEM_NO)
) ;

-- アンケート
drop table if exists SURVEYS cascade;

create table SURVEYS (
  KNOWLEDGE_ID bigint not null
  , TITLE character varying(256) not null
  , DESCRIPTION text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SURVEYS_PKC primary key (KNOWLEDGE_ID)
) ;

-- イベント
drop table if exists EVENTS cascade;

create table EVENTS (
  KNOWLEDGE_ID bigint not null
  , START_DATE_TIME timestamp not null
  , TIME_ZONE character varying(64)
  , NOTIFY_STATUS integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint EVENTS_PKC primary key (KNOWLEDGE_ID)
) ;

-- 参加者
drop table if exists PARTICIPANTS cascade;

create table PARTICIPANTS (
  KNOWLEDGE_ID bigint not null
  , USER_ID integer not null
  , STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PARTICIPANTS_PKC primary key (KNOWLEDGE_ID,USER_ID)
) ;

-- サービスの表示言語毎の設定
drop table if exists SERVICE_LOCALE_CONFIGS cascade;

create table SERVICE_LOCALE_CONFIGS (
  SERVICE_NAME character varying(64) not null
  , LOCALE_KEY character varying(12) not null
  , PAGE_HTML text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SERVICE_LOCALE_CONFIGS_PKC primary key (SERVICE_NAME,LOCALE_KEY)
) ;

-- サービスの設定
drop table if exists SERVICE_CONFIGS cascade;

create table SERVICE_CONFIGS (
  SERVICE_NAME character varying(64) not null
  , SERVICE_LABEL character varying(24) not null
  , SERVICE_ICON character varying(24) not null
  , SERVICE_IMAGE BYTEA
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint SERVICE_CONFIGS_PKC primary key (SERVICE_NAME)
) ;

-- ピン
drop table if exists PINS cascade;

create table PINS (
  NO serial not null
  , KNOWLEDGE_ID bigint not null
  , ROW_ID character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint PINS_PKC primary key (NO)
) ;

create index IDX_PINS_INSERT_USER
  on PINS(INSERT_USER);

-- Webhooks
drop table if exists WEBHOOKS cascade;

create table WEBHOOKS (
  WEBHOOK_ID character varying(64) not null
  , STATUS integer not null
  , HOOK character varying(20)
  , CONTENT text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint WEBHOOKS_PKC primary key (WEBHOOK_ID)
) ;

create index IDX_WEBHOOKS_STATUS
  on WEBHOOKS(STATUS);

-- Webhook 設定
drop table if exists WEBHOOK_CONFIGS cascade;

create table WEBHOOK_CONFIGS (
  HOOK_ID serial not null
  , HOOK character varying(20) not null
  , URL character varying(256) not null
  , IGNORE_PROXY integer
  , TEMPLATE text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint WEBHOOK_CONFIGS_PKC primary key (HOOK_ID)
) ;

-- ナレッジの項目値
drop table if exists DRAFT_ITEM_VALUES cascade;

create table DRAFT_ITEM_VALUES (
  DRAFT_ID bigint not null
  , TYPE_ID integer not null
  , ITEM_NO integer not null
  , ITEM_VALUE text
  , ITEM_STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint DRAFT_ITEM_VALUES_PKC primary key (DRAFT_ID,TYPE_ID,ITEM_NO)
) ;

-- ナレッジの下書き
drop table if exists DRAFT_KNOWLEDGES cascade;

create table DRAFT_KNOWLEDGES (
  DRAFT_ID BIGSERIAL not null
  , KNOWLEDGE_ID bigint
  , TITLE character varying(1024) not null
  , CONTENT text
  , PUBLIC_FLAG integer
  , ACCESSES text
  , EDITORS text
  , TAG_NAMES text
  , TYPE_ID integer
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint DRAFT_KNOWLEDGES_PKC primary key (DRAFT_ID)
) ;

-- メールから投稿
drop table if exists MAIL_POSTS cascade;

create table MAIL_POSTS (
  MESSAGE_ID character varying(128) not null
  , POST_KIND integer not null
  , ID BIGINT not null
  , SENDER text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_POSTS_PKC primary key (MESSAGE_ID)
) ;

-- メールから投稿する条件
drop table if exists MAIL_HOOK_CONDITIONS cascade;

create table MAIL_HOOK_CONDITIONS (
  HOOK_ID INTEGER not null
  , CONDITION_NO integer not null
  , CONDITION_KIND integer not null
  , CONDITION character varying(256)
  , PROCESS_USER integer not null
  , PROCESS_USER_KIND integer not null
  , PUBLIC_FLAG integer not null
  , TAGS text
  , VIEWERS text
  , EDITORS text
  , POST_LIMIT integer
  , LIMIT_PARAM character varying(256)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_HOOK_CONDITIONS_PKC primary key (HOOK_ID,CONDITION_NO)
) ;

-- 受信したメールからの処理
drop table if exists MAIL_HOOKS cascade;

create table MAIL_HOOKS (
  HOOK_ID SERIAL not null
  , MAIL_PROTOCOL character varying(10) not null
  , MAIL_HOST character varying(256) not null
  , MAIL_PORT integer not null
  , MAIL_USER character varying(256)
  , MAIL_PASS character varying(1024)
  , MAIL_PASS_SALT character varying(1024)
  , MAIL_FOLDER character varying(64)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint MAIL_HOOKS_PKC primary key (HOOK_ID)
) ;

-- ストック
drop table if exists STOCKS cascade;

create table STOCKS (
  STOCK_ID BIGSERIAL not null
  , STOCK_NAME character varying(256) not null
  , STOCK_TYPE integer not null
  , DESCRIPTION character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint STOCKS_PKC primary key (STOCK_ID)
) ;

-- ナレッジの項目値
drop table if exists KNOWLEDGE_ITEM_VALUES cascade;

create table KNOWLEDGE_ITEM_VALUES (
  KNOWLEDGE_ID bigint not null
  , TYPE_ID integer not null
  , ITEM_NO integer not null
  , ITEM_VALUE text
  , ITEM_STATUS integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_ITEM_VALUES_PKC primary key (KNOWLEDGE_ID,TYPE_ID,ITEM_NO)
) ;

-- 選択肢の値
drop table if exists ITEM_CHOICES cascade;

create table ITEM_CHOICES (
  TYPE_ID integer not null
  , ITEM_NO integer not null
  , CHOICE_NO integer not null
  , CHOICE_VALUE character varying(256) not null
  , CHOICE_LABEL character varying(256) not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint ITEM_CHOICES_PKC primary key (TYPE_ID,ITEM_NO,CHOICE_NO)
) ;

-- テンプレートのマスタ
drop table if exists TEMPLATE_MASTERS cascade;

create table TEMPLATE_MASTERS (
  TYPE_ID serial not null
  , TYPE_NAME character varying(256) not null
  , TYPE_ICON character varying(64)
  , DESCRIPTION character varying(1024)
  , INITIAL_VALUE text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TEMPLATE_MASTERS_PKC primary key (TYPE_ID)
) ;

-- テンプレートの項目
drop table if exists TEMPLATE_ITEMS cascade;

create table TEMPLATE_ITEMS (
  TYPE_ID integer not null
  , ITEM_NO integer not null
  , ITEM_NAME character varying(32) not null
  , ITEM_TYPE integer not null
  , DESCRIPTION character varying(1024)
  , INITIAL_VALUE text
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint TEMPLATE_ITEMS_PKC primary key (TYPE_ID,ITEM_NO)
) ;

-- 編集可能なグループ
drop table if exists KNOWLEDGE_EDIT_GROUPS cascade;

create table KNOWLEDGE_EDIT_GROUPS (
  KNOWLEDGE_ID bigint not null
  , GROUP_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_EDIT_GROUPS_PKC primary key (KNOWLEDGE_ID,GROUP_ID)
) ;

-- 編集可能なユーザ
drop table if exists KNOWLEDGE_EDIT_USERS cascade;

create table KNOWLEDGE_EDIT_USERS (
  KNOWLEDGE_ID bigint not null
  , USER_ID integer not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGE_EDIT_USERS_PKC primary key (KNOWLEDGE_ID,USER_ID)
) ;

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
  , LIKE_CLASS integer default 1
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
  , COMMENT_STATUS integer
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

create index IDX_VIEW_HISTORIES_INSERT_USER
  on VIEW_HISTORIES(INSERT_USER);

create index IDX_VIEW_HISTORIES_KNOWLEDGE_ID_INSERT_USER
  on VIEW_HISTORIES(KNOWLEDGE_ID,INSERT_USER);

-- ストックしたナレッジ
drop table if exists STOCK_KNOWLEDGES cascade;

create table STOCK_KNOWLEDGES (
  STOCK_ID bigint not null
  , KNOWLEDGE_ID bigint not null
  , COMMENT character varying(1024)
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint STOCK_KNOWLEDGES_PKC primary key (STOCK_ID,KNOWLEDGE_ID)
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
  , DRAFT_ID bigint
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
  , VIEW_COUNT bigint
  , TYPE_ID integer
  , NOTIFY_STATUS integer
  , POINT integer default 0 not null
  , INSERT_USER integer
  , INSERT_DATETIME timestamp
  , UPDATE_USER integer
  , UPDATE_DATETIME timestamp
  , DELETE_FLAG integer
  , constraint KNOWLEDGES_PKC primary key (KNOWLEDGE_ID)
) ;

comment on table NOTIFICATION_STATUS is 'いいねの通知状態';
comment on column NOTIFICATION_STATUS.TYPE is '種類';
comment on column NOTIFICATION_STATUS.TARGET_ID is 'ターゲットのID';
comment on column NOTIFICATION_STATUS.USER_ID is '登録者';
comment on column NOTIFICATION_STATUS.STATUS is '通知の状態';
comment on column NOTIFICATION_STATUS.INSERT_USER is '登録ユーザ';
comment on column NOTIFICATION_STATUS.INSERT_DATETIME is '登録日時';
comment on column NOTIFICATION_STATUS.UPDATE_USER is '更新ユーザ';
comment on column NOTIFICATION_STATUS.UPDATE_DATETIME is '更新日時';
comment on column NOTIFICATION_STATUS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_HOOK_IGNORE_CONDITIONS is 'メールから投稿の際の除外条件';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.HOOK_ID is 'HOOK_ID';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.CONDITION_NO is 'CONDITION_NO';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.IGNORE_CONDITION_NO is 'IGNORE_CONDITION_NO';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.CONDITION_KIND is '条件の種類	 1:宛先が「条件文字」であった場合';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.CONDITION is '条件の文字';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.INSERT_USER is '登録ユーザ';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.INSERT_DATETIME is '登録日時';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_HOOK_IGNORE_CONDITIONS.DELETE_FLAG is '削除フラグ';

comment on table POINT_USER_HISTORIES is 'ユーザのポイント獲得履歴';
comment on column POINT_USER_HISTORIES.USER_ID is 'ユーザID';
comment on column POINT_USER_HISTORIES.HISTORY_NO is '履歴番号';
comment on column POINT_USER_HISTORIES.ACTIVITY_NO is 'アクティビティ番号';
comment on column POINT_USER_HISTORIES.TYPE is '獲得のタイプ';
comment on column POINT_USER_HISTORIES.POINT is '獲得ポイント';
comment on column POINT_USER_HISTORIES.BEFORE_TOTAL is '獲得前ポイント';
comment on column POINT_USER_HISTORIES.TOTAL is 'トータルポイント';
comment on column POINT_USER_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column POINT_USER_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column POINT_USER_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column POINT_USER_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column POINT_USER_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table POINT_KNOWLEDGE_HISTORIES is 'ナレッジのポイント獲得履歴';
comment on column POINT_KNOWLEDGE_HISTORIES.KNOWLEDGE_ID is 'ナレッジID';
comment on column POINT_KNOWLEDGE_HISTORIES.HISTORY_NO is '履歴番号';
comment on column POINT_KNOWLEDGE_HISTORIES.ACTIVITY_NO is 'アクティビティ番号';
comment on column POINT_KNOWLEDGE_HISTORIES.TYPE is '獲得のタイプ';
comment on column POINT_KNOWLEDGE_HISTORIES.POINT is '獲得ポイント';
comment on column POINT_KNOWLEDGE_HISTORIES.BEFORE_TOTAL is '獲得前ポイント';
comment on column POINT_KNOWLEDGE_HISTORIES.TOTAL is 'トータルポイント';
comment on column POINT_KNOWLEDGE_HISTORIES.INSERT_USER is '登録ユーザ';
comment on column POINT_KNOWLEDGE_HISTORIES.INSERT_DATETIME is '登録日時';
comment on column POINT_KNOWLEDGE_HISTORIES.UPDATE_USER is '更新ユーザ';
comment on column POINT_KNOWLEDGE_HISTORIES.UPDATE_DATETIME is '更新日時';
comment on column POINT_KNOWLEDGE_HISTORIES.DELETE_FLAG is '削除フラグ';

comment on table USER_BADGES is 'ユーザの称号';
comment on column USER_BADGES.USER_ID is 'ユーザID';
comment on column USER_BADGES.NO is '番号';
comment on column USER_BADGES.INSERT_USER is '登録ユーザ';
comment on column USER_BADGES.INSERT_DATETIME is '登録日時';
comment on column USER_BADGES.UPDATE_USER is '更新ユーザ';
comment on column USER_BADGES.UPDATE_DATETIME is '更新日時';
comment on column USER_BADGES.DELETE_FLAG is '削除フラグ';

comment on table ACTIVITIES is 'アクティビティ';
comment on column ACTIVITIES.ACTIVITY_NO is 'アクティビティ番号';
comment on column ACTIVITIES.USER_ID is 'イベントをおこしたユーザ';
comment on column ACTIVITIES.KIND is 'アクティビティの種類';
comment on column ACTIVITIES.TARGET is 'ターゲットID';
comment on column ACTIVITIES.INSERT_USER is '登録ユーザ';
comment on column ACTIVITIES.INSERT_DATETIME is '登録日時';
comment on column ACTIVITIES.UPDATE_USER is '更新ユーザ';
comment on column ACTIVITIES.UPDATE_DATETIME is '更新日時';
comment on column ACTIVITIES.DELETE_FLAG is '削除フラグ';

comment on table BADGES is '称号';
comment on column BADGES.NO is '番号';
comment on column BADGES.NAME is '名称';
comment on column BADGES.DISPLAY_TEXT is '表示名';
comment on column BADGES.DESCRIPTION is '説明';
comment on column BADGES.IMAGE is '画像';
comment on column BADGES.POINT is '獲得ポイント';
comment on column BADGES.INSERT_USER is '登録ユーザ';
comment on column BADGES.INSERT_DATETIME is '登録日時';
comment on column BADGES.UPDATE_USER is '更新ユーザ';
comment on column BADGES.UPDATE_DATETIME is '更新日時';
comment on column BADGES.DELETE_FLAG is '削除フラグ';

comment on table LIKE_COMMENTS is 'コメントのイイネ';
comment on column LIKE_COMMENTS.NO is 'NO';
comment on column LIKE_COMMENTS.COMMENT_NO is 'コメント番号';
comment on column LIKE_COMMENTS.LIKE_CLASS is '種類';
comment on column LIKE_COMMENTS.INSERT_USER is '登録ユーザ';
comment on column LIKE_COMMENTS.INSERT_DATETIME is '登録日時';
comment on column LIKE_COMMENTS.UPDATE_USER is '更新ユーザ';
comment on column LIKE_COMMENTS.UPDATE_DATETIME is '更新日時';
comment on column LIKE_COMMENTS.DELETE_FLAG is '削除フラグ';

comment on table TOKENS is '認証トークン';
comment on column TOKENS.TOKEN is 'TOKEN';
comment on column TOKENS.USER_ID is 'ユーザID';
comment on column TOKENS.EXPIRES is '有効期限';
comment on column TOKENS.INSERT_USER is '登録ユーザ';
comment on column TOKENS.INSERT_DATETIME is '登録日時';
comment on column TOKENS.UPDATE_USER is '更新ユーザ';
comment on column TOKENS.UPDATE_DATETIME is '更新日時';
comment on column TOKENS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_PROPERTIES is 'メール受信設定';
comment on column MAIL_PROPERTIES.HOOK_ID is 'HOOK_ID';
comment on column MAIL_PROPERTIES.PROPERTY_KEY is 'PROPERTY_KEY';
comment on column MAIL_PROPERTIES.PROPERTY_VALUE is 'PROPERTY_VALUE';
comment on column MAIL_PROPERTIES.INSERT_USER is '登録ユーザ';
comment on column MAIL_PROPERTIES.INSERT_DATETIME is '登録日時';
comment on column MAIL_PROPERTIES.UPDATE_USER is '更新ユーザ';
comment on column MAIL_PROPERTIES.UPDATE_DATETIME is '更新日時';
comment on column MAIL_PROPERTIES.DELETE_FLAG is '削除フラグ';

comment on table MAIL_LOCALE_TEMPLATES is 'ロケール毎のメールテンプレート';
comment on column MAIL_LOCALE_TEMPLATES.TEMPLATE_ID is 'テンプレートID';
comment on column MAIL_LOCALE_TEMPLATES.KEY is 'キー';
comment on column MAIL_LOCALE_TEMPLATES.TITLE is 'タイトル';
comment on column MAIL_LOCALE_TEMPLATES.CONTENT is '本文';
comment on column MAIL_LOCALE_TEMPLATES.INSERT_USER is '登録ユーザ';
comment on column MAIL_LOCALE_TEMPLATES.INSERT_DATETIME is '登録日時';
comment on column MAIL_LOCALE_TEMPLATES.UPDATE_USER is '更新ユーザ';
comment on column MAIL_LOCALE_TEMPLATES.UPDATE_DATETIME is '更新日時';
comment on column MAIL_LOCALE_TEMPLATES.DELETE_FLAG is '削除フラグ';

comment on table MAIL_TEMPLATES is 'メールテンプレート';
comment on column MAIL_TEMPLATES.TEMPLATE_ID is 'テンプレートID';
comment on column MAIL_TEMPLATES.TEMPLATE_TITLE is 'テンプレートタイトル';
comment on column MAIL_TEMPLATES.DESCRIPTION is '説明文';
comment on column MAIL_TEMPLATES.INSERT_USER is '登録ユーザ';
comment on column MAIL_TEMPLATES.INSERT_DATETIME is '登録日時';
comment on column MAIL_TEMPLATES.UPDATE_USER is '更新ユーザ';
comment on column MAIL_TEMPLATES.UPDATE_DATETIME is '更新日時';
comment on column MAIL_TEMPLATES.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_ITEM_ANSWERS is 'アンケートの回答の値';
comment on column SURVEY_ITEM_ANSWERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_ITEM_ANSWERS.ANSWER_ID is '回答ID';
comment on column SURVEY_ITEM_ANSWERS.ITEM_NO is '項目NO';
comment on column SURVEY_ITEM_ANSWERS.ITEM_VALUE is '項目値';
comment on column SURVEY_ITEM_ANSWERS.INSERT_USER is '登録ユーザ';
comment on column SURVEY_ITEM_ANSWERS.INSERT_DATETIME is '登録日時';
comment on column SURVEY_ITEM_ANSWERS.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_ITEM_ANSWERS.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_ITEM_ANSWERS.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_ANSWERS is 'アンケートの回答';
comment on column SURVEY_ANSWERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_ANSWERS.ANSWER_ID is '回答ID';
comment on column SURVEY_ANSWERS.INSERT_USER is '登録ユーザ';
comment on column SURVEY_ANSWERS.INSERT_DATETIME is '登録日時';
comment on column SURVEY_ANSWERS.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_ANSWERS.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_ANSWERS.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_CHOICES is 'アンケートの選択肢の値';
comment on column SURVEY_CHOICES.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_CHOICES.ITEM_NO is '項目NO';
comment on column SURVEY_CHOICES.CHOICE_NO is '選択肢番号';
comment on column SURVEY_CHOICES.CHOICE_VALUE is '選択肢値';
comment on column SURVEY_CHOICES.CHOICE_LABEL is '選択肢ラベル';
comment on column SURVEY_CHOICES.INSERT_USER is '登録ユーザ';
comment on column SURVEY_CHOICES.INSERT_DATETIME is '登録日時';
comment on column SURVEY_CHOICES.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_CHOICES.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_CHOICES.DELETE_FLAG is '削除フラグ';

comment on table SURVEY_ITEMS is 'アンケート項目';
comment on column SURVEY_ITEMS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEY_ITEMS.ITEM_NO is '項目NO';
comment on column SURVEY_ITEMS.ITEM_NAME is '項目名';
comment on column SURVEY_ITEMS.ITEM_TYPE is '項目の種類';
comment on column SURVEY_ITEMS.DESCRIPTION is '説明';
comment on column SURVEY_ITEMS.INSERT_USER is '登録ユーザ';
comment on column SURVEY_ITEMS.INSERT_DATETIME is '登録日時';
comment on column SURVEY_ITEMS.UPDATE_USER is '更新ユーザ';
comment on column SURVEY_ITEMS.UPDATE_DATETIME is '更新日時';
comment on column SURVEY_ITEMS.DELETE_FLAG is '削除フラグ';

comment on table SURVEYS is 'アンケート';
comment on column SURVEYS.KNOWLEDGE_ID is 'ナレッジID';
comment on column SURVEYS.TITLE is 'タイトル';
comment on column SURVEYS.DESCRIPTION is '説明';
comment on column SURVEYS.INSERT_USER is '登録ユーザ';
comment on column SURVEYS.INSERT_DATETIME is '登録日時';
comment on column SURVEYS.UPDATE_USER is '更新ユーザ';
comment on column SURVEYS.UPDATE_DATETIME is '更新日時';
comment on column SURVEYS.DELETE_FLAG is '削除フラグ';

comment on table EVENTS is 'イベント';
comment on column EVENTS.KNOWLEDGE_ID is 'ナレッジID';
comment on column EVENTS.START_DATE_TIME is '開催日	 UTC';
comment on column EVENTS.TIME_ZONE is 'タイムゾーン';
comment on column EVENTS.NOTIFY_STATUS is '通知ステータス';
comment on column EVENTS.INSERT_USER is '登録ユーザ';
comment on column EVENTS.INSERT_DATETIME is '登録日時';
comment on column EVENTS.UPDATE_USER is '更新ユーザ';
comment on column EVENTS.UPDATE_DATETIME is '更新日時';
comment on column EVENTS.DELETE_FLAG is '削除フラグ';

comment on table PARTICIPANTS is '参加者';
comment on column PARTICIPANTS.KNOWLEDGE_ID is 'ナレッジID';
comment on column PARTICIPANTS.USER_ID is 'ユーザID';
comment on column PARTICIPANTS.STATUS is 'ステータス';
comment on column PARTICIPANTS.INSERT_USER is '登録ユーザ';
comment on column PARTICIPANTS.INSERT_DATETIME is '登録日時';
comment on column PARTICIPANTS.UPDATE_USER is '更新ユーザ';
comment on column PARTICIPANTS.UPDATE_DATETIME is '更新日時';
comment on column PARTICIPANTS.DELETE_FLAG is '削除フラグ';

comment on table SERVICE_LOCALE_CONFIGS is 'サービスの表示言語毎の設定';
comment on column SERVICE_LOCALE_CONFIGS.SERVICE_NAME is 'サービス名';
comment on column SERVICE_LOCALE_CONFIGS.LOCALE_KEY is 'ロケールキー';
comment on column SERVICE_LOCALE_CONFIGS.PAGE_HTML is 'トップページのHTML';
comment on column SERVICE_LOCALE_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column SERVICE_LOCALE_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column SERVICE_LOCALE_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column SERVICE_LOCALE_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column SERVICE_LOCALE_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table SERVICE_CONFIGS is 'サービスの設定';
comment on column SERVICE_CONFIGS.SERVICE_NAME is 'サービス名';
comment on column SERVICE_CONFIGS.SERVICE_LABEL is '表示名';
comment on column SERVICE_CONFIGS.SERVICE_ICON is 'アイコン文字列';
comment on column SERVICE_CONFIGS.SERVICE_IMAGE is '背景画像';
comment on column SERVICE_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column SERVICE_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column SERVICE_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column SERVICE_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column SERVICE_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table PINS is 'ピン';
comment on column PINS.NO is 'NO';
comment on column PINS.KNOWLEDGE_ID is 'ナレッジID';
comment on column PINS.ROW_ID is '行ID';
comment on column PINS.INSERT_USER is '登録ユーザ';
comment on column PINS.INSERT_DATETIME is '登録日時';
comment on column PINS.UPDATE_USER is '更新ユーザ';
comment on column PINS.UPDATE_DATETIME is '更新日時';
comment on column PINS.DELETE_FLAG is '削除フラグ';

comment on table WEBHOOKS is 'Webhooks';
comment on column WEBHOOKS.WEBHOOK_ID is 'WEBHOOK ID';
comment on column WEBHOOKS.STATUS is 'ステータス';
comment on column WEBHOOKS.HOOK is 'HOOK';
comment on column WEBHOOKS.CONTENT is '通知用json文字列';
comment on column WEBHOOKS.INSERT_USER is '登録ユーザ';
comment on column WEBHOOKS.INSERT_DATETIME is '登録日時';
comment on column WEBHOOKS.UPDATE_USER is '更新ユーザ';
comment on column WEBHOOKS.UPDATE_DATETIME is '更新日時';
comment on column WEBHOOKS.DELETE_FLAG is '削除フラグ';

comment on table WEBHOOK_CONFIGS is 'Webhook 設定';
comment on column WEBHOOK_CONFIGS.HOOK_ID is 'HOOK ID';
comment on column WEBHOOK_CONFIGS.HOOK is 'HOOK';
comment on column WEBHOOK_CONFIGS.URL is 'URL';
comment on column WEBHOOK_CONFIGS.IGNORE_PROXY is 'IGNORE_PROXY';
comment on column WEBHOOK_CONFIGS.TEMPLATE is 'TEMPLATE';
comment on column WEBHOOK_CONFIGS.INSERT_USER is '登録ユーザ';
comment on column WEBHOOK_CONFIGS.INSERT_DATETIME is '登録日時';
comment on column WEBHOOK_CONFIGS.UPDATE_USER is '更新ユーザ';
comment on column WEBHOOK_CONFIGS.UPDATE_DATETIME is '更新日時';
comment on column WEBHOOK_CONFIGS.DELETE_FLAG is '削除フラグ';

comment on table DRAFT_ITEM_VALUES is 'ナレッジの項目値';
comment on column DRAFT_ITEM_VALUES.DRAFT_ID is '下書きID';
comment on column DRAFT_ITEM_VALUES.TYPE_ID is 'テンプレートの種類ID';
comment on column DRAFT_ITEM_VALUES.ITEM_NO is '項目NO';
comment on column DRAFT_ITEM_VALUES.ITEM_VALUE is '項目値';
comment on column DRAFT_ITEM_VALUES.ITEM_STATUS is 'ステータス';
comment on column DRAFT_ITEM_VALUES.INSERT_USER is '登録ユーザ';
comment on column DRAFT_ITEM_VALUES.INSERT_DATETIME is '登録日時';
comment on column DRAFT_ITEM_VALUES.UPDATE_USER is '更新ユーザ';
comment on column DRAFT_ITEM_VALUES.UPDATE_DATETIME is '更新日時';
comment on column DRAFT_ITEM_VALUES.DELETE_FLAG is '削除フラグ';

comment on table DRAFT_KNOWLEDGES is 'ナレッジの下書き';
comment on column DRAFT_KNOWLEDGES.DRAFT_ID is '下書きID';
comment on column DRAFT_KNOWLEDGES.KNOWLEDGE_ID is 'ナレッジID';
comment on column DRAFT_KNOWLEDGES.TITLE is 'タイトル';
comment on column DRAFT_KNOWLEDGES.CONTENT is '内容';
comment on column DRAFT_KNOWLEDGES.PUBLIC_FLAG is '公開区分';
comment on column DRAFT_KNOWLEDGES.ACCESSES is '公開対象';
comment on column DRAFT_KNOWLEDGES.EDITORS is '共同編集対象';
comment on column DRAFT_KNOWLEDGES.TAG_NAMES is 'タグ名称一覧';
comment on column DRAFT_KNOWLEDGES.TYPE_ID is 'テンプレートの種類ID';
comment on column DRAFT_KNOWLEDGES.INSERT_USER is '登録ユーザ';
comment on column DRAFT_KNOWLEDGES.INSERT_DATETIME is '登録日時';
comment on column DRAFT_KNOWLEDGES.UPDATE_USER is '更新ユーザ';
comment on column DRAFT_KNOWLEDGES.UPDATE_DATETIME is '更新日時';
comment on column DRAFT_KNOWLEDGES.DELETE_FLAG is '削除フラグ';

comment on table MAIL_POSTS is 'メールから投稿';
comment on column MAIL_POSTS.MESSAGE_ID is 'Message-ID';
comment on column MAIL_POSTS.POST_KIND is '投稿区分	 1: Knowledge 2:Comment';
comment on column MAIL_POSTS.ID is 'ID';
comment on column MAIL_POSTS.SENDER is 'SENDER';
comment on column MAIL_POSTS.INSERT_USER is '登録ユーザ';
comment on column MAIL_POSTS.INSERT_DATETIME is '登録日時';
comment on column MAIL_POSTS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_POSTS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_POSTS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_HOOK_CONDITIONS is 'メールから投稿する条件';
comment on column MAIL_HOOK_CONDITIONS.HOOK_ID is 'HOOK_ID';
comment on column MAIL_HOOK_CONDITIONS.CONDITION_NO is 'CONDITION_NO';
comment on column MAIL_HOOK_CONDITIONS.CONDITION_KIND is '条件の種類	 1:宛先が「条件文字」であった場合';
comment on column MAIL_HOOK_CONDITIONS.CONDITION is '条件の文字';
comment on column MAIL_HOOK_CONDITIONS.PROCESS_USER is '投稿者';
comment on column MAIL_HOOK_CONDITIONS.PROCESS_USER_KIND is '投稿者の指定	 1:送信者のメールアドレスから、2:常に固定';
comment on column MAIL_HOOK_CONDITIONS.PUBLIC_FLAG is '公開区分';
comment on column MAIL_HOOK_CONDITIONS.TAGS is 'タグ';
comment on column MAIL_HOOK_CONDITIONS.VIEWERS is '公開先';
comment on column MAIL_HOOK_CONDITIONS.EDITORS is '共同編集者';
comment on column MAIL_HOOK_CONDITIONS.POST_LIMIT is '投稿者の制限';
comment on column MAIL_HOOK_CONDITIONS.LIMIT_PARAM is '制限のパラメータ';
comment on column MAIL_HOOK_CONDITIONS.INSERT_USER is '登録ユーザ';
comment on column MAIL_HOOK_CONDITIONS.INSERT_DATETIME is '登録日時';
comment on column MAIL_HOOK_CONDITIONS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_HOOK_CONDITIONS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_HOOK_CONDITIONS.DELETE_FLAG is '削除フラグ';

comment on table MAIL_HOOKS is '受信したメールからの処理';
comment on column MAIL_HOOKS.HOOK_ID is 'HOOK_ID';
comment on column MAIL_HOOKS.MAIL_PROTOCOL is 'MAIL_PROTOCOL';
comment on column MAIL_HOOKS.MAIL_HOST is 'MAIL_HOST';
comment on column MAIL_HOOKS.MAIL_PORT is 'MAIL_PORT';
comment on column MAIL_HOOKS.MAIL_USER is 'MAIL_USER';
comment on column MAIL_HOOKS.MAIL_PASS is 'MAIL_PASS';
comment on column MAIL_HOOKS.MAIL_PASS_SALT is 'MAIL_PASS_SALT';
comment on column MAIL_HOOKS.MAIL_FOLDER is 'MAIL_FOLDER';
comment on column MAIL_HOOKS.INSERT_USER is '登録ユーザ';
comment on column MAIL_HOOKS.INSERT_DATETIME is '登録日時';
comment on column MAIL_HOOKS.UPDATE_USER is '更新ユーザ';
comment on column MAIL_HOOKS.UPDATE_DATETIME is '更新日時';
comment on column MAIL_HOOKS.DELETE_FLAG is '削除フラグ';

comment on table STOCKS is 'ストック';
comment on column STOCKS.STOCK_ID is 'STOCK ID';
comment on column STOCKS.STOCK_NAME is 'STOCK 名';
comment on column STOCKS.STOCK_TYPE is '区分';
comment on column STOCKS.DESCRIPTION is '説明';
comment on column STOCKS.INSERT_USER is '登録ユーザ';
comment on column STOCKS.INSERT_DATETIME is '登録日時';
comment on column STOCKS.UPDATE_USER is '更新ユーザ';
comment on column STOCKS.UPDATE_DATETIME is '更新日時';
comment on column STOCKS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_ITEM_VALUES is 'ナレッジの項目値';
comment on column KNOWLEDGE_ITEM_VALUES.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_ITEM_VALUES.TYPE_ID is 'テンプレートの種類ID';
comment on column KNOWLEDGE_ITEM_VALUES.ITEM_NO is '項目NO';
comment on column KNOWLEDGE_ITEM_VALUES.ITEM_VALUE is '項目値';
comment on column KNOWLEDGE_ITEM_VALUES.ITEM_STATUS is 'ステータス';
comment on column KNOWLEDGE_ITEM_VALUES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_ITEM_VALUES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_ITEM_VALUES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_ITEM_VALUES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_ITEM_VALUES.DELETE_FLAG is '削除フラグ';

comment on table ITEM_CHOICES is '選択肢の値';
comment on column ITEM_CHOICES.TYPE_ID is 'テンプレートの種類ID';
comment on column ITEM_CHOICES.ITEM_NO is '項目NO';
comment on column ITEM_CHOICES.CHOICE_NO is '選択肢番号';
comment on column ITEM_CHOICES.CHOICE_VALUE is '選択肢値';
comment on column ITEM_CHOICES.CHOICE_LABEL is '選択肢ラベル';
comment on column ITEM_CHOICES.INSERT_USER is '登録ユーザ';
comment on column ITEM_CHOICES.INSERT_DATETIME is '登録日時';
comment on column ITEM_CHOICES.UPDATE_USER is '更新ユーザ';
comment on column ITEM_CHOICES.UPDATE_DATETIME is '更新日時';
comment on column ITEM_CHOICES.DELETE_FLAG is '削除フラグ';

comment on table TEMPLATE_MASTERS is 'テンプレートのマスタ';
comment on column TEMPLATE_MASTERS.TYPE_ID is 'テンプレートの種類ID';
comment on column TEMPLATE_MASTERS.TYPE_NAME is 'テンプレート名';
comment on column TEMPLATE_MASTERS.TYPE_ICON is 'アイコン';
comment on column TEMPLATE_MASTERS.DESCRIPTION is '説明';
comment on column TEMPLATE_MASTERS.INITIAL_VALUE is '本文の初期値';
comment on column TEMPLATE_MASTERS.INSERT_USER is '登録ユーザ';
comment on column TEMPLATE_MASTERS.INSERT_DATETIME is '登録日時';
comment on column TEMPLATE_MASTERS.UPDATE_USER is '更新ユーザ';
comment on column TEMPLATE_MASTERS.UPDATE_DATETIME is '更新日時';
comment on column TEMPLATE_MASTERS.DELETE_FLAG is '削除フラグ';

comment on table TEMPLATE_ITEMS is 'テンプレートの項目';
comment on column TEMPLATE_ITEMS.TYPE_ID is 'テンプレートの種類ID';
comment on column TEMPLATE_ITEMS.ITEM_NO is '項目NO';
comment on column TEMPLATE_ITEMS.ITEM_NAME is '項目名';
comment on column TEMPLATE_ITEMS.ITEM_TYPE is '項目の種類';
comment on column TEMPLATE_ITEMS.DESCRIPTION is '説明';
comment on column TEMPLATE_ITEMS.INITIAL_VALUE is '初期値';
comment on column TEMPLATE_ITEMS.INSERT_USER is '登録ユーザ';
comment on column TEMPLATE_ITEMS.INSERT_DATETIME is '登録日時';
comment on column TEMPLATE_ITEMS.UPDATE_USER is '更新ユーザ';
comment on column TEMPLATE_ITEMS.UPDATE_DATETIME is '更新日時';
comment on column TEMPLATE_ITEMS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_EDIT_GROUPS is '編集可能なグループ';
comment on column KNOWLEDGE_EDIT_GROUPS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_EDIT_GROUPS.GROUP_ID is 'GROUP_ID';
comment on column KNOWLEDGE_EDIT_GROUPS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_EDIT_GROUPS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_EDIT_GROUPS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_EDIT_GROUPS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_EDIT_GROUPS.DELETE_FLAG is '削除フラグ';

comment on table KNOWLEDGE_EDIT_USERS is '編集可能なユーザ';
comment on column KNOWLEDGE_EDIT_USERS.KNOWLEDGE_ID is 'ナレッジID';
comment on column KNOWLEDGE_EDIT_USERS.USER_ID is 'USER_ID';
comment on column KNOWLEDGE_EDIT_USERS.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGE_EDIT_USERS.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGE_EDIT_USERS.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGE_EDIT_USERS.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGE_EDIT_USERS.DELETE_FLAG is '削除フラグ';

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
comment on column LIKES.LIKE_CLASS is '種類';
comment on column LIKES.INSERT_USER is '登録ユーザ';
comment on column LIKES.INSERT_DATETIME is '登録日時';
comment on column LIKES.UPDATE_USER is '更新ユーザ';
comment on column LIKES.UPDATE_DATETIME is '更新日時';
comment on column LIKES.DELETE_FLAG is '削除フラグ';

comment on table COMMENTS is 'コメント';
comment on column COMMENTS.COMMENT_NO is 'コメント番号';
comment on column COMMENTS.KNOWLEDGE_ID is 'ナレッジID';
comment on column COMMENTS.COMMENT is 'コメント';
comment on column COMMENTS.COMMENT_STATUS is 'ステータス';
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

comment on table STOCK_KNOWLEDGES is 'ストックしたナレッジ';
comment on column STOCK_KNOWLEDGES.STOCK_ID is 'STOCK ID';
comment on column STOCK_KNOWLEDGES.KNOWLEDGE_ID is 'ナレッジID';
comment on column STOCK_KNOWLEDGES.COMMENT is 'コメント';
comment on column STOCK_KNOWLEDGES.INSERT_USER is '登録ユーザ';
comment on column STOCK_KNOWLEDGES.INSERT_DATETIME is '登録日時';
comment on column STOCK_KNOWLEDGES.UPDATE_USER is '更新ユーザ';
comment on column STOCK_KNOWLEDGES.UPDATE_DATETIME is '更新日時';
comment on column STOCK_KNOWLEDGES.DELETE_FLAG is '削除フラグ';

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
comment on column KNOWLEDGE_FILES.DRAFT_ID is '下書きID';
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
comment on column KNOWLEDGES.VIEW_COUNT is '参照件数';
comment on column KNOWLEDGES.TYPE_ID is 'テンプレートの種類ID';
comment on column KNOWLEDGES.NOTIFY_STATUS is '通知ステータス';
comment on column KNOWLEDGES.POINT is 'ポイント';
comment on column KNOWLEDGES.INSERT_USER is '登録ユーザ';
comment on column KNOWLEDGES.INSERT_DATETIME is '登録日時';
comment on column KNOWLEDGES.UPDATE_USER is '更新ユーザ';
comment on column KNOWLEDGES.UPDATE_DATETIME is '更新日時';
comment on column KNOWLEDGES.DELETE_FLAG is '削除フラグ';
