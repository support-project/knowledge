import Promise from 'bluebird'
import store from '../../store'
import logger from 'logger'
import rightSidebar from './../../lib/displayParts/rightSidebar'

const LABEL = 'toggleTOC.js'

var toggle = true

export default () => {
  return Promise.try(() => {
    if (!toggle) {
      toggle = true
    } else {
      toggle = false
    }
    logger.debug(LABEL, 'toggle:' + toggle)
    store.commit('SET_PAGE_STATE', {
      toggleTOC: toggle
    })
    rightSidebar(toggle)
    return toggle
  })
}
