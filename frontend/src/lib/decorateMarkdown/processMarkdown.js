import Promise from 'bluebird'
import logger from 'logger'
import marked from 'marked'
import hljs from 'highlight.js'

const LABEL = 'processMarkdown.js'

marked.setOptions({
  renderer: new marked.Renderer(),
  gfm: true,
  tables: true,
  breaks: true,
  pedantic: false,
  sanitize: false,
  smartLists: true,
  smartypants: false
})

var sequentialId = 0

var renderer = new marked.Renderer()
renderer.heading = function (text, level) {
  var escapedText = text.toLowerCase().replace(/[^\w]+/g, '-')
  return '<h' + level + ' id="markdown-agenda-' + (sequentialId++) + '" ><a name="' +
    escapedText +
    '" class="anchor" href="#' +
    escapedText +
    '"><span class="header-link"></span></a>' +
    text + '</h' + level + '>'
}
renderer.code = function (code, language) {
  logger.debug(code)
  var value = '<pre class="hljs"><code class="' + language + '">' + hljs.highlightAuto(code).value + '</code></pre>'
  logger.debug(LABEL, value)
  return value
}
renderer.codespan = function (code) {
  logger.debug(code)
  var value = '<code class="hljs">' + hljs.highlightAuto(code).value + '</code>'
  logger.debug(LABEL, value)
  return value
}

/**
 * Markdownのパース
 * サニタイズやコードハイライトも実施
 */
export default function (input) {
  sequentialId = 0
  return Promise.try(() => {
    logger.debug(LABEL, input)
    return marked(input, { renderer: renderer })
  })
}
