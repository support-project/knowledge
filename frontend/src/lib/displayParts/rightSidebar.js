import $ from 'jQuery'

export default function (toggle) {
  if (toggle) {
    $('body').addClass('control-sidebar-open')
  } else {
    $('body').removeClass('control-sidebar-open')
  }
}
