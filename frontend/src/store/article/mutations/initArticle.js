import logger from 'logger'
const LABEL = 'initArticle.js'

export default (state) => {
  logger.info(LABEL, 'initialize article.')
  state.article = {
    knowledgeId: '',
    title: '',
    content: '',
    draftId: '',
    type: {
      id: -100,
      items: []
    }
  }
}
