/* global $ */
import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'showDialog.js'

export default (store, params) => {
  logger.debug(LABEL, 'showdialog')
  return Promise.try(() => {
    store.state.selected = params.selected
    store.state.selected.groups.forEach(element => {
      element.type = 'group'
    })
    store.state.selected.users.forEach(element => {
      element.type = 'user'
    })
    store.state.params = {
      keyword: '',
      limit: 10,
      offset: 0
    }
    store.state.targets = []
    store.state.loading = false
    $('#select-target-dialog').modal('show')
    return store.dispatch('searchTargets', store.state.params)
  })
}
