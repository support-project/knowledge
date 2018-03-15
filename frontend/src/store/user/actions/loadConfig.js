import logger from 'logger'
const LABEL = 'loadConfig.js'

export default (configs, configName) => {
  logger.debug(LABEL, JSON.stringify(configs))
  let val = ''
  configs.forEach(element => {
    if (element.configName === configName) {
      val = element.configValue
    }
  })
  return val
}
