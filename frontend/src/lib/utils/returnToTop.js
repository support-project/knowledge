/* global $ */
$(document).ready(function () {
  var pagetop = $('.pagetop')
  $(window).scroll(function () {
    console.log(pagetop)
    if ($(this).scrollTop() > 100) {
      console.log('fadeIn')
      pagetop.show()
    } else {
      console.log('fadeOut')
      pagetop.hide()
    }
  })
  pagetop.click(function () {
    $('body, html').animate({
      scrollTop: 0
    }, 500)
    return false
  })
})
