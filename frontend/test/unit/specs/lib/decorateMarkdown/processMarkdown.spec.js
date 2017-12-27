import chai from 'chai'
import processMarkdown from '@/lib/decorateMarkdown/processMarkdown'
import logger from 'logger'

const assert = chai.assert
logger.level = logger.LEVEL.INFO

describe('test for markdown', () => {
  it('parse test1', (done) => {
    var str = '# head1'
    processMarkdown(str).then((result) => {
      logger.debug(result)
      assert.equal('<h1 id="markdown-agenda-0" ><a name="head1" class="anchor" href="#head1"><span class="header-link"></span></a>head1</h1>', result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })
})
