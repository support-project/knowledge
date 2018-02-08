/* global $ */
import logger from 'logger'
const LABEL = 'rightSidebar.js'

export default function (toggle) {
  logger.debug(LABEL, 'showRightSideBar: ' + toggle)
  if (toggle) {
    $('body').addClass('control-sidebar-open')
  } else {
    $('body').removeClass('control-sidebar-open')
  }
}
