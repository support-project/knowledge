import Promise from 'bluebird'
import logger from 'logger'

const LABEL = 'processFootnotes.js'

var setFootnotes = function (input) {
  var BLOCK_FOOTNOTES = /^\[\^([0-9]+)\]: (.*)/
  var lines = input.split('\n')
  var results = ''
  var footnotes = ''

  var cnt = 0
  lines.forEach((line) => {
    var matchs = line.match(BLOCK_FOOTNOTES)
    if (matchs && matchs.length === 3) {
      var key = matchs[1]
      var text = matchs[2].trim()
      var li = '<li id="fn-' + key + '">'
      li += text.trim()
      li += '<a href="#fnref-' + key + '">&#8617;</a>'
      li += '</li>'
      li += '\n'
      logger.debug(LABEL, line)
      logger.debug(LABEL, li)
      footnotes += li
    } else {
      if (cnt > 0) {
        results += '\n'
      }
      results += line
      cnt++
    }
  })
  if (footnotes) {
    logger.trace(LABEL, results)
    results += '<ol class="footnotes">' + footnotes + '</ol>'
    logger.trace(LABEL, results)
  }
  return results
}

var toFootnotes = function (input) {
  var INLINE_FOOTNOTE = /\[\^([0-9]+)\]/g
  var lines = input.split('\n')
  var results = ''

  var cnt = 0
  lines.forEach((line) => {
    if (cnt > 0) {
      results += '\n'
    }
    logger.debug(LABEL, line)
    var matchs = line.match(INLINE_FOOTNOTE)
    if (matchs && matchs.length > 0) {
      matchs.forEach((m) => {
        var key = m.substring(2, m.length - 1)
        logger.debug(LABEL, key)
        var sup = '<sup class="footnote-ref" id="fnref-' + key + '">'
        sup += '<a href="#fn-' + key + '">' + key + '</a>'
        line = line.replace(m, sup)
      })
    }
    results += line
    cnt++
  })
  return results
}

/**
 * 脚注記法を処理する
 */
export default function (input) {
  return Promise.try(() => {
    input = setFootnotes(input)
    logger.debug(LABEL, input)
    input = toFootnotes(input)
    logger.debug(LABEL, input)
    return input
  })
}
