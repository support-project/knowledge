import logger from 'logger'
const LABEL = 'getTypes.js'

export default (state) => {
  logger.debug(LABEL, 'call getTypes')
  return state.types
}
