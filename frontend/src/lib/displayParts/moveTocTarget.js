import Promise from 'bluebird'
import $ from 'jquery'
import logger from 'logger'

const LABEL = 'moveTocTarget.js'

export default function () {
  logger.debug(LABEL, 'start')
  return Promise.try(() => {
    logger.warn(LABEL, $('.toclink').length)
    $('.toclink').each(function () {
      logger.warn(LABEL, $(this).html())
      $(this).click(function () {
        logger.warn(LABEL, 'clicked')
        var targetId = $(this).attr('href')
        $('body, html').animate({
          scrollTop: $(targetId).offset().top - 80
        }, 500)
        return false
      })
    })
  })
}
