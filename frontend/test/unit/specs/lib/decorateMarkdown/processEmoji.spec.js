import chai from 'chai'
import processEmoji from '@/lib/decorateMarkdown/processEmoji'
import logger from 'logger'

const assert = chai.assert
logger.level = logger.LEVEL.INFO

describe('test for emoji', () => {
  it('thumsup', (done) => {
    var str = 'これは:+1:です'
    processEmoji(str).then((result) => {
      logger.debug(result)
      assert.equal('これは👍です', result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })
})
