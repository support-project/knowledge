import Promise from 'bluebird'
import lang from 'lang'
import logger from 'logger'
const LABEL = 'getTypesInformation.js'

const getTypeItems = function (store) {
  return Promise.try(() => {
    if (store.rootState.types.types && store.rootState.types.types.length > 0) {
      return store.rootState.types.types
    } else {
      return store.dispatch('types/loadTypes', null, {root: true})
    }
  })
}

export default (store) => {
  logger.trace(LABEL, JSON.stringify(store, null, '  '))
  return Promise.try(() => {
    return getTypeItems(store)
  }).then(typeItems => {
    logger.trace(LABEL, JSON.stringify(typeItems, null, '  '))
    let typeIds = store.state.search.typeIds
    let typeNames = store.state.search.types
    let types = []
    if (typeIds && typeIds.length > 0) {
      typeIds.forEach(id => {
        typeItems.forEach(element => {
          if (lang.isString(id)) {
            id = parseInt(id)
          }
          if (id === element.id) {
            types.push(element)
          }
        })
      })
    } else if (typeNames && typeNames.length > 0) {
      typeNames.forEach(name => {
        typeItems.forEach(element => {
          if (name === element.name) {
            types.push(element)
          }
        })
      })
    }
    return types
  })
}
