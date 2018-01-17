import logger from 'logger'
const LABEL = 'setPageState.js'

export default (state, pagestate) => {
  if ('loading' in pagestate) {
    state.pagestate.loading = pagestate.loading
  }
  if ('toggleTOC' in pagestate) {
    logger.debug(LABEL, 'Change state.pagestate.toggleTOC: ' + JSON.stringify(pagestate.toggleTOC))
    state.pagestate.toggleTOC = pagestate.toggleTOC
  }
  if ('toggleAttributes' in pagestate) {
    logger.debug(LABEL, 'Change state.pagestate.toggleAttributes: ' + JSON.stringify(pagestate.toggleAttributes))
    state.pagestate.toggleAttributes = pagestate.toggleAttributes
  }
}
