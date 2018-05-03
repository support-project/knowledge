var isString = function (obj) {
  return typeof (obj) === 'string' || obj instanceof String
}

var escapeLink = function (url) {
  if (url.toLowerCase().indexOf('javascript:') !== -1) {
    var conv = ''
    conv += url.substring(0, url.toLowerCase().indexOf('javascript:'))
    conv += encodeURIComponent(url.substring(url.toLowerCase().indexOf('javascript:')))
    return conv
  } else {
    return url
  }
}

var abbreviate = function (value, length, omission) {
  if (!length) {
    length = 20
  }
  if (isString(length)) {
    length = parseInt(length, 10)
  }
  if (!omission) {
    omission = '...'
  }
  if (value.length <= length) {
    return value
  } else {
    return value.substring(0, length) + omission
  }
}

const deepClone = function (obj) {
  const channel = new MessageChannel()
  const inPort = channel.port1
  const outPort = channel.port2
  return new Promise(function (resolve) {
    inPort.onmessage = function (data) {
      resolve(data.data)
    }
    outPort.postMessage(obj)
  })
}

const parseParamsToQuery = (params) => {
  let query = ''
  Object.entries(params).forEach(e => {
    if (query) query += '&'
    query += e[0] + '='
    if (Array.isArray(e[1])) {
      query += e[1].join(',')
    } else {
      query += e[1]
    }
  })
  return query
}

module.exports = {
  isString: isString,
  escapeLink: escapeLink,
  abbreviate: abbreviate,
  deepClone: deepClone,
  parseParamsToQuery: parseParamsToQuery
}
