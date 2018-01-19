import logger from 'logger'
const LABEL = 'addAlert.js'

var types = {
  danger: {type: 'danger', icon: 'fa-ban'},
  info: {type: 'info', icon: 'fa-info-circle'},
  warning: {type: 'warning', icon: 'fa-warning'},
  success: {type: 'success', icon: 'fa-check'}
}

export default (state, params) => {
  logger.debug(LABEL, JSON.stringify(params))
  var type = types.info
  if (params.type) {
    if (params.type in types) {
      type = types[params.type]
    }
  }
  if (params.icon) {
    type.icon = params.icon
  }
  var alertMsg = {
    key: new Date().getTime(),
    type: type.type,
    title: params.title,
    msg: params.msg,
    icon: type.icon
  }
  logger.debug(LABEL, JSON.stringify(alertMsg))
  state.pagestate.alerts.push(alertMsg)
}
