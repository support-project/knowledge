import chai from 'chai'
import logger from 'logger'
import processDecorateAll from '@/lib/decorateMarkdown/processDecorateAll'
import processToc from '@/lib/decorateMarkdown/processToc'

const LABEL = 'processToc.spec.js'
const assert = chai.assert
logger.level = logger.LEVEL.INFO

describe('test for toc', () => {
  it('toc', (done) => {
    var str = '# aaa'
    processDecorateAll(str).then((result) => {
      logger.debug(LABEL, result)
      return processToc(result)
    }).then((result) => {
      logger.debug(LABEL, result)
      assert.equal('<div class="toc-h toc-h1"><a href="#markdown-agenda-0">- aaa</a></div>', result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })
  it('toc2', (done) => {
    var str = '# h1\n## h1-1\n### h1-1-1\n### h1-1-2\n## h2-1\n## h2-2'
    processDecorateAll(str).then((result) => {
      logger.debug(LABEL, result)
      return processToc(result)
    }).then((result) => {
      logger.debug(LABEL, result)
      assert.equal('<div class="toc-h toc-h1"><a href="#markdown-agenda-0">- h1</a></div><div class="toc-h toc-h2"><a href="#markdown-agenda-1">- h1-1</a></div><div class="toc-h toc-h3"><a href="#markdown-agenda-2">- h1-1-1</a></div><div class="toc-h toc-h3"><a href="#markdown-agenda-3">- h1-1-2</a></div><div class="toc-h toc-h2"><a href="#markdown-agenda-4">- h2-1</a></div><div class="toc-h toc-h2"><a href="#markdown-agenda-5">- h2-2</a></div>', result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })
})
