import Promise from 'bluebird'
import $ from 'jquery'
import hljs from 'highlight.js'
import lang from 'lang'
import logger from 'logger'

const LABEL = 'processHighlight.js'

const highlight = function (jqobj, addstylus) {
  logger.trace(LABEL, jqobj.html())
  return Promise.try(() => {
    if (addstylus) {
      jqobj.addClass('stylus')
    }
    var text = jqobj.text()
    if (text.indexOf('://') !== -1) {
      logger.debug(LABEL, 'skip on hljs freeze word')
      logger.debug(LABEL, text)
      return
    }
    var result = hljs.highlightAuto(text)
    return result
  }).then((result) => {
    if (result) {
      jqobj.html(result.value)
    }
  })
}

var codeHighlight = function (parent) {
  var block = parent
  if (lang.isString(parent)) {
    block = $('<div>' + parent + '</div>')
  }
  var highlightPromises = []
  block.find('pre code').not('.lang-math').each(function (i, block) {
    highlightPromises.push(highlight($(this)))
  })
  block.find('p code').not('.lang-math').each(function (i, block) {
    highlightPromises.push(highlight($(this)))
  })
  block.find('li code').not('.lang-math').each(function (i, block) {
    highlightPromises.push(highlight($(this)))
  })
  return Promise.all(highlightPromises).then(() => {
    return block.html()
  })
}

export default function (parent) {
  return Promise.try(() => {
    return codeHighlight(parent)
  })
}
