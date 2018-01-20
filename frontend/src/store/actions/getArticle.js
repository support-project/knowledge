import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

import actionCommon from './actionCommon'

import processDecorateAll from '../../lib/decorateMarkdown/processDecorateAll'
import processToc from '../../lib/decorateMarkdown/processToc'
import processTemplateItemView from '../../lib/displayParts/processTemplateItemView'

const LABEL = 'getArticle.js'

export default (context, id) => {
  context.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    context.commit('SET_RESOURCES', {article: ''})
    context.commit('SET_RESOURCES', {comments: []})
    context.commit('CREAR_ALERTS')
    return api.request('get', '/_api/articles/' + id + '', null)
    .then(response => {
      var article = response.data
      actionCommon.setIcon(context, article)
      logger.debug(LABEL, response)
      return Promise.try(() => {
        return processDecorateAll(response.data.content)
      }).then(function (result) {
        logger.debug(LABEL, result)
        article.displaySafeHtml = result
        return processToc(result)
      }).then(function (toc) {
        logger.debug(LABEL, toc)
        context.commit('SET_RESOURCES', {toc: toc})
        return api.request('get', '/_api/articles/' + id + '/items', null)
      }).then(function (response) {
        return processTemplateItemView(response.data)
      }).then(function (itemsHtml) {
        logger.debug(itemsHtml)
        article.itemsHtml = itemsHtml
        context.commit('SET_RESOURCES', {article: article})
        // return article.comments
        return api.request('get', '/_api/articles/' + id + '/comments', null)
      }).then(function (response) {
        logger.debug(LABEL, response.data)
        return Promise.each(response.data, function (comment) {
          return processDecorateAll(comment.comment).then(function (result) {
            comment.displaySafeHtml = result
            actionCommon.setIcon(context, comment)
          })
        })
      }).then(function (comments) {
        context.commit('SET_RESOURCES', {comments: comments})
      })
    })
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    context.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    context.commit('SET_PAGE_STATE', {loading: false})
  })
}
