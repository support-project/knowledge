import Vue from 'vue'
import VueI18n from 'vue-i18n'

const messageEn = require('../../../static/resource/message-en.json')
const messageJa = require('../../../static/resource/message-ja.json')
const message = {
  en: messageEn,
  ja: messageJa
}

// 言語の設定
Vue.use(VueI18n)
const i18n = new VueI18n({
  locale: 'en',
  fallbackLocale: 'en',
  messages: message
})

export default i18n
