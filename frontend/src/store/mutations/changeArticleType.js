import logger from 'logger'
const LABEL = 'changeArticleType.js'

export default (state, type) => {
  logger.debug(LABEL, 'Change article type: ' + type)
  var article = state.resources.article
  var types = state.resources.types
  logger.debug(LABEL, JSON.stringify(article))
  logger.debug(LABEL, JSON.stringify(types))
  article.type.id = type
  var beforeTypeId = -100
  if (article.type.items.length > 0) {
    beforeTypeId = article.type.items[0].typeId
  }
  types.forEach(element => {
    if (beforeTypeId === element.id) {
      for (var i = 0; i < article.type.items.length; i++) {
        element.items[i].itemValue = article.type.items[i].itemValue
      }
    }
  })
  types.forEach(element => {
    if (type === element.id) {
      article.type.name = element.name
      var items = []
      element.items.forEach(item => {
        items.push(item)
      })
      article.type.items = items
    }
  })
}
