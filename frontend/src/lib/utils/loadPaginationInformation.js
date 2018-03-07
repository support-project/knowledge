import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'loadPaginationInformation.js'

let loadHeaders = (response) => {
  return Promise.try(() => {
    logger.debug(LABEL, 'loadHeaders')
    let pagination = {
      limit: 10,
      offset: 0,
      total: -1,
      next: -1,
      prev: -1,
      pages: []
    }
    logger.debug(LABEL, JSON.stringify(response.headers, null, '  '))
    let headers = response.headers
    if (headers['x-offset']) pagination.offset = parseInt(headers['x-offset'])
    if (headers['x-total']) pagination.total = parseInt(headers['x-total'])
    if (headers['x-first-offset']) pagination.first = parseInt(headers['x-first-offset'])
    if (headers['x-last-offset']) pagination.last = parseInt(headers['x-last-offset'])
    if (headers['x-next-offset']) pagination.next = parseInt(headers['x-next-offset'])
    if (headers['x-previous-offset']) pagination.prev = parseInt(headers['x-previous-offset'])
    if (headers['x-limit']) pagination.limit = parseInt(headers['x-limit'])

    for (let i = 1; i <= 5; i++) {
      let pagekey = 'x-page' + i
      if (headers[pagekey + '-offset']) {
        let page = {
          label: headers[pagekey + '-label'],
          offset: parseInt(headers[pagekey + '-offset'])
        }
        if (page.offset === pagination.offset) {
          page.current = true
        }
        pagination.pages.push(page)
      }
    }
    logger.debug(JSON.stringify(pagination, null, '  '))
    return pagination
  })
}

export default loadHeaders
