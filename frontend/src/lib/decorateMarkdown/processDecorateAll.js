import Promise from 'bluebird'

import processLink from './processLink'
import processHighlight from './processHighlight'
import processEmoji from './processEmoji'

export default function (target) {
  return Promise.try(() => {
//    console.log(target)
    return processLink(target)
  }).then(function (result) {
//    console.log(result)
    return processHighlight(result)
  }).then(function (result) {
    //    console.log(result)
    return processEmoji(result)
  })
}
