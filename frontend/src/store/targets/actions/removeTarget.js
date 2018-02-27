import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'removeTarget.js'

export default (store, target) => {
  logger.debug(LABEL, 'removeTarget: ' + JSON.stringify(target))
  return Promise.try(() => {
    let array
    if (target.type === 'group') {
      array = store.state.selected.groups
    } else {
      array = store.state.selected.users
    }
    var arrayNew = array.filter(function (v, i) {
      return (v.id !== target.id)
    })
    if (target.type === 'group') {
      store.state.selected.groups = arrayNew
    } else {
      store.state.selected.users = arrayNew
    }

    store.state.targets.forEach(element => {
      if (target.id === element.id && target.type === element.type) {
        element.selected = false
      }
    })
    store.dispatch('pagestate/triggerResize', null, {root: true})
  })
}
