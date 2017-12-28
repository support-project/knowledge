import Promise from 'bluebird'
import $ from 'jquery'

export default function (targetId) {
  if (!targetId) {
    targetId = 'secondNavbar'
  }
  return Promise.try(() => {
    var nav = $('#' + targetId)
    var offset = nav.offset()
    $(window).scroll(function () {
      if ($(window).scrollTop() > offset.top) {
        nav.addClass('secondNavbarfixed')
        nav.addClass('navbar-color')
      } else {
        nav.removeClass('secondNavbarfixed')
        nav.removeClass('navbar-color')
      }
    })
  })
}
