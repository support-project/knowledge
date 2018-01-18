/* global $ */
import logger from 'logger'
import Promise from 'bluebird'
const LABEL = 'secondNavbar.js'

var setScroll = false

export default function (targetId) {
  if (!targetId) {
    targetId = 'secondNavbar'
  }
  if (!setScroll) {
    setScroll = true
    logger.debug(LABEL, 'set secondNavbar')
    return Promise.try(() => {
      var nav = $('#' + targetId)
      var offset = nav.offset()
      window.onscroll = function () {
        nav = $('#' + targetId)
        logger.debug(LABEL, 'scrollTop: ' + $(window).scrollTop() + ' / ' + JSON.stringify(offset))
        if (nav) {
          if ($(window).scrollTop() > offset.top) {
            logger.debug(LABEL, 'fix secondNavbar')
            nav.addClass('secondNavbarfixed')
            nav.addClass('navbar-color')
          } else {
            logger.debug(LABEL, 'remove fix secondNavbar')
            nav.removeClass('secondNavbarfixed')
            nav.removeClass('navbar-color')
          }
        }
      }
    })
  }
}
