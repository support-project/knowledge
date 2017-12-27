import chai from 'chai'
import processFootnotes from '@/lib/decorateMarkdown/processFootnotes'
import logger from 'logger'

const assert = chai.assert
logger.level = logger.LEVEL.INFO

describe('test for footnotes', () => {
  it('脚注の生成', (done) => {
    var str = '[^1]: 注釈対応とは1\n[^2]: 注釈対応とは2'
    processFootnotes(str).then((result) => {
      logger.debug(result)
      assert.equal('<ol class="footnotes"><li id="fn-1">注釈対応とは1<a href="#fnref-1">&#8617;</a></li>\n<li id="fn-2">注釈対応とは2<a href="#fnref-2">&#8617;</a></li>\n</ol>', result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })

  it('脚注へのリンクの生成', (done) => {
    var str = '- 注釈1に関係する文章 [^1] つづいて [^2] があることもある\n- 注釈3に関係する文章 [^3]'
    processFootnotes(str).then((result) => {
      logger.debug(result)
      assert.equal('- 注釈1に関係する文章 <sup class="footnote-ref" id="fnref-1"><a href="#fn-1">1</a> つづいて <sup class="footnote-ref" id="fnref-2"><a href="#fn-2">2</a> があることもある\n- 注釈3に関係する文章 <sup class="footnote-ref" id="fnref-3"><a href="#fn-3">3</a>', result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })

  it('脚注に関係ないものは何も変化しない', (done) => {
    var str = '注釈対応とは1\n注釈対応とは2'
    processFootnotes(str).then((result) => {
      logger.debug(result)
      assert.equal(str, result)
      return done()
    }).catch((err) => {
      return done(err)
    })
  })
})
