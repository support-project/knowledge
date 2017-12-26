import Promise from 'bluebird'
import $ from 'jquery'
import hljs from 'highlight.js'
import lang from '../../lib/lang'

const highlight = function (jqobj, addstylus) {
  console.log(jqobj.html())
  return Promise.try(() => {
    if (addstylus) {
      jqobj.addClass('stylus')
    }
    var text = jqobj.text()
    if (text.indexOf('://') !== -1) {
      console.log('skip on hljs freeze word')
      console.log(text)
      return
    }
    var result = hljs.highlightAuto(text)
    return result
  }).then((result) => {
    if (result) {
      jqobj.html(result.value)
      /*
      var html = '<code'
      if (result.language) {
        html += 'class="' + result.language + '"'
      }
      html += '>'
      html += result
      html += '</code>'
      console.log(html)
      jqobj.replaceWith(html)
      console.log(jqobj.html())
      */
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
