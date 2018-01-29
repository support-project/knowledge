import logger from 'logger'
const LABEL = 'setArticles.js'

export default (state, articles) => {
  logger.info(LABEL, 'Set articles on state.\n' + JSON.stringify(articles))
  state.articles = articles
}
