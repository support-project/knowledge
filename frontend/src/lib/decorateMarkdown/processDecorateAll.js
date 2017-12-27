import Promise from 'bluebird'
import logger from 'logger'

import processLink from './processLink'
import processMarkdown from './processMarkdown'
import processEmoji from './processEmoji'
import processFootnotes from './processFootnotes'

export default function (target) {
  return Promise.try(() => {
    logger.trace(target)
    return processFootnotes(target) // Markdownのパースより先に実施すること（優先）
  }).then(function (result) {
    logger.trace(result)
    return processMarkdown(result)
  }).then(function (result) {
    logger.trace(result)
    return processLink(result)
  }).then(function (result) {
    logger.trace(result)
    return processEmoji(result)
  })
}
