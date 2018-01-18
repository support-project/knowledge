import logger from 'logger'
const LABEL = 'setResources.js'

export default (state, resources) => {
  if ('article' in resources) {
    logger.debug(LABEL, 'Change state.resources.article: \n' + JSON.stringify(resources.article, null, '  '))
    state.resources.article = resources.article
  }
  if ('articles' in resources) {
    state.resources.articles = resources.articles
  }
  if ('drafts' in resources) {
    state.resources.drafts = resources.drafts
  }
  if ('comments' in resources) {
    state.resources.comments = resources.comments
  }
  if ('groups' in resources) {
    state.resources.groups = resources.groups
  }
  if ('types' in resources) {
    state.resources.types = resources.types
  }
  if ('tags' in resources) {
    state.resources.tags = resources.tags
  }
  if ('toc' in resources) {
    state.resources.toc = resources.toc
  }
}
