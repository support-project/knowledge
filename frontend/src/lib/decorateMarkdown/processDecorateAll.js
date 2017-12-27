import Promise from 'bluebird'
import logger from 'logger'

import processLink from './processLink'
import processHighlight from './processHighlight'
import processEmoji from './processEmoji'

export default function (target) {
  return Promise.try(() => {
    logger.trace(target)
    return processLink(target)
  }).then(function (result) {
    logger.trace(result)
    return processHighlight(result)
  }).then(function (result) {
    logger.trace(result)
    return processEmoji(result)
  })
}
