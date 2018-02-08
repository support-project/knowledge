/* global $ */
import logger from 'logger'
const LABEL = 'setPageState.js'

export default (state, pagestate) => {
  if ('loading' in pagestate) {
    logger.debug(LABEL, 'loading:' + pagestate.loading)
    state.loading = pagestate.loading
    // display loading by pace
    if (pagestate.loading) {
      $('div.pace').removeClass('pace-inactive')
      $('div.pace').addClass('pace-active')
    } else {
      $('div.pace').addClass('pace-inactive')
      $('div.pace').removeClass('pace-active')
    }
  }
  if ('showRightSideBar' in pagestate) {
    logger.debug(LABEL, 'Change state.showRightSideBar: ' + JSON.stringify(pagestate.showRightSideBar))
    state.showRightSideBar = pagestate.showRightSideBar
  }
}
