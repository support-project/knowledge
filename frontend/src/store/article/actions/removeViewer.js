import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'removeViewer.js'

export default (store, target) => {
  logger.debug(LABEL, 'removeViewer: ' + JSON.stringify(target))
  return Promise.try(() => {
    let array
    if (target.type === 'group') {
      array = store.state.article.viewers.groups
    } else {
      array = store.state.article.viewers.users
    }
    var arrayNew = array.filter(function (v, i) {
      return (v.id !== target.id)
    })
    if (target.type === 'group') {
      store.state.article.viewers.groups = arrayNew
    } else {
      store.state.article.viewers.users = arrayNew
    }
  })
}
