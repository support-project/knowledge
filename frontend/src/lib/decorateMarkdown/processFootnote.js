import Promise from 'bluebird'
import $ from 'jquery'
import lang from 'lang'
import logger from 'logger'

/**
 * 脚注記法を正しく処理
 * !!! 場所の制御なので、画面描画後に呼び出す（actionの処理内で実行はできない）
 * @param {*} parent 処理対象のHTML or JqueryObjct
 */
export default function (parent) {
  logger.trace(parent)
  return Promise.try(() => {
    var jqObj = parent
    if (lang.isString(parent)) {
      jqObj = $('<div>' + parent + '</div>')
    }

    jqObj.find('a').each(function () {
      if ($(this).attr('href').startsWith('#fn')) {
        var href = $(this).attr('href')
        var target = $(href === '#' || href === '' ? 'html' : href)
        var position = target.offset().top
        logger.debug(position)
        // ヘッダーの分だけずらす
        position -= 80
        var link = location.pathname + $(this).attr('href')
        $(this).attr('href', link)
        $(this).click(function () {
          var speed = 400
          logger.debug(position)
          $('body,html').animate({ scrollTop: position }, speed, 'swing')
          return false
        })
      }
    })
    return jqObj.html()
  })
}
