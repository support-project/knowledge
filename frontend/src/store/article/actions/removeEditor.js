import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'removeEditor.js'

export default (store, target) => {
  logger.debug(LABEL, 'removeEditor: ' + JSON.stringify(target))
  return Promise.try(() => {
    let array
    if (target.type === 'group') {
      array = store.state.article.editors.groups
    } else {
      array = store.state.article.editors.users
    }
    var arrayNew = array.filter(function (v, i) {
      return (v.id !== target.id)
    })
    if (target.type === 'group') {
      store.state.article.editors.groups = arrayNew
    } else {
      store.state.article.editors.users = arrayNew
    }
  })
}
