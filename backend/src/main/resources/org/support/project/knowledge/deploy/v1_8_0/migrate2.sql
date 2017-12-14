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
