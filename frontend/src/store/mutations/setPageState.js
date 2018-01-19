import logger from 'logger'
const LABEL = 'setPageState.js'

export default (state, pagestate) => {
  if ('loading' in pagestate) {
    state.pagestate.loading = pagestate.loading
  }
  if ('showRightSideBar' in pagestate) {
    logger.debug(LABEL, 'Change state.pagestate.showRightSideBar: ' + JSON.stringify(pagestate.showRightSideBar))
    state.pagestate.showRightSideBar = pagestate.showRightSideBar
  }
}
