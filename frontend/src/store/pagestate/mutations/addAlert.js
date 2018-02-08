import logger from 'logger'
const LABEL = 'addAlert.js'

var types = {
  danger: { type: 'danger', icon: 'fa-ban', display: true, notify: true },
  info: { type: 'info', icon: 'fa-info-circle', display: true, notify: true },
  warning: { type: 'warning', icon: 'fa-warning', display: true, notify: true },
  success: { type: 'success', icon: 'fa-check', display: true, notify: true }
}

export default (state, params) => {
  logger.debug(LABEL, JSON.stringify(params))
  var type = types.info
  if (params.type) {
    if (params.type in types) {
      type = types[params.type]
    }
  }
  var alertMsg = {
    key: new Date().getTime(),
    title: params.title,
    content: params.content
  }
  alertMsg = Object.assign(alertMsg, type)
  if (params.icon) {
    alertMsg.icon = params.icon
  }
  if ('display' in params) {
    alertMsg.display = params.display
  }
  if ('notify' in params) {
    alertMsg.notify = params.notify
  }
  logger.debug(LABEL, JSON.stringify(alertMsg))
  state.alerts.push(alertMsg)
}
