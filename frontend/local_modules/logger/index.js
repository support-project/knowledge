'use strict'

// var callsite = require('callsite')
// var path = require('path')

var getCallsiteStr = function () {
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
  return ' - '
}

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
logger.level = logger.LEVEL.WARN

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

var makelogStr = function (level, filePosition, msg) {
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
  str += filePosition
  str += msg
  return str
}

logger.debug = function () {
  if (this.level < this.LEVEL.DEBUG) return
  var args = [].slice.call(arguments)
  args[0] = makelogStr(this.LEVEL.DEBUG, getCallsiteStr(), args[0])
  console.log.apply(console, args)
}
logger.info = function () {
  if (this.level < this.LEVEL.INFO) return
  var args = [].slice.call(arguments)
  args[0] = makelogStr(this.LEVEL.INFO, getCallsiteStr(), args[0])
  console.info.apply(console, args)
}
logger.warn = function () {
  if (this.level < this.LEVEL.WARN) return
  var args = [].slice.call(arguments)
  args[0] = makelogStr(this.LEVEL.WARN, getCallsiteStr(), args[0])
  console.warn.apply(console, args)
}
logger.error = function () {
  if (this.level < this.LEVEL.ERROR) return
  var args = [].slice.call(arguments)
  args[0] = makelogStr(this.LEVEL.ERROR, getCallsiteStr(), args[0])
  console.error.apply(console, args)
}
logger.trace = function () {
  if (this.level < this.LEVEL.TRACE) return
  var args = [].slice.call(arguments)
  args[0] = makelogStr(this.LEVEL.TRACE, getCallsiteStr(), args[0])
  console.log.apply(console, args)
}

module.exports = (function () {
  return logger
})()
