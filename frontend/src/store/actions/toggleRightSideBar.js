import Promise from 'bluebird'
import store from '../../store'
import logger from 'logger'
import rightSidebar from './../../lib/displayParts/rightSidebar'

const LABEL = 'toggleRightSideBar.js'

export default () => {
  return Promise.try(() => {
    logger.debug(LABEL, store.state.pagestate.showRightSideBar)
    var toggle = store.state.pagestate.showRightSideBar
    if (!toggle) {
      toggle = true
    } else {
      toggle = false
    }
    logger.debug(LABEL, 'toggle:' + toggle)
    store.commit('SET_PAGE_STATE', {
      showRightSideBar: toggle
    })
    rightSidebar(toggle)
    return toggle
  })
}
