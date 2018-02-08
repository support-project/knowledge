import Promise from 'bluebird'
import $ from 'jquery'
import lang from 'lang'
import logger from 'logger'

const LABEL = 'processToc.js'

/**
 * 目次のHTMLを生成
 */
export default function (parent) {
  logger.trace(LABEL, parent)
  return Promise.try(() => {
    var jqObj = parent
    if (lang.isString(parent)) {
      jqObj = $('<div>' + parent + '</div>')
    }
    logger.debug(LABEL, jqObj.html())
    var toc = $('<div></div>')
    jqObj.find('h1, h2, h3').each(function () {
      logger.debug(LABEL, $(this).attr('id'))
      var targetId = $(this).attr('id')
      // level
      var tagName = $(this).prop('tagName').toLowerCase()
      // link
      var $a = $('<a href="" class="toclink"></a>')
      $a.text('- ' + $(this).text())
      $a.attr('href', '#' + targetId)
      // link div
      var $div = $('<div class="toc-h toc-' + tagName + '"></div>')
      $a.appendTo($div)
      // append
      toc.append($div)
    })
    return toc.html()
  })
}
