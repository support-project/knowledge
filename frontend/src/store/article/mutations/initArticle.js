import logger from 'logger'
const LABEL = 'initArticle.js'

export default (state) => {
  logger.debug(LABEL, 'initialize article.')
  state.article = {
    knowledgeId: '',
    title: '',
    content: '',
    draftId: '',
    publicFlag: 0,
    type: {
      id: -100,
      items: []
    },
    viewers: {
      groups: [],
      users: []
    },
    editors: {
      groups: [],
      users: []
    },
    tags: []
  }
}
