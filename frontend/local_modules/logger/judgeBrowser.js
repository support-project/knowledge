var judgeBrowser = function () {
  var userAgent = window.navigator.userAgent.toLowerCase()
  if(userAgent.indexOf('msie') != -1 || userAgent.indexOf('trident') != -1) {
    return 'MSIE'
  } else if(userAgent.indexOf('edge') != -1) {
    return 'Edge'
  } else if(userAgent.indexOf('chrome') != -1) {
    return 'Chrome'
  } else if(userAgent.indexOf('safari') != -1) {
    return 'Safari'
  } else if(userAgent.indexOf('firefox') != -1) {
    return 'FireFox'
  } else if(userAgent.indexOf('opera') != -1) {
    return 'Opera'
  } else {
    return 'Other'
  }
}

module.exports = judgeBrowser
