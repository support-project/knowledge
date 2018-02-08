import logger from 'logger'
const LABEL = 'setArticle.js'

export default (state, article) => {
  logger.info(LABEL, 'Set article on state.\n' + JSON.stringify(article))
  state.article = article
}
