ALTER TABLE MAIL_CONFIGS ADD COLUMN FROM_ADDRESS character varying(256);
ALTER TABLE MAIL_CONFIGS ADD COLUMN FROM_NAME character varying(256);

comment on column MAIL_CONFIGS.FROM_ADDRESS is '送信元アドレス';
comment on column MAIL_CONFIGS.FROM_NAME is '送信元名';
