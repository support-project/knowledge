var isString = function (obj) {
  return typeof (obj) === 'string' || obj instanceof String
}

var escapeLink = function (url) {
  if (url.toLowerCase().indexOf('javascript:') != -1) {
      var conv = ''
      conv += url.substring(0, url.toLowerCase().indexOf('javascript:'))
      conv += encodeURIComponent(url.substring(url.toLowerCase().indexOf('javascript:')))
      return conv
  } else {
      return url
  }
}

module.exports = {
  isString: isString,
  escapeLink: escapeLink
}
