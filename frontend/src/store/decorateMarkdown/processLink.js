import Promise from 'bluebird'
import $ from 'jquery'

import lang from '../../lib/lang'

/**
 * 内部の別の記事へのリンクを生成
 * @param {*} parent 処理対象のHTML or JqueryObjct
 */
export default function (parent) {
  // console.log(parent)
  return Promise.try(() => {
    var jqObj = parent
    if (lang.isString(parent)) {
      jqObj = $('<div>' + parent + '</div>')
    }
    // console.log(jqObj.html())

    var target = jqObj.find('.internallink')
    target.replaceWith(function () {
      var knowledgeNo = $(this).text().substring(1)
      // console.log(knowledgeNo)
      var link = '<a href="/open.knowledge/view/"' + knowledgeNo + '">'
      link += '#' + knowledgeNo
      link += '</a>'
      // console.log(link)
      $(this).replaceWith(link)
      // console.log($(this))
    })
    return jqObj.html()
  })
}
