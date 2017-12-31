import Promise from 'bluebird'
import $ from 'jquery'
import store from '../../store'

var toggle = true

export default {
  init: (val) => {
    toggle = val
  },
  toggle: () => {
    return Promise.try(() => {
      if (!toggle) {
        $('.control-sidebar').addClass('control-sidebar-open')
        toggle = true
      } else {
        $('.control-sidebar').removeClass('control-sidebar-open')
        toggle = false
      }
      store.commit('SET_PAGE_STATE', {
        toggleTOC: toggle
      })
      return toggle
    })
  }
}
