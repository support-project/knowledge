import logger from 'logger'
const LABEL = 'setTypes.js'

export default (state, types) => {
  logger.info(LABEL, 'Set types on state.\n' + JSON.stringify(types))
  state.types = types
}
