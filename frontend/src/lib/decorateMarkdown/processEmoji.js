import Promise from 'bluebird'
import wemoji from 'wemoji'

const escape = function (s) {
  return s.replace(/[-/\\^$*+?.()|[\]{}]/g, '\\$&')
}

/**
 * 絵文字のテキストを、Unicodeの絵文字に置換する
 * :+1: → 👍🏻
 * 今後は、Unicodeの絵文字をメインに使う
 */
export default function (input) {
  return Promise.try(() => {
    console.log(input)
    const regex = /(:.+?:)/g
    const results = input.match(regex)
    console.log(results)
    if (results && results.length > 0) {
      results.forEach((str) => {
        console.log(str)
        const name = str.substring(1, str.length - 1) // 左右の:を削除
        console.log(name)
        var emoji = wemoji.name[name]
        if (emoji) {
          input = input.replace(new RegExp(escape(str), 'g'), emoji.emoji)
        }
      })
    }
    console.log(input)
    return input
  })
}
