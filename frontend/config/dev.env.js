var merge = require('webpack-merge')
var prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  LOG_LEVEL: '"INFO"',
  LOG_CALLSITE: 'true'
})
