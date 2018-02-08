/* global @sourceURL */
// var callsite = require('callsite')
// var path = require('path')
var he = require('he')
var judgeBrowser = require('./judgeBrowser')

var logger = function logger() {
}

logger.LEVEL = {
  OFF: 0,
  ERROR: 1,
  WARN: 2,
  INFO: 3,
  DEBUG: 4,
  TRACE: 5,
  FULL: 6
}
logger.level = logger.LEVEL.INFO // Default is INFO

logger.setLevel = function (level) {
  if (level === 'DEBUG') {
    logger.level = logger.LEVEL.DEBUG
  } else if (level === 'INFO') {
    logger.level = logger.LEVEL.INFO
  } else if (level === 'WARN') {
    logger.level = logger.LEVEL.WARN
  } else if (level === 'ERROR') {
    logger.level = logger.LEVEL.ERROR
  } else if (level === 'TRACE') {
    logger.level = logger.LEVEL.TRACE
  } else if (level === 'OFF') {
    logger.level = logger.LEVEL.OFF
  } else if (level === 'FULL') {
    logger.level = logger.LEVEL.FULL
  }
}
logger.callsite = true

logger.setCallSite = function (callsite) {
  logger.callsite = callsite
}

var getCallsiteStr = function (loglevel) {
  /* webpack で結合されたjsファイルになるので、ファイル名＆行番号を取得できない（直接Node.jsで実行する場合は以下のコードで取得できるけど利用しない）
  if (!path) {
    return ''
  }
  if (!callsite) {
    return ''
  }
  var calls = callsite()
  console.log(calls)
  if (!calls || !calls.length || calls.length < 2) {
    return ''
  }
  var call = calls[2]
  var file = path.relative(process.cwd(), call.getFileName())
  var line = call.getLineNumber()
  return '[' + file + ':' + line + '] - '
  */
  var c = ''
  if (logger.callsite) {
    var browser = judgeBrowser()
    if (browser === 'Chrome') {
      require('source-map-support/register')
    }
    var stack = new Error().stack
    if (stack) {
      var lines = stack.toString().match(/[^\r\n]+/g)
      var line = lines[4]
      var call = line.substring(line.lastIndexOf('(') + 1, line.lastIndexOf(')'))
      if (call.lastIndexOf('webpack:/') != -1) {
        // may be Chrome only
        if (browser === 'Chrome') {
          call = call.substring(call.lastIndexOf('webpack:/') + 'webpack:/'.length, call.lastIndexOf(':'))
          call = 'webpack:///./' + call
        }
      }
      c += ' (' + call + ')'
    }
  }
  c += ' - '
  return c
}

var isString = function (obj) {
  return typeof (obj) === 'string' || obj instanceof String
}

var makelogStr = function (level, filePosition, msg, label) {
  var str = ''
  if (level === logger.LEVEL.DEBUG) {
    str += '[DEBUG]'
  } else if (level === logger.LEVEL.INFO) {
    str += '[INFO]'
  } else if (level === logger.LEVEL.WARN) {
    str += '[WARN]'
  } else if (level === logger.LEVEL.ERROR) {
    str += '[ERROR]'
  } else if (level === logger.LEVEL.TRACE) {
    str += '[TRACE]'
  }
  if (label) {
    str += ' ' + label
  }
  str += filePosition
  str += msg
  return str
}

var createMsgArgs = function(loglevel, params) {
  var args = [].slice.call(params)
  if (args.length === 2) {
    var msg = args[1]
    args[1] = makelogStr(loglevel, getCallsiteStr(loglevel), args[1], args[0])
    args.shift() // 先頭のラベルは削除
  } else {
    args[0] = makelogStr(loglevel, getCallsiteStr(loglevel), args[0])
  }
  return args;
}

logger.debug = function () {
  var L = this.LEVEL.DEBUG
  if (this.level < L) return
  var args = createMsgArgs(L, arguments)
  console.log.apply(console, args)
}
logger.info = function () {
  var L = this.LEVEL.INFO
  if (this.level < L) return
  var args = createMsgArgs(L, arguments)
  console.info.apply(console, args)
}
logger.warn = function () {
  var L = this.LEVEL.WARN
  if (this.level < L) return
  var args = createMsgArgs(L, arguments)
  console.warn.apply(console, args)
}
logger.error = function () {
  var L = this.LEVEL.ERROR
  if (this.level < L) return
  var args = createMsgArgs(L, arguments)
  console.error.apply(console, args)
}
logger.trace = function () {
  var L = this.LEVEL.TRACE
  if (this.level < L) return
  var args = createMsgArgs(L, arguments)
  console.log.apply(console, args)
}

// axios の http response が error になった場合に、そこからエラーのメッセージを生成する
logger.buildResponseErrorMsg = function (response, params) {
  var msg = ''
  var detail = false
  if (params && params.detail) {
    detail = params.detail
  }
  var prefix = ''
  var suffix = ''
  if (params && params.prefix) {
    prefix = params.prefix
  }
  if (params && params.suffix) {
    suffix = params.suffix
  }
  msg += prefix  
  if (response) {
    if (response.status && detail) {
      msg += '[Status] ' + response.status + '. '
    }
    if (response.data && response.data.message) {
      msg += he.decode(response.data.message, {
        'isAttributeValue': true
      })
      msg += ' '
    }
  } else {
    msg += 'response is undefined.'
  }
  msg += suffix  
  return msg
}

module.exports = (function () {
  return logger
})()
