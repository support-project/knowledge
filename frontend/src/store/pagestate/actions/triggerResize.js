/* global $ */
import Promise from 'bluebird'
import logger from 'logger'
const LABEL = 'triggerResize.js'

export default (time) => {
  if (!time) {
    time = 500
  }
  return Promise.try(() => {
    logger.debug(LABEL, 'triggerResize')
    setTimeout(() => {
      // Chrome でサイドバーの描画サイズとスクロールバーでスクロールできる範囲がずれるので、Windowのリサイズイベントを発行する
      $(window).trigger('resize')
    }, time)
  })
}
