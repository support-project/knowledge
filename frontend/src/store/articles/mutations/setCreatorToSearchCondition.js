import logger from 'logger'
const LABEL = 'setCreatorToSearchCondition.js'

export default (state, id) => {
  logger.debug(LABEL, 'set creator\'s id for search condition')
  state.search.creators = [id]
}
