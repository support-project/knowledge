import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'selectTarget.js'

export default (store, target) => {
  logger.debug(LABEL, 'selectTarget')
  return Promise.try(() => {
    let exist = false
    if (target.type === 'group') {
      store.state.selected.groups.forEach(group => {
        if (target.id === group.id) {
          exist = true
        }
      })
      if (!exist) {
        store.state.selected.groups.push(target)
        target.selected = true
      }
    } else {
      store.state.selected.users.forEach(user => {
        if (target.id === user.id) {
          exist = true
        }
      })
      if (!exist) {
        store.state.selected.users.push(target)
        target.selected = true
      }
    }
    if (!exist) {
      store.dispatch('pagestate/triggerResize', null, {root: true})
    }
  })
}
