import logger from 'logger'
const LABEL = 'setToc.js'

export default (state, toc) => {
  logger.info(LABEL, 'Set toc on state')
  state.toc = toc
}
