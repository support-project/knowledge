import logger from 'logger'
const LABEL = 'clearSearchCondition.js'

export default (state) => {
  logger.debug(LABEL, 'Clear search condition')
  state.search = {
    search: false,
    keyword: '',
    creators: []
  }
}
