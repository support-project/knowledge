import Promise from 'bluebird'
import logger from 'logger'

import processLink from './processLink'
import processMarkdown from './processMarkdown'
import processEmoji from './processEmoji'

export default function (target) {
  return Promise.try(() => {
    logger.trace(target)
    return processMarkdown(target)
  }).then(function (result) {
    logger.trace(result)
    return processLink(result)
  }).then(function (result) {
    logger.trace(result)
    return processEmoji(result)
  })
}
