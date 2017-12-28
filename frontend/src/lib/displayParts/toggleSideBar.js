import Promise from 'bluebird'
import $ from 'jquery'

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
    })
  }
}
