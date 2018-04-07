import jstz from 'jstz'
var timezone = jstz.determine().name()

export default {
  avatar: 'open.account/icon/',
  userName: 'anonymous',
  localeKey: 'en',
  timezone: timezone,
  POINT: 0,
  configs: []
}
