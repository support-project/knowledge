import Promise from 'bluebird'

import logger from 'logger'
const LABEL = 'loadPaginationInformation.js'

let loadHeaders = (response) => {
  return Promise.try(() => {
    logger.info(LABEL, 'loadHeaders')
    let pagination = {
      limit: 10,
      offset: 0,
      total: -1,
      next: -1,
      prev: -1,
      pages: []
    }
    logger.info(LABEL, JSON.stringify(response.headers, null, '  '))
    let headers = response.headers
    if (headers['x-offset']) pagination.offset = headers['x-offset']
    if (headers['x-total']) pagination.total = headers['x-total']
    if (headers['x-next-offset']) pagination.next = headers['x-next-offset']
    if (headers['x-previous-offset']) pagination.prev = headers['x-previous-offset']

    for (let i = 1; i <= 5; i++) {
      let pagekey = 'x-page' + i
      if (headers[pagekey + '-offset']) {
        let page = {
          label: headers[pagekey + '-label'],
          offset: headers[pagekey + '-offset']
        }
        if (page.offset === pagination.offset) {
          page.current = true
        }
        pagination.pages.push(page)
      }
    }
    logger.info(JSON.stringify(pagination, null, '  '))
    return pagination
  })
}

export default loadHeaders
