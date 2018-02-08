import Promise from 'bluebird'
import $ from 'jquery'
import lang from 'lang'
import logger from 'logger'

const LABEL = 'processLink.js'

/**
 * 内部の別の記事へのリンクを生成
 * @param {*} parent 処理対象のHTML or JqueryObjct
 */
export default function (parent) {
  logger.trace(LABEL, parent)
  return Promise.try(() => {
    var jqObj = parent
    if (lang.isString(parent)) {
      jqObj = $('<div>' + parent + '</div>')
    }
    logger.trace(LABEL, jqObj.html())
    var target = jqObj.find('.internallink')
    target.replaceWith(function () {
      var knowledgeNo = $(this).text().substring(1)
      logger.debug(LABEL, knowledgeNo)
      var link = '<a href="/open.knowledge/view/"' + knowledgeNo + '">'
      link += '#' + knowledgeNo
      link += '</a>'
      logger.debug(LABEL, link)
      $(this).replaceWith(link)
      logger.debug(LABEL, $(this))
    })
    return jqObj.html()
  })
}
